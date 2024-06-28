package application;

import java.time.LocalDateTime;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarGridPane;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarTableRow;
import ansarcontrols.ControlType;
import entities.GroupLevel;
import javafx.scene.layout.Pane;
import utilities.*;

public class GroupLevelScreen implements IFileScreen<GroupLevel>
{
    private AnsarScene scene;
    private AnsarLabeledControlHBox<String> codeBox;
    private AnsarLabeledControlHBox<String> nameBox;
    private AnsarLabeledControlHBox<String> registrationDateBox;
    private AnsarLabeledControlHBox<Surah> fromSurah;
    private AnsarLabeledControlHBox<Surah> toSurah;
    private AnsarLabeledControlHBox<String> dailyRecitationInVerses;
    private AnsarLabeledControlHBox<Surah> revisionFromSurah;
    private AnsarLabeledControlHBox<Surah> revisionToSurah;
    private AnsarLabeledControlHBox<String> revisionRecitationInVerses;
    private AnsarButton saveBtn;
    private AnsarButton backBtn;
    private AnsarTable<GroupLevel> table;
    private AnsarTableColumn<GroupLevel, String> codeCol;
    private AnsarTableColumn<GroupLevel, String> nameCol;
    private AnsarTableColumn<GroupLevel, LocalDateTime> creationDateCol;
    private AnsarTableColumn<GroupLevel, Surah> fromSurahCol;
    private AnsarTableColumn<GroupLevel, Surah> toSurahCol;
    private AnsarTableColumn<GroupLevel, Short> dailyRecitationInVersesCol;
    private AnsarTableColumn<GroupLevel, Surah> revisionFromSurahCol;
    private AnsarTableColumn<GroupLevel, Surah> revisionToSurahCol;
    private AnsarTableColumn<GroupLevel, Short> revisionRecitationInVersesCol;

    public GroupLevelScreen()
    {
        this.scene = constructScreen();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "levelsScreen";
    }

