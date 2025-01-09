package application;

import ansarcontrols.*;
import entities.details.*;
import entities.files.GroupLevel;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import utilities.*;

import java.time.LocalDateTime;

public class GroupLevelScreen implements IFileScreen<GroupLevel>
{
    private AnsarScene scene;
    private AnsarLabeledControlHBox<String> codeBox;
    private AnsarLabeledControlHBox<String> nameBox;
    private AnsarLabeledControlHBox<String> registrationDateBox;
    private AnsarLabeledControlHBox<Surah> fromSurah;
    private AnsarLabeledControlHBox<Surah> toSurah;
    private AnsarLabeledControlHBox<Surah> revisionFromSurah;
    private AnsarLabeledControlHBox<Surah> revisionToSurah;
    private AnsarButton saveBtn;
    private AnsarButton backBtn;
    private AnsarTable<GroupLevel> table;


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
        revisionFromSurah = new AnsarLabeledControlHBox<>("revisionFromSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> revisionFromSurahComboBox = revisionFromSurah.getControl();
        revisionFromSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        revisionToSurah = new AnsarLabeledControlHBox<>("revisionToSurah", ControlType.ComboBox);
        AnsarComboBox<Surah> revisionToSurahComboBox = revisionToSurah.getControl();
        revisionToSurahComboBox.config(SurahsUtil.getSurahs(), Surah::nameByLang);
        headerPane.add(codeBox, 0, 0);
        headerPane.add(registrationDateBox, 1, 0);
        headerPane.add(nameBox, 0, 1);
        headerPane.add(fromSurah, 0, 2);
        headerPane.add(toSurah, 1, 2);
        headerPane.add(revisionFromSurah, 0, 4);
        headerPane.add(revisionToSurah, 1, 4);
        AnsarVBox root = new AnsarVBox(headerPane, createGroupLevelDetailsTable(), createRevisionDetailsTable(),
                new Button("what!"));
        return root;
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
        level.setRevisionFromSurah(revisionFromSurah.fetchValue());
        level.setRevisionToSurah(revisionToSurah.fetchValue());

        return level;
    }

    @Override
    public AnsarTable<GroupLevel> createListViewTable()
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
        AnsarTableColumn<GroupLevel, String> codeCol = new AnsarTableColumn<>("code");
        codeCol.config("code");
        AnsarTableColumn<GroupLevel, String> nameCol = new AnsarTableColumn<>("name");
        nameCol.config("name");
        AnsarTableColumn<GroupLevel, LocalDateTime> creationDateCol = new AnsarTableColumn<>("creationDate");
        creationDateCol.config("creationDate");
        AnsarTableColumn<GroupLevel, Surah> fromSurahCol = new AnsarTableColumn<>("fromSurah");
        fromSurahCol.config("fromSurah", Surah::nameByLang);
        AnsarTableColumn<GroupLevel, Surah> toSurahCol = new AnsarTableColumn<>("toSurah");
        toSurahCol.config("toSurah", Surah::nameByLang);
        AnsarTableColumn<GroupLevel, Surah> revisionFromSurahCol = new AnsarTableColumn<>("revisionFromSurah");
        revisionFromSurahCol.config("revisionFromSurah", Surah::nameByLang);
        AnsarTableColumn<GroupLevel, Surah> revisionToSurahCol = new AnsarTableColumn<>("revisionToSurah");
        revisionToSurahCol.config("revisionToSurah", Surah::nameByLang);
        table.getColumns().addAll(codeCol, creationDateCol, nameCol, fromSurahCol, toSurahCol,
                revisionFromSurahCol, revisionToSurahCol);
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
        revisionFromSurah.insertValue(item.getRevisionFromSurah());
        revisionToSurah.insertValue(item.getRevisionToSurah());
    }

    public AnsarTable<GroupLevelDetail> createGroupLevelDetailsTable()
    {
        AnsarTable<GroupLevelDetail> table = new AnsarTable<>();
        table.setEditable(true);
        AnsarTableColumn<GroupLevelDetail, Surah> fromSurahCol = new AnsarTableColumn<>("fromSurah");
        fromSurahCol.configEditableCol("fromSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelDetail, Surah> toSurahCol = new AnsarTableColumn<>("toSurah");
        toSurahCol.configEditableCol("toSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelDetail, Surah> revisionFromSurahCol = new AnsarTableColumn<>("revisionFromSurah");
        revisionFromSurahCol.configEditableCol("revisionFromSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelDetail, Surah> revisionToSurahCol = new AnsarTableColumn<>("revisionToSurah");
        revisionToSurahCol.configEditableCol("revisionToSurah", SurahsUtil.stringConverter());
        table.getColumns().addAll(fromSurahCol, toSurahCol,
                revisionFromSurahCol, revisionToSurahCol);
        table.getItems().add(new GroupLevelDetail());
        return table;
    }

    public AnsarTable<GroupLevelRevisionDetail> createRevisionDetailsTable()
    {
        AnsarTable<GroupLevelRevisionDetail> table = new AnsarTable<>();
        table.setEditable(true);
        AnsarTableColumn<GroupLevelRevisionDetail, Surah> fromSurahCol = new AnsarTableColumn<>("fromSurah");
        fromSurahCol.configEditableCol("fromSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelRevisionDetail, Surah> toSurahCol = new AnsarTableColumn<>("toSurah");
        toSurahCol.configEditableCol("toSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelRevisionDetail, Surah> revisionFromSurahCol = new AnsarTableColumn<>("revisionFromSurah");
        revisionFromSurahCol.configEditableCol("revisionFromSurah", SurahsUtil.stringConverter());
        AnsarTableColumn<GroupLevelRevisionDetail, Surah> revisionToSurahCol = new AnsarTableColumn<>("revisionToSurah");
        revisionToSurahCol.configEditableCol("revisionToSurah", SurahsUtil.stringConverter());
        table.getColumns().addAll(fromSurahCol, toSurahCol,
                revisionFromSurahCol, revisionToSurahCol);
        table.getItems().add(new GroupLevelRevisionDetail());
        return table;
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
