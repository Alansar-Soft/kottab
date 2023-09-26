package application;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBoxRoot;
import ansarcontrols.AnsarScene;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class HomeScreen {
	private static AnsarScene scene;

	public HomeScreen() {
	}

	public static AnsarScene fetchScreen() {
		if (ObjectChecker.isEmptyOrNull(scene))
			scene = new AnsarScene(constructRoot(), ResourceUtility.fetchScreenWidth() - 100,
					ResourceUtility.fetchScreenHeight() - 100);
		return scene;
	}

	private static Parent constructRoot() {
		AnsarHBoxRoot root = new AnsarHBoxRoot();
		String id = ResourceUtility.id("addTeacher");
		AnsarButton addTeacherBtn = new AnsarButton(id);
		
		addTeacherBtn.setFont(Font.font("Times New Roman"));
		addTeacherBtn.setOnAction(e -> {
			System.out.println(id);
			ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.TeacherScreen));
		});
		AnsarButton addStudentBtn = new AnsarButton(ResourceUtility.id("addStudent"));
		addStudentBtn.setOnAction(e -> {
			ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.StudentScreen));
		});
		AnsarButton addGroupBtn = new AnsarButton(ResourceUtility.id("addGroup"));
		addGroupBtn.setOnAction(e -> {
			ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupScreen));
		});
		AnsarButton memorizationNoteBookBtn = new AnsarButton(ResourceUtility.id("memorizationNoteBook"));
		memorizationNoteBookBtn.setOnAction(e -> {
			ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
		});
		AnsarButton groupLevelBtn = new AnsarButton(ResourceUtility.id("groupLevel"));
		groupLevelBtn.setOnAction(e -> {
			ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.GroupLevelScreen));
		});

		root.getChildren().addAll(addTeacherBtn, addStudentBtn, addGroupBtn, memorizationNoteBookBtn, groupLevelBtn);
		return root;
	}
}
