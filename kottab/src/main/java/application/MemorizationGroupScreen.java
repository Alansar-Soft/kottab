package application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ansarcontrols.AnsarButton;
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
import utilities.ResourceUtility;

public class MemorizationGroupScreen implements IFileScreen<MemorizationGroup> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> codeBox;
	private AnsarLabeledControlHBox<String> creationDateBox;
	private AnsarLabeledControlHBox<String> nameBox;
	private AnsarLabeledControlHBox<MemorizationTeacher> teacherBox;
	private AnsarLabeledControlHBox<MemorizationTeacher> assistantBox;
	private AnsarLabeledControlHBox<GroupLevel> groupLevel;
	private AnsarGridPane headerPane;
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
	public Pane createHeaderBox() {
		codeBox = new AnsarLabeledControlHBox<>("code", ControlType.Label);
		codeBox.insertValue(fetchCode());
		creationDateBox = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
		creationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		nameBox = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
		teacherBox = new AnsarLabeledControlHBox<>("teacher", ControlType.ComboBox);
		List<MemorizationTeacher> teachers = Persister.list(MemorizationTeacher.class);
		AnsarComboBox<MemorizationTeacher> teacherCombo = teacherBox.getControl();
		teacherCombo.config(teachers);
		assistantBox = new AnsarLabeledControlHBox<>("assistant", ControlType.ComboBox);
		AnsarComboBox<MemorizationTeacher> assistantCombo = assistantBox.getControl();
		assistantCombo.config(teachers);
		groupLevel = new AnsarLabeledControlHBox<>("groupLevel", ControlType.ComboBox);
		AnsarComboBox<GroupLevel> groupLevelCombo = groupLevel.getControl();
		groupLevelCombo.config(Persister.list(GroupLevel.class));
		headerPane = new AnsarGridPane();
		headerPane.add(codeBox, 0, 0);
		headerPane.add(creationDateBox, 1, 0);
		headerPane.add(nameBox, 0, 1);
		headerPane.add(groupLevel, 1, 1);
		headerPane.add(teacherBox, 0, 2);
		headerPane.add(assistantBox, 1, 2);
		return headerPane;
	}

	@Override
	public Pane createBtnsBox() {
		btnsBox = new AnsarHBox();
		AnsarButton saveBtn = new AnsarButton("Save");
		AnsarButton backBtn = new AnsarButton("Back");
		saveBtn.setOnAction(e -> submit());
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, backBtn);
		return btnsBox;
	}

	@Override
	public MemorizationGroup fetchFile() {
		Long code = Long.valueOf(codeBox.fetchValue());
		MemorizationGroup group = table.getItems().stream().filter(g -> ObjectChecker.areEqual(g.getCode(), code))
				.findFirst().orElse(null);
		if (ObjectChecker.isEmptyOrNull(group)) {
			group = new MemorizationGroup();
			table.getItems().add(group);
		}
		group.setCode(code);
		group.setCreationDate(DateTimeUtility.parseDateTime(creationDateBox.fetchValue()));
		group.setName(nameBox.fetchValue());
		group.setTeacher(teacherBox.fetchValue());
		group.setAssistantTeacher(assistantBox.fetchValue());
		group.setGroupLevel(groupLevel.fetchValue());
		group.setStudents(new ArrayList<>());
		return group;
	}

	@Override
	public void reset() {
		IFileScreen.super.reset();
		codeBox.insertValue(fetchCode());
		creationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
	}

	@Override
	public AnsarTable<MemorizationGroup> createTableView() {
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
		table.getItems().addAll(Persister.list(fetchDocumentClass()));
		return table;
	}

	@Override
	public void selectRowAction(MemorizationGroup item) {
		codeBox.insertValue(item.getCode());
		nameBox.insertValue(item.getName());
		creationDateBox.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
		teacherBox.insertValue(item.getTeacher());
		assistantBox.insertValue(item.getAssistantTeacher());
		groupLevel.insertValue(item.getGroupLevel());
	}

	@Override
	public Class<MemorizationGroup> fetchDocumentClass() {
		return MemorizationGroup.class;
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	@Override
	public AnsarScene refreshScreen() {
		List<MemorizationTeacher> teachers = Persister.list(MemorizationTeacher.class);
		((AnsarComboBox<MemorizationTeacher>) teacherBox.getControl()).insertItems(teachers);
		((AnsarComboBox<MemorizationTeacher>) assistantBox.getControl()).insertItems(teachers);
		reset();
		return scene;
	}

}
