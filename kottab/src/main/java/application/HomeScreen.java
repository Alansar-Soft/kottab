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
		AnsarButton addTeacherBtn = new AnsarButton(ResourceUtility.translate("addTeacher"));
		addTeacherBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen));
		});
		AnsarButton addStudentBtn = new AnsarButton(ResourceUtility.translate("addStudent"));
		addStudentBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen));
		});
		AnsarButton addGroupBtn = new AnsarButton(ResourceUtility.translate("addGroup"));
		addGroupBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen));
		});
		AnsarButton memorizationNoteBookBtn = new AnsarButton(ResourceUtility.translate("memorizationNoteBook"));
		memorizationNoteBookBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
		});
		AnsarButton groupLevelBtn = new AnsarButton(ResourceUtility.translate("groupLevel"));
		groupLevelBtn.setOnAction(e -> {
			stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen));
		});
		root.getChildren().addAll(addTeacherBtn, addStudentBtn, addGroupBtn, memorizationNoteBookBtn, groupLevelBtn);
		return root;
	}
}
