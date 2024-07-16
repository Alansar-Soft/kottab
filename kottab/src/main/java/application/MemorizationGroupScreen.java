package application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarGridPane;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarTableRow;
import ansarcontrols.ControlType;
import entities.GroupLevel;
import entities.MemorizationGroup;
import entities.MemorizationTeacher;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;

public class MemorizationGroupScreen implements IFileScreen<MemorizationGroup> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> codeBox;
	private AnsarLabeledControlHBox<String> registrationDateBox;
	private AnsarLabeledControlHBox<String> nameBox;
	private AnsarLabeledControlHBox<MemorizationTeacher> teacherBox;
	private AnsarLabeledControlHBox<MemorizationTeacher> assistantBox;
	private AnsarLabeledControlHBox<GroupLevel> groupLevel;
	private AnsarGridPane headerContentPane;
	private AnsarHBox btnsBox;
	private AnsarTable<MemorizationGroup> table;
	private AnsarTableColumn<MemorizationGroup, Long> codeCol;
	private AnsarTableColumn<MemorizationGroup, LocalDateTime> creationDateCol;
	private AnsarTableColumn<MemorizationGroup, String> nameCol;
	private AnsarTableColumn<MemorizationGroup, MemorizationTeacher> teacherCol;
	private AnsarTableColumn<MemorizationGroup, MemorizationTeacher> assistantCol;
	private AnsarTableColumn<MemorizationGroup, GroupLevel> groupLevelCol;

	public MemorizationGroupScreen() {
		this.scene = constructScreen();

	}

	@Override
	public String fetchScreenTitle() {
		return "groupsScreen";
	}

	@Override
	public Pane createContent() {
		codeBox = new AnsarLabeledControlHBox<>("code", ControlType.Label);
		codeBox.insertValue(fetchCode());
		registrationDateBox = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
		registrationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		nameBox = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
		teacherBox = new AnsarLabeledControlHBox<>("teacher", ControlType.ComboBox);
		assistantBox = new AnsarLabeledControlHBox<>("assistant", ControlType.ComboBox);
		groupLevel = new AnsarLabeledControlHBox<>("groupLevel", ControlType.ComboBox);
		headerContentPane = new AnsarGridPane();
		headerContentPane.add(codeBox, 0, 0);
		headerContentPane.add(registrationDateBox, 1, 0);
		headerContentPane.add(nameBox, 0, 1);
		headerContentPane.add(groupLevel, 1, 1);
		headerContentPane.add(teacherBox, 0, 2);
		headerContentPane.add(assistantBox, 1, 2);
		return headerContentPane;
	}

	@Override
	public void updateRefFieldsData() {
		List<MemorizationTeacher> teachers = Persister.list(MemorizationTeacher.class);
		AnsarComboBox<MemorizationTeacher> teacherCombo = teacherBox.getControl();
		teacherCombo.config(teachers);
		AnsarComboBox<MemorizationTeacher> assistantCombo = assistantBox.getControl();
		assistantCombo.config(teachers);
		AnsarComboBox<GroupLevel> groupLevelCombo = groupLevel.getControl();
		groupLevelCombo.config(Persister.list(GroupLevel.class));
	}

	@Override
	public MemorizationGroup createEntity() {
		MemorizationGroup group = new MemorizationGroup();
		String code = codeBox.fetchValue();
		Long id = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).map(i -> i.getId())
				.findFirst().orElse(null);
		group.setId(id);
		group.setCode(code);
		group.setCreationDate(DateTimeUtility.parseDateTime(registrationDateBox.fetchValue()));
		group.setName(nameBox.fetchValue());
		group.setTeacher(teacherBox.fetchValue());
		group.setAssistantTeacher(assistantBox.fetchValue());
		group.setGroupLevel(groupLevel.fetchValue());
		group.setStudents(new ArrayList<>());
		return group;
	}

	@Override
	public AnsarTable<MemorizationGroup> createListViewTable() {
		table = new AnsarTable<>();
		table.setRowFactory(c -> {
			AnsarTableRow<MemorizationGroup> row = new AnsarTableRow<>();
			row.setOnMouseClicked(e -> {
				AnsarTableRow<MemorizationGroup> rowRef = row;
				selectRowAction(rowRef.getItem());
			});
			return row;
		});

		codeCol = new AnsarTableColumn<>("code");
		codeCol.config("code");
		creationDateCol = new AnsarTableColumn<>("creationDate");
		creationDateCol.config("creationDate");
		nameCol = new AnsarTableColumn<>("name");
		nameCol.config("name");
		teacherCol = new AnsarTableColumn<>("teacher");
		teacherCol.useBaseEntityConfiguration("teacher");
		assistantCol = new AnsarTableColumn<>("assistant");
		assistantCol.useBaseEntityConfiguration("assistantTeacher");
		groupLevelCol = new AnsarTableColumn<>("groupLevel");
		groupLevelCol.useBaseEntityConfiguration("groupLevel");
		table.getColumns().addAll(codeCol, creationDateCol, nameCol, teacherCol, assistantCol, groupLevelCol);
		return table;
	}

	@Override
	public void selectRowAction(MemorizationGroup item) {
		codeBox.insertValue(item.getCode());
		nameBox.insertValue(item.getName());
		registrationDateBox.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
		teacherBox.insertValue(item.getTeacher());
		assistantBox.insertValue(item.getAssistantTeacher());
		groupLevel.insertValue(item.getGroupLevel());
	}

	@Override
	public Class<MemorizationGroup> fetchDocumentClass() {
		return MemorizationGroup.class;
	}

	@Override
	public AnsarTable<MemorizationGroup> fetchTable() {
		return table;
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCodeBox() {
		return codeBox;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCreationDateBox() {
		return registrationDateBox;
	}
}
