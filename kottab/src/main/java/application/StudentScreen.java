package application;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.ControlType;
import entities.files.MemorizationGroup;
import entities.files.Person;
import entities.files.Student;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Persister;

public class StudentScreen extends AbsPersonScreen<Student> {
	private AnsarLabeledControlHBox<String> parentFirstPhoneNo;
	private AnsarLabeledControlHBox<String> parentSecondPhoneNo;
	private AnsarTableColumn<Student, String> parentFirstPhoneNoCol;
	private AnsarTableColumn<Student, String> parentSecondPhoneNoCol;
	private AnsarLabeledControlHBox<MemorizationGroup> group;
	private AnsarTableColumn<Student, MemorizationGroup> groupCol;

	public StudentScreen() {
		AnsarScene scene = constructScreen();
		setScene(scene);
	}

	@Override
	public String fetchScreenTitle() {
		return "studentsScreen";
	}

	@Override
	public Pane createContent() {
		GridPane headerPane = (GridPane) super.createContent();
		parentFirstPhoneNo = new AnsarLabeledControlHBox<>("parentFirstPhoneNo", ControlType.TextField);
		parentSecondPhoneNo = new AnsarLabeledControlHBox<>("parentSecondPhoneNo", ControlType.TextField);
		group = new AnsarLabeledControlHBox<>("group", ControlType.ComboBox);
		int rowNo = headerPane.getRowCount();
		headerPane.add(parentFirstPhoneNo, 0, rowNo);
		headerPane.add(parentSecondPhoneNo, 1, rowNo);
		headerPane.add(group, 0, ++rowNo);
		return headerPane;
	}

	@Override
	public void updateRefFieldsData() {
		AnsarComboBox<MemorizationGroup> groupsComboBox = group.getControl();
		groupsComboBox.config(Persister.list(MemorizationGroup.class));
	}

	@Override
	public AnsarTable<Student> createListViewTable() {
		AnsarTable<Student> table = super.createListViewTable();
		parentFirstPhoneNoCol = new AnsarTableColumn<>("parentFirstPhoneNo");
		parentFirstPhoneNoCol.config("parentsFirstPhoneNo");
		parentSecondPhoneNoCol = new AnsarTableColumn<>("parentSecondPhoneNo");
		parentSecondPhoneNoCol.config("parentsSecondPhoneNo");
		groupCol = new AnsarTableColumn<>("group");
		groupCol.useBaseEntityConfiguration("group");
		table.getColumns().addAll(parentFirstPhoneNoCol, parentSecondPhoneNoCol, groupCol);
		return table;
	}

	@Override
	public Person createPersonEntity() {
		return new Student();
	}

	@Override
	public Class<? extends Person> fetchDocumentClass() {
		return Student.class;
	}

	@Override
	public Student createEntity() {
		Student entityToSave = super.createEntity();
		entityToSave.setParentsFirstPhoneNo(parentFirstPhoneNo.fetchValue());
		entityToSave.setParentsSecondPhoneNo((String) parentSecondPhoneNo.fetchValue());
		entityToSave.setGroup(group.fetchValue());
		return entityToSave;
	}

	@Override
	public void selectRowAction(Student student) {
		super.selectRowDefaultAction(student);
		parentFirstPhoneNo.insertValue(student.getParentsFirstPhoneNo());
		parentSecondPhoneNo.insertValue(student.getParentsSecondPhoneNo());
		group.insertValue(student.getGroup());
	}
}
