package application;

import ansarcontrols.*;
import entities.entries.*;
import entities.files.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.*;

public class RecitationNoteBookScreen implements IAnsarScreen<RecitationEntry>
{
    private AnsarScene scene;
    private AnsarLabeledControlHBox<String> creationDate;
    private AnsarLabeledControlHBox<String> studentCode;
    private AnsarLabeledControlHBox<String> studentName;
    private RecitationBoxWithGrade recitationBox;
    private RecitationBoxWithGrade revisionBox;
    private RecitationBox nextRecitationBox;
    private RecitationBox nextRevisionBox;
    private AnsarLabeledControlHBox<String> remark;
    private Student student;

    public RecitationNoteBookScreen()
    {
        this.scene = constructScreen();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "memorizationNoteBook";
    }

    @Override
    public Pane createContentBox()
    {
        creationDate = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
        creationDate.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
        studentCode = new AnsarLabeledControlHBox<>("studentCode", ControlType.TextField);
        studentCode.setOnKeyPressed(e ->
        {
            if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
                return;
            student = Persister.findByCode(Student.class, studentCode.fetchValue());
            if (ObjectChecker.isEmptyOrZeroOrNull(student))
                return;
            GroupLevel groupLevel = student.getGroup().getGroupLevel();
            updateSurahsComboBoxes(groupLevel);
            studentName.insertValue(student.getName());

            RecitationEntry entry = RecitationUtil.createRecitationEntryForStudent(student);

            updateRecitationBoxData(entry.getRecitation(), recitationBox);
            updateRecitationBoxData(entry.getNextRecitation(), nextRecitationBox);
            updateRecitationBoxData(entry.getRevision(), revisionBox);
            updateRecitationBoxData(entry.getNextRevision(), nextRevisionBox);
            remark.insertValue(entry.getRemark());
        });
        studentName = new AnsarLabeledControlHBox<>("studentName", ControlType.TextField);
        ((AnsarTextField) studentName.getControl()).setEditable(false);
        recitationBox = new RecitationBoxWithGrade("recitation");
        recitationBox.toAyaCallback(toAya ->
        {
            if (recitationBox.isEmpty())
                return;
            updateNextRecitationBoxData(recitationBox.fetchRecitationInfo(), nextRecitationBox,
                    student.getGroup().getGroupLevel().getDailyRecitationInVerses());
        });
        revisionBox = new RecitationBoxWithGrade("revision");
        revisionBox.toAyaCallback(toAya ->
        {
            if (revisionBox.isEmpty())
                return;
            updateNextRecitationBoxData(revisionBox.fetchRecitationInfo(), nextRevisionBox,
                    student.getGroup().getGroupLevel().getRevisionRecitationInVerses());
        });
        nextRecitationBox = new RecitationBox("nextRecitation");
        nextRevisionBox = new RecitationBox("nextRevision");
        remark = new AnsarLabeledControlHBox<>("remark", ControlType.TextArea);
        AnsarVBox contentPane = new AnsarVBox();
        contentPane.getChildren().addAll(creationDate, new AnsarHBox(studentCode, studentName), recitationBox,
                revisionBox, nextRecitationBox, nextRevisionBox, remark, createBtnsBox());
        AnsarScrollPane pane = new AnsarScrollPane(contentPane);
        pane.setMaxHeight(ResourceUtility.fetchScreenHeight() - 150);
        return new AnsarVBox(pane);
    }

    private void updateSurahsComboBoxes(GroupLevel groupLevel)
    {
        recitationBox.updateSurahs(SurahsUtil.fetchRecitationSurahsOfLevel(groupLevel));
        revisionBox.updateSurahs(SurahsUtil.fetchRevisionSurahsOfLevel(groupLevel));
        nextRecitationBox.updateSurahs(SurahsUtil.fetchRecitationSurahsOfLevel(groupLevel));
        nextRevisionBox.updateSurahs(SurahsUtil.fetchRevisionSurahsOfLevel(groupLevel));
    }

    private void updateRecitationBoxData(AbsRecitationInfo recitationInfo, RecitationBox recitationBox)
    {
        recitationBox.setFromSurah(recitationInfo.getFromSurah());
        recitationBox.setFromAya(recitationInfo.getFromAya());
        recitationBox.setToSurah(recitationInfo.getToSurah());
        recitationBox.setToAya(recitationInfo.getToAya());
    }

    private void updateNextRecitationBoxData(AbsRecitationInfo recitationInfo, RecitationBox recitationBox,
                                             int versesCount)
    {
        RecitationInfo nextRecitation = RecitationUtil.calcNextRecitationInfo(recitationInfo, versesCount);
        updateRecitationBoxData(nextRecitation, recitationBox);
    }

    public Pane createBtnsBox()
    {
        AnsarHBox btnsBox = new AnsarHBox();
        AnsarButton saveBtn = new AnsarButton("save");
        saveBtn.setOnAction(e -> submit());
        AnsarButton newBtn = new AnsarButton("new");
        newBtn.setOnAction(e -> reset());
        btnsBox.getChildren().addAll(saveBtn, newBtn);
        return btnsBox;
    }

    @Override
    public void reset()
    {
        IAnsarScreen.super.reset();
        student = null;
        creationDate.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
    }

    @Override
    public AnsarScene fetchScene()
    {
        return scene;
    }

    @Override
    public RecitationEntry createEntity()
    {
        RecitationInfoWithGrade recitation = (RecitationInfoWithGrade) recitationBox.fetchRecitationInfo();
        RecitationInfoWithGrade revision = (RecitationInfoWithGrade) revisionBox.fetchRecitationInfo();
        RecitationInfo nextRecitation = (RecitationInfo) nextRecitationBox.fetchRecitationInfo();
        RecitationInfo nextRevision = (RecitationInfo) nextRevisionBox.fetchRecitationInfo();

        RecitationEntry entry = new RecitationEntry();
        entry.setStudent(student);
        entry.setGroup(student.getGroup());
        entry.setRecitation(recitation);
        entry.setRevision(revision);
        entry.setNextRecitation(nextRecitation);
        entry.setNextRevision(nextRevision);
        entry.setRemark(remark.fetchValue());
        return entry;
    }

}
