package application;

import ansarcontrols.*;
import javafx.scene.Parent;
import javafx.stage.Stage;
import utilities.*;

public class HomeScreen
{
    private static AnsarScene scene;
    private static Stage stage;

    public HomeScreen()
    {
    }

    public static AnsarScene fetchScreen()
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(scene))
            scene = new AnsarScene(constructRoot());
        return scene;
    }

    private static Parent constructRoot()
    {
        AnsarHBoxRoot root = new AnsarHBoxRoot();
        if (stage == null)
            stage = ResourceUtility.fetchStage();
        AnsarButton teacherBtn = new AnsarButton(Translator.translate("teachersScreen"));
        teacherBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen));
        });
        AnsarButton studentBtn = new AnsarButton(Translator.translate("studentsScreen"));
        studentBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen));
        });
        AnsarButton groupBtn = new AnsarButton(Translator.translate("groupsScreen"));
        groupBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen));
        });
        AnsarButton memorizationNoteBookBtn = new AnsarButton(Translator.translate("memorizationNoteBook"));
        memorizationNoteBookBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
        });
        AnsarButton groupLevelBtn = new AnsarButton(Translator.translate("levelsScreen"));
        groupLevelBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen));
        });
        AnsarButton userBtn = new AnsarButton(Translator.translate("usersScreen"));
        userBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.UserScreen));
        });
        AnsarButton absenceBtn = new AnsarButton(Translator.translate("absence"));
        absenceBtn.setOnAction(e ->
        {
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.AbsenceScreen));
        });
        AnsarButton logoutBtn = new AnsarButton("logout");
        logoutBtn.setOnAction(e -> stage.setScene(LoginScreen.fetchScreen()));
        root.getChildren().addAll(teacherBtn, studentBtn, groupBtn, memorizationNoteBookBtn, groupLevelBtn, userBtn,
                absenceBtn, logoutBtn);
        return root;
    }
}
