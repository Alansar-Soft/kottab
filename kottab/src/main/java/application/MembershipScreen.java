package application;

import ansarcontrols.*;
import entities.documents.Membership;
import entities.files.Student;
import javafx.scene.layout.Pane;
import utilities.ObjectChecker;

public class MembershipScreen implements IAnsarScreen<Membership>
{
    private AnsarScene scene;
    private AnsarTable<Student> table;
    private AnsarTableColumn<Student, Student> studentCodeCol;
    private AnsarTableColumn<Student, String> studentNameCol;
    private AnsarTableColumn<Student, Boolean> payCol;
    private Student student;

    public MembershipScreen()
    {
        scene = constructScreen();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "membership";
    }

    @Override
    public Pane createContentBox()
    {
        table = new AnsarTable<>();
        studentCodeCol = new AnsarTableColumn<>("studentCode");
        studentCodeCol.config("code");
        studentNameCol = new AnsarTableColumn<>("name");
        studentNameCol.config("name");
        payCol = new AnsarTableColumn<>("pay");
       /* payCol.setCellFactory(col ->
        {
            AnsarTableCell<Membership, Boolean> cell = new AnsarTableCell<>()
            {
              private AnsarCheckBox payCheckBox = new AnsarCheckBox();

                @Override
                protected void updateItem(Boolean item, boolean empty)
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

                    setGraphic(payCheckBox);

                }
            };
            return cell;
        });*/

        table.getColumns().addAll(studentCodeCol, studentNameCol, payCol);
        return new AnsarVBox(new AnsarSearchableTable<>(Student.class, table));
    }

    @Override
    public Membership createEntity()
    {
        return null;
    }

    @Override
    public AnsarScene fetchScene()
    {
        return scene;
    }
}
