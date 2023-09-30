package application;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.ControlType;
import entities.MemorizationGroup;
import entities.Person;
import entities.Student;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Persister;

public class StudentScreen<T extends Student> extends AbsPersonScreen<Student> {
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
	public Pane createHeaderBox() {
		GridPane headerPane = (GridPane) super.createHeaderBox();
		parentFirstPhoneNo = new AnsarLabeledControlHBox<>("parentFirstPhoneNo", ControlType.TextField);
		parentSecondPhoneNo = new AnsarLabeledControlHBox<>("parentSecondPhoneNo", ControlType.TextField);
		group = new AnsarLabeledControlHBox<>("group", ControlType.ComboBox);
		AnsarComboBox<MemorizationGroup> groupsComboBox = group.getControl();
		groupsComboBox.config(Persister.list(MemorizationGroup.class));
		int rowNo = headerPane.getRowCount() - 1;
		headerPane.add(parentFirstPhoneNo, 2, rowNo);
		headerPane.add(parentSecondPhoneNo, 3, rowNo);
		headerPane.add(group, 0, ++rowNo);
		return headerPane;
	}

	@Override
	public AnsarTable<Student> createTableView() {
		AnsarTable<Student> table = super.createTableView();
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
	public Person createEntity() {
		return new Student();
	}

	@Override
	public Class<? extends Person> fetchDocumentClass() {
		return Student.class;
	}

	@Override
	public Student fetchFile() {
		Student entityToSave = super.fetchFile();
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

	@Override
	public AnsarScene refreshScreen() {
		((AnsarComboBox<MemorizationGroup>) group.getControl()).insertItems(Persister.list(MemorizationGroup.class));
		super.refreshScreen();
		return fetchScene();
	}
}
