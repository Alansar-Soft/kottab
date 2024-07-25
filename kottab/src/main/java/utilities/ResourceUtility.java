package utilities;

import ansarcontrols.*;
import application.*;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.util.*;

public class ResourceUtility
{
    private static double screenWidth;
    private static double screenHeight;

    public static double fetchScreenWidth()
    {
        if (screenWidth == 0)
            screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
        return screenWidth;
    }

    public static double fetchScreenHeight()
    {
        if (screenHeight == 0)
            screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
        return screenHeight;
    }

    public static double fetchLoginScreenWidth()
    {
        return fetchScreenWidth() / 3;
    }

    public static double fetchLoginScreenHeight()
    {
        return fetchScreenHeight() / 2.2;
    }

    private static Stage primaryStage;

    public static void cachePrimaryStage(Stage stage)
    {
        primaryStage = stage;
    }

    public static Stage fetchStage()
    {
        return primaryStage;
    }

    private static Map<ScreensNames, IAnsarScreen> cachedScreens = new LinkedHashMap<>();

    public static Scene fetchCachedScreen(ScreensNames screen)
    {
        return cachedScreens.get(screen) == null ? createScreen(screen) : cachedScreens.get(screen).refreshScreen();
    }

    private static AnsarScene createScreen(ScreensNames screen)
    {
        if (ObjectChecker.areEqual(screen, ScreensNames.StudentScreen))
        {
            StudentScreen studentScreen = new StudentScreen();
            cachedScreens.put(screen, studentScreen);
            return studentScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.TeacherScreen))
        {
            TeacherScreen teacherScreen = new TeacherScreen();
            cachedScreens.put(screen, teacherScreen);
            return teacherScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.GroupScreen))
        {
            MemorizationGroupScreen groupScreen = new MemorizationGroupScreen();
            cachedScreens.put(screen, groupScreen);
            return groupScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.NoteBookScreen))
        {
            RecitationNoteBookScreen notebookScreen = new RecitationNoteBookScreen();
            cachedScreens.put(screen, notebookScreen);
            return notebookScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.GroupLevelScreen))
        {
            GroupLevelScreen groupLevelScreen = new GroupLevelScreen();
            cachedScreens.put(screen, groupLevelScreen);
            return groupLevelScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.UserScreen))
        {
            UserScreen userScreen = new UserScreen();
            cachedScreens.put(screen, userScreen);
            return userScreen.fetchScene();
        }
        if (ObjectChecker.areEqual(screen, ScreensNames.AbsenceScreen))
        {
            AbsenceScreen absenceScreen = new AbsenceScreen();
            cachedScreens.put(screen, absenceScreen);
            return absenceScreen.fetchScene();
        }
        return null;
    }

    public static void showError(Result result)
    {
        AnsarToolTip messageWindow = new AnsarToolTip(result.getMessage());
        messageWindow.setAutoFix(true);
        messageWindow.setAutoHide(true);
        messageWindow.setMinWidth(250);
        messageWindow.setMinHeight(30);
        messageWindow.setFont(Font.font("Times New Romans", 20));
        messageWindow.setStyle("-fx-background-color: #ff8080;-fx-text-fill: black;");
        messageWindow.show(fetchStage());
    }

}