    @Override
    public Pane createContent()
    {
        AnsarGridPane headerPane = new AnsarGridPane();
        codeBox = new AnsarLabeledControlHBox<>("code", ControlType.Label);
        codeBox.insertValue(fetchCode());
        registrationDateBox = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
        registrationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
        nameBox = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
        fromSurah = new AnsarLabeledControlHBox<>("fromSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> fromSurahComboBox = fromSurah.getControl();
        fromSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        toSurah = new AnsarLabeledControlHBox<>("toSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> toSurahComboBox = toSurah.getControl();
        toSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        dailyRecitationInVerses = new AnsarLabeledControlHBox<>("dailyRecitationInVerses", ControlType.TextField);
        revisionFromSurah = new AnsarLabeledControlHBox<>("revisionFromSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> revisionFromSurahComboBox = revisionFromSurah.getControl();
        revisionFromSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        revisionToSurah = new AnsarLabeledControlHBox<>("revisionToSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> revisionToSurahComboBox = revisionToSurah.getControl();
        revisionToSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        revisionRecitationInVerses = new AnsarLabeledControlHBox<>("revisionRecitationInVerses", ControlType.TextField);
        headerPane.add(codeBox, 0, 0);
        headerPane.add(registrationDateBox, 1, 0);
        headerPane.add(nameBox, 0, 1);
        headerPane.add(fromSurah, 0, 2);
        headerPane.add(toSurah, 1, 2);
        headerPane.add(dailyRecitationInVerses, 2, 2);
        headerPane.add(revisionFromSurah, 0, 3);
        headerPane.add(revisionToSurah, 1, 3);
        headerPane.add(revisionRecitationInVerses, 2, 3);
        return headerPane;
    }

    @Override
    public GroupLevel createEntity()
    {
        GroupLevel level = new GroupLevel();
        String code = codeBox.fetchValue();
        Long id = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).map(i -> i.getId())
                .findFirst().orElse(null);
        level.setId(id);
        level.setCode(code);
        level.setName(nameBox.fetchValue());
        level.setCreationDate(DateTimeUtility.parseDateTime(registrationDateBox.fetchValue()));
        level.setFromSurah(fromSurah.fetchValue());
        level.setToSurah(toSurah.fetchValue());
        level.setDailyRecitationInVerses(
                Short.valueOf(ObjectChecker.isEmptyOrZeroOrNull(dailyRecitationInVerses.fetchValue()) ? "0"
                        : dailyRecitationInVerses.fetchValue()));
        level.setRevisionFromSurah(revisionFromSurah.fetchValue());
        level.setRevisionToSurah(revisionToSurah.fetchValue());
        level.setRevisionRecitationInVerses(
                Short.valueOf(ObjectChecker.isEmptyOrZeroOrNull(revisionRecitationInVerses.fetchValue()) ? "0"
                        : revisionRecitationInVerses.fetchValue()));
        return level;
    }

    @Override
    public AnsarTable<GroupLevel> createTableView()
    {
        table = new AnsarTable<>();
        table.setRowFactory(c ->
        {
            AnsarTableRow<GroupLevel> row = new AnsarTableRow<>();
            row.setOnMouseClicked(e ->
            {
                AnsarTableRow<GroupLevel> rowRef = row;
                selectRowAction(rowRef.getItem());
            });
            return row;
        });
        codeCol = new AnsarTableColumn<>("code");
        codeCol.config("code");
        nameCol = new AnsarTableColumn<>("name");
        nameCol.config("name");
        creationDateCol = new AnsarTableColumn<>("creationDate");
        creationDateCol.config("creationDate");
        fromSurahCol = new AnsarTableColumn<>("fromSurah");
        fromSurahCol.config("fromSurah", s -> s.nameByLang());
        toSurahCol = new AnsarTableColumn<>("toSurah");
        toSurahCol.config("toSurah", s -> s.nameByLang());
        dailyRecitationInVersesCol = new AnsarTableColumn<>("dailyRecitationInVerses");
        dailyRecitationInVersesCol.config("dailyRecitationInVerses", v -> ObjectChecker.toStringOrEmpty(v));
        revisionFromSurahCol = new AnsarTableColumn<>("revisionFromSurah");
        revisionFromSurahCol.config("revisionFromSurah", s -> s.nameByLang());
        revisionToSurahCol = new AnsarTableColumn<>("revisionToSurah");
        revisionToSurahCol.config("revisionToSurah", s -> s.nameByLang());
        revisionRecitationInVersesCol = new AnsarTableColumn<>("revisionRecitationInVerses");
        revisionRecitationInVersesCol.config("revisionRecitationInVerses", v -> ObjectChecker.toStringOrEmpty(v));
        table.getColumns().addAll(codeCol, creationDateCol, nameCol, fromSurahCol, toSurahCol,
                dailyRecitationInVersesCol, revisionFromSurahCol, revisionToSurahCol, revisionRecitationInVersesCol);
        return table;
    }

    @Override
    public void selectRowAction(GroupLevel item)
    {
        codeBox.insertValue(item.getCode());
        registrationDateBox.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
        nameBox.insertValue(item.getName());
        fromSurah.insertValue(item.getFromSurah());
        toSurah.insertValue(item.getToSurah());
        dailyRecitationInVerses.insertValue(item.getDailyRecitationInVerses());
        revisionFromSurah.insertValue(item.getRevisionFromSurah());
        revisionToSurah.insertValue(item.getRevisionToSurah());
        revisionRecitationInVerses.insertValue(item.getRevisionRecitationInVerses());
    }

    @Override
    public AnsarTable<GroupLevel> fetchTable()
    {
        return table;
    }

    @Override
    public AnsarScene fetchScene()
    {
        return scene;
    }

    @Override
    public Class<GroupLevel> fetchDocumentClass()
    {
        return GroupLevel.class;
    }

    @Override
    public AnsarLabeledControlHBox<String> fetchCodeBox()
    {
        return codeBox;
    }

    @Override
    public AnsarLabeledControlHBox<String> fetchCreationDateBox()
    {
        return registrationDateBox;
    }

}
