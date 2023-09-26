package application;

import java.util.List;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.ControlType;
import entities.MemorizationGroup;
import entities.Person;
import entities.Student;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.CollectionsUtility;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

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
		parentFirstPhoneNo = new AnsarLabeledControlHBox("parentFirstPhoneNo", ControlType.TextField);
		parentSecondPhoneNo = new AnsarLabeledControlHBox("parentSecondPhoneNo", ControlType.TextField);
		group = new AnsarLabeledControlHBox("group", ControlType.ComboBox);
		AnsarComboBox<MemorizationGroup> groupsComboBox = (AnsarComboBox<MemorizationGroup>) group.getControl();
		List<MemorizationGroup> groups = Persister.list(MemorizationGroup.class);
		groupsComboBox.getItems().addAll(groups);
		groupsComboBox.setConverter(ResourceUtility.createStringConverter(g -> g.getName(), gName -> CollectionsUtility
				.fetchFirstMatched(groups, g -> ObjectChecker.areEqual(g.getName(), gName))));
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
		parentFirstPhoneNoCol.setCellValueFactory(new PropertyValueFactory<Student, String>("parentsFirstPhoneNo"));
		parentSecondPhoneNoCol = new AnsarTableColumn<>("parentSecondPhoneNo");
		parentSecondPhoneNoCol.setCellValueFactory(new PropertyValueFactory<>("parentsSecondPhoneNo"));
		groupCol = new AnsarTableColumn<>("group");
		groupCol.setCellValueFactory(new PropertyValueFactory<>("groupName"));
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
	public void reset() {
		super.reset();
		parentFirstPhoneNo.reset();
		parentSecondPhoneNo.reset();
		group.reset();
	}

	@Override
	public AnsarScene refreshScreen() {
		AnsarComboBox<MemorizationGroup> groupsComboBox = (AnsarComboBox<MemorizationGroup>) group.getControl();
		List<MemorizationGroup> groups = Persister.list(MemorizationGroup.class);
		groupsComboBox.insertItems(groups);
		return fetchScene();
	}
}
