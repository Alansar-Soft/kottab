package application;

import java.time.LocalDate;
import java.util.List;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarSearchableTable;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarVBox;
import entities.AbsenceEntry;
import entities.Student;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class AbsenceScreen implements IDocumentScreen<AbsenceEntry> {
	private AnsarScene scene;
	private boolean absence;
	private Student student;
	private List<AbsenceEntry> list;

	public AbsenceScreen() {
		list = Persister.list(AbsenceEntry.class, " WHERE creationDate = :creationDate",
				Persister.params("creationDate", LocalDate.now()));
		scene = constructScreen();
	}

	@Override
	public String fetchScreenTitle() {
		return "absenceScreen";
	}

	@Override
	public Pane createContentBox() {
		AnsarTable<Student> table = new AnsarTable<>();
		AnsarTableColumn<Student, String> studentCodeCol = new AnsarTableColumn<>("studentCode");
		studentCodeCol.config("code");
		AnsarTableColumn<Student, String> studentNameCol = new AnsarTableColumn<>("studentName");
		studentNameCol.config("name");
		AnsarTableColumn<Student, String> groupNameCol = new AnsarTableColumn<>("groupName");
		groupNameCol.config("groupName");
		AnsarTableColumn<Student, String> absenceCol = new AnsarTableColumn<>("absence");
		absenceCol.setCellFactory(c -> {
			return new TableCell<Student, String>() {
				AnsarButton absenceBtn = new AnsarButton("absence");
				AnsarButton attendBtn = new AnsarButton("attend");
				{
					absenceBtn.setOnAction(e -> {
						absence = true;
						student = getTableRow().getItem();
						submit();
						absenceBtn.setDisable(absence);
						attendBtn.setDisable(!absence);
						table.refresh();
					});
					attendBtn.setOnAction(e -> {
						absence = false;
						student = getTableRow().getItem();
						submit();
						absenceBtn.setDisable(absence);
						attendBtn.setDisable(true);
						table.refresh();
					});
				}

				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
						setGraphic(null);
						return;
					}

					if (getTableRow() == null || getTableRow().getItem() == null)
						return;
					Long studentId = getTableRow().getItem().getId();
					boolean absence = list.stream().map(l -> l.getStudent().getId())
							.anyMatch(id -> ObjectChecker.areEqual(id, studentId));
					absenceBtn.setDisable(absence);
					attendBtn.setDisable(!absence);
					setGraphic(new AnsarHBox(absenceBtn, attendBtn));

				}

			};
		});
		table.getColumns().addAll(studentCodeCol, studentNameCol, groupNameCol, absenceCol);
		return new AnsarVBox(new AnsarSearchableTable<>(Student.class, table));
	}

	@Override
	public AbsenceEntry submit() {
		AbsenceEntry absenceEntry = createEntity();
		if (absence) {
			Persister.saveOrUpdate(absenceEntry);
			list.add(absenceEntry);
		} else {
			AbsenceEntry removed = list.stream().filter(l -> ObjectChecker.areEqual(l.getStudent(), student))
					.findFirst().orElse(null);
			list.remove(removed);
			Persister.remove(removed);
		}
		return absenceEntry;
	}

	@Override
	public AbsenceEntry createEntity() {
		AbsenceEntry entry = new AbsenceEntry();
		entry.setCreationDate(LocalDate.now());
		entry.setStudent(student);
		entry.setGroup(student.getGroup());
		return entry;
	}

	@Override
	public Pane createFooterBox() {
		AnsarHBox footerBox = (AnsarHBox) IDocumentScreen.super.createFooterBox();
		AnsarButton backBtn = new AnsarButton("back");
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		footerBox.getChildren().add(backBtn);
		return footerBox;
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

}
