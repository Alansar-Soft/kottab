package application;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarVBoxRoot;
import ansarcontrols.ControlType;
import entities.AnsarUser;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Result;

public class LoginScreen {
	private static AnsarScene scene;
	private static AnsarLabeledControlHBox<String> userName;
	private static AnsarLabeledControlHBox<String> password;

	public static AnsarScene fetchScreen() {
		if (ObjectChecker.isEmptyOrZeroOrNull(scene))
			scene = new AnsarScene(constructRoot());
		return scene;
	}

	private static Parent constructRoot() {
		AnsarVBoxRoot root = new AnsarVBoxRoot();
		userName = new AnsarLabeledControlHBox<>("userName", ControlType.TextField);
		userName.setAlignment(Pos.CENTER);
		password = new AnsarLabeledControlHBox<>("password", ControlType.PasswordField);
		password.setAlignment(Pos.CENTER);
		password.setOnKeyPressed(e -> {
			if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
				return;
			loginAction();
		});
		AnsarButton loginBtn = new AnsarButton("login");
		loginBtn.setOnAction(e -> loginAction());
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(userName, password, loginBtn);
		return root;
	}

	private static void loginAction() {
		Result result = authenticate(ObjectChecker.toStringOrEmpty(LoginScreen.userName.fetchValue()),
				LoginScreen.password.fetchValue());
		if (result.isFailed()) {
			ResourceUtility.showError(result);
			return;
		}
		ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen());
	}

	public static Result authenticate(String userName, String password) {
		Result result = new Result();
		if (ObjectChecker.isEmptyOrZeroOrNull(userName))
			result.failure("Please enter username");
		if (ObjectChecker.isEmptyOrZeroOrNull(password))
			result.failure("Please enter password");
		if (result.isFailed())
			return result;
		AnsarUser user = Persister.getSingleResult(
				"FROM " + AnsarUser.class.getSimpleName() + " WHERE userName=:userName",
				Persister.params("userName", userName));
		if (user == null)
			result.failure("Invalid username");
		if (user != null && ObjectChecker.areNotEqual(user.getPassword(), password))
			result.failure("Invalid password");
		if (result.isFailed())
			return result;
		return result;
	}
}
