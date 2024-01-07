package application;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTextField;
import ansarcontrols.AnsarVBox;
import ansarcontrols.AnsarVBoxRoot;
import ansarcontrols.ControlType;
import entities.AbsRecitationInfo;
import entities.GroupLevel;
import entities.RecitationEntry;
import entities.RecitationInfo;
import entities.RecitationInfoWithGrade;
import entities.Student;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class RecitationNoteBookScreen implements IDocumentScreen<RecitationEntry> {
	private AnsarScene scene;
	private AnsarVBoxRoot root;
	private AnsarLabeledControlHBox<String> creationDate;
	private AnsarLabeledControlHBox<String> studentCode;
	private AnsarLabeledControlHBox<String> studentName;
	private RecitationBoxWithGrade recitationBox;
	private RecitationBoxWithGrade revisionBox;
	private RecitationBox nextRecitationBox;
	private RecitationBox nextRevisionBox;
	private AnsarLabeledControlHBox<String> remark;
	private Student student;

	public RecitationNoteBookScreen() {
		this.scene = constructScreen();
	}

	@Override
	public String fetchScreenTitle() {
		return "memorizationNoteBook";
	}

	@Override
	public Pane createContentBox() {
		AnsarVBox headerPane = new AnsarVBox();
		creationDate = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
		creationDate.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		studentCode = new AnsarLabeledControlHBox<>("studentCode", ControlType.TextField);
		studentCode.setOnKeyPressed(e -> {
			if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
				return;
			student = Persister.findByCode(Student.class, Long.valueOf(studentCode.fetchValue()));
			if (ObjectChecker.isEmptyOrZeroOrNull(student))
				return;
			studentName.insertValue(student.getName());
			RecitationEntry lastEntry = Persister.getSingleResult(
					"FROM RecitationEntry WHERE student_id = :studentId ORDER BY creationDate DESC",
					Persister.params("studentId", student.getId()));

			GroupLevel groupLevel = student.getGroup().getGroupLevel();

			RecitationInfo recitation = lastEntry.getNextRecitation();
			updateRecitationBoxData(recitation, recitationBox);
			updateNextRecitationBoxData(recitation, nextRecitationBox,
					student.getGroup().getGroupLevel().getDailyRecitationInVerses().intValue());

			RecitationInfo revision = lastEntry.getNextRevision();
			updateRecitationBoxData(revision, revisionBox);
			updateNextRecitationBoxData(revision, nextRevisionBox,
					student.getGroup().getGroupLevel().getRevisionRecitationInVerses().intValue());
		});
		studentName = new AnsarLabeledControlHBox<>("studentName", ControlType.TextField);
		((AnsarTextField) studentName.getControl()).setEditable(false);
		recitationBox = new RecitationBoxWithGrade("recitation");
		recitationBox.toAyaCallback(toAya -> {
			if (recitationBox.isEmpty())
				return;
			updateNextRecitationBoxData(recitationBox.fetchRecitationInfo(), nextRecitationBox,
					student.getGroup().getGroupLevel().getDailyRecitationInVerses().intValue());
		});
		revisionBox = new RecitationBoxWithGrade("revision");
		revisionBox.toAyaCallback(toAya -> {
			if (revisionBox.isEmpty())
				return;
			updateNextRecitationBoxData(revisionBox.fetchRecitationInfo(), nextRevisionBox,
					student.getGroup().getGroupLevel().getRevisionRecitationInVerses().intValue());
		});
		nextRecitationBox = new RecitationBox("nextRecitation");
		nextRevisionBox = new RecitationBox("nextRevision");
		remark = new AnsarLabeledControlHBox<>("remark", ControlType.TextArea);
		headerPane.getChildren().addAll(creationDate, new AnsarHBox(studentCode, studentName), recitationBox,
				revisionBox, nextRecitationBox, nextRevisionBox, remark, createBtnsBox());
		return headerPane;
	}

	private void updateRecitationBoxData(AbsRecitationInfo recitationInfo, RecitationBox recitationBox) {
		recitationBox.setFromSurah(recitationInfo.getFromSurah().nameByLang());
		recitationBox.setFromAya(recitationInfo.getFromAya());
		recitationBox.setToSurah(recitationInfo.getToSurah().nameByLang());
		recitationBox.setToAya(recitationInfo.getToAya());
	}

	private void updateNextRecitationBoxData(AbsRecitationInfo recitationInfo, RecitationBox recitationBox,
			int versesCount) {
		RecitationInfo nextRecitation = ResourceUtility.calcNextRecitationInfo(recitationInfo, versesCount);
		updateRecitationBoxData(nextRecitation, recitationBox);
	}

	public Pane createBtnsBox() {
		AnsarHBox btnsBox = new AnsarHBox();
		AnsarButton saveBtn = new AnsarButton("save");
		saveBtn.setOnAction(e -> submit());
		AnsarButton newBtn = new AnsarButton("new");
		newBtn.setOnAction(e -> reset());
		AnsarButton backBtn = new AnsarButton("back");
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, newBtn, backBtn);
		return btnsBox;
	}

	@Override
	public void reset() {
		IDocumentScreen.super.reset();
		student = null;
		creationDate.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	@Override
	public RecitationEntry createEntity() {
		RecitationInfoWithGrade recitation = (RecitationInfoWithGrade) recitationBox.fetchRecitationInfo();
		RecitationInfoWithGrade revision = (RecitationInfoWithGrade) revisionBox.fetchRecitationInfo();
		RecitationInfo nextRecitation = (RecitationInfo) nextRecitationBox.fetchRecitationInfo();
		RecitationInfo nextRevision = (RecitationInfo) nextRevisionBox.fetchRecitationInfo();

		RecitationEntry entry = Persister.getSingleResult(
				"FROM RecitationEntry WHERE student=:student AND creationDate=:creationDate",
				Persister.params("student", student, "creationDate", LocalDate.now()));
//		RecitationEntry entry = new RecitationEntry();
		System.out.println(entry);
		entry.setCreationDate(LocalDate.now());
		entry.setStudent(student);
		entry.setRecitation(recitation);
		entry.setRevision(revision);
		entry.setNextRecitation(nextRecitation);
		entry.setNextRevision(nextRevision);
		entry.setRemark(remark.fetchValue());
		return entry;
	}
}
