package application;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBoxRoot;
import ansarcontrols.AnsarScene;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class HomeScreen {
	private static AnsarScene scene;
	private static Stage stage;

	public HomeScreen() {
	}

	public static AnsarScene fetchScreen() {
		if (ObjectChecker.isEmptyOrZeroOrNull(scene))
			scene = new AnsarScene(constructRoot());
		return scene;
	}

	private static Parent constructRoot() {
		AnsarHBoxRoot root = new AnsarHBoxRoot();
		if (stage == null)
			stage = ResourceUtility.fetchStage();
		AnsarButton teacherBtn = new AnsarButton(ResourceUtility.translate("teachersScreen"));
		teacherBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen));
		});
		AnsarButton studentBtn = new AnsarButton(ResourceUtility.translate("studentsScreen"));
		studentBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen));
		});
		AnsarButton groupBtn = new AnsarButton(ResourceUtility.translate("groupsScreen"));
		groupBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen));
		});
		AnsarButton memorizationNoteBookBtn = new AnsarButton(ResourceUtility.translate("memorizationNoteBook"));
		memorizationNoteBookBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
		});
		AnsarButton groupLevelBtn = new AnsarButton(ResourceUtility.translate("levelsScreen"));
		groupLevelBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen));
		});
		AnsarButton userBtn = new AnsarButton(ResourceUtility.translate("usersScreen"));
		userBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.UserScreen));
		});
		AnsarButton absenceBtn = new AnsarButton(ResourceUtility.translate("absence"));
		absenceBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.AbsenceScreen));
		});
		AnsarButton logoutBtn = new AnsarButton("logout");
		logoutBtn.setOnAction(e -> stage.setScene(LoginScreen.fetchScreen()));
		root.getChildren().addAll(teacherBtn, studentBtn, groupBtn, memorizationNoteBookBtn, groupLevelBtn, userBtn,
				absenceBtn, logoutBtn);
		return root;
	}
}
