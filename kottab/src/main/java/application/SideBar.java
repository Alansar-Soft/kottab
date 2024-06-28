package application;

import ansarcontrols.*;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utilities.*;

public class SideBar extends AnsarVBox
{
    private Stage stage;

    public SideBar()
    {
        setPrefWidth(150);
        setPadding(new Insets(10));
        setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        setSpacing(20);
        setStyle("-fx-border-style:solid; -fx-border-size: 2; -fx-border-color: black; -fx-background-color:#eeeee4; ");
        if (stage == null)
            stage = ResourceUtility.fetchStage();
        AnsarButton teacherBtn = new AnsarButton(Translator.translate("teachersScreen"));
        teacherBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen)));
        AnsarButton studentBtn = new AnsarButton(Translator.translate("studentsScreen"));
        studentBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen)));
        AnsarButton groupBtn = new AnsarButton(Translator.translate("groupsScreen"));
        groupBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen)));
        AnsarButton memorizationNoteBookBtn = new AnsarButton(Translator.translate("memorizationNoteBook"));
        memorizationNoteBookBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen)));
        AnsarButton groupLevelBtn = new AnsarButton(Translator.translate("levelsScreen"));
        groupLevelBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen)));
        AnsarButton userBtn = new AnsarButton(Translator.translate("usersScreen"));
        userBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.UserScreen)));
        AnsarButton absenceBtn = new AnsarButton(Translator.translate("absence"));
        absenceBtn.setOnAction(e ->
                stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.AbsenceScreen)));
        AnsarButton logoutBtn = new AnsarButton("logout");
        logoutBtn.setOnAction(e -> stage.setScene(LoginScreen.fetchScreen()));
        getChildren().addAll(teacherBtn, studentBtn, groupBtn, memorizationNoteBookBtn, groupLevelBtn, userBtn,
                absenceBtn, logoutBtn);
        getChildren().forEach(child->{
            Button btn = (Button) child;
            btn.setPrefSize(130,40);
        });
    }
}
