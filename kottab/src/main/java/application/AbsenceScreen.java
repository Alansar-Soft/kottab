package application;

import ansarcontrols.*;
import entities.*;
import javafx.scene.control.TableCell;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.ObjectChecker;

import java.time.LocalDate;
import java.util.List;

public class AbsenceScreen implements IDocumentScreen<AbsenceEntry>
{
    private AnsarScene scene;
    private boolean absence;
    private Student student;
    private List<AbsenceEntry> absenceEntries;

    public AbsenceScreen()
    {
        absenceEntries = Persister.list(AbsenceEntry.class, " WHERE creationDate = :creationDate",
                Persister.params("creationDate", LocalDate.now()));
        scene = constructScreen();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "absenceScreen";
    }

    @Override
    public Pane createContentBox()
    {
        AnsarTable<Student> table = new AnsarTable<>();
        AnsarTableColumn<Student, String> studentCodeCol = new AnsarTableColumn<>("studentCode");
        studentCodeCol.config("code");
        AnsarTableColumn<Student, String> studentNameCol = new AnsarTableColumn<>("studentName");
        studentNameCol.config("name");
        AnsarTableColumn<Student, String> groupNameCol = new AnsarTableColumn<>("groupName");
        groupNameCol.config("groupName");
        AnsarTableColumn<Student, String> absenceCol = new AnsarTableColumn<>("absence");
        absenceCol.setCellFactory(c ->
        {
            return new TableCell<Student, String>()
            {
                AnsarButton absenceBtn = new AnsarButton("absence");
                AnsarButton attendBtn = new AnsarButton("attend");

                {
                    absenceBtn.setOnAction(e ->
                    {
                        absence = true;
                        student = getTableRow().getItem();
                        submit();
                        absenceBtn.setDisable(absence);
                        attendBtn.setDisable(!absence);
                        table.refresh();
                    });
                    attendBtn.setOnAction(e ->
                    {
                        absence = false;
                        student = getTableRow().getItem();
                        submit();
                        absenceBtn.setDisable(absence);
                        attendBtn.setDisable(true);
                        table.refresh();
                    });
                }

                @Override
                protected void updateItem(String item, boolean empty)
                {
                    super.updateItem(item, empty);
                    if (empty)
                    {
                        setText(null);
                        setGraphic(null);
                        return;
                    }

                    if (getTableRow() == null || getTableRow().getItem() == null)
                        return;
                    Long studentId = getTableRow().getItem().getId();
                    boolean absence = absenceEntries.stream().map(l -> l.getStudent().getId())
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
    public AbsenceEntry submit()
    {
        AbsenceEntry absenceEntry = createEntity();
        if (absence)
        {
            Persister.saveOrUpdate(absenceEntry);
            absenceEntries.add(absenceEntry);
        } else
        {
            AbsenceEntry removed = absenceEntries.stream().filter(l -> ObjectChecker.areEqual(l.getStudent(), student))
                    .findFirst().orElse(null);
            absenceEntries.remove(removed);
            Persister.remove(removed);
        }
        return absenceEntry;
    }

    @Override
    public AbsenceEntry createEntity()
    {
        AbsenceEntry entry = new AbsenceEntry();
        entry.setStudent(student);
        entry.setGroup(student.getGroup());
        return entry;
    }

    @Override
    public AnsarScene fetchScene()
    {
        return scene;
    }

    @Override
    public void updateRefFieldsData()
    {
        absenceEntries = Persister.list(AbsenceEntry.class, " WHERE creationDate = :creationDate",
                Persister.params("creationDate", LocalDate.now()));
    }
}
