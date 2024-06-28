package application;

import ansarcontrols.*;
import entities.AnsarUser;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import model.Persister;
import utilities.*;

public class LoginScreen
{
    private static AnsarScene scene;
    private static AnsarLabeledControlHBox<String> username;
    private static AnsarLabeledControlHBox<String> password;

    public static AnsarScene fetchScreen()
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(scene))
            scene = new AnsarScene(constructRoot());
        return scene;
    }

    private static Parent constructRoot()
    {
        AnsarVBoxRoot root = new AnsarVBoxRoot();
        username = new AnsarLabeledControlHBox<>("userName", ControlType.TextField);
        username.setAlignment(Pos.CENTER);
        password = new AnsarLabeledControlHBox<>("password", ControlType.PasswordField);
        password.setAlignment(Pos.CENTER);
        password.setOnKeyPressed(e ->
        {
            if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
                return;
            login();
        });
        AnsarButton loginBtn = new AnsarButton("login");
        loginBtn.setOnAction(e -> login());
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(username, password, loginBtn);
        return root;
    }

    private static void login()
    {
        Result result = validateCredentials();
        if (result.isFailed())
            ResourceUtility.showError(result);
        else ResourceUtility.fetchStage().setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
    }

    private static Result validateCredentials()
    {
        Result result = new Result();
        if (ObjectChecker.areEqual(username.fetchValue(), "admin") && ObjectChecker.areEqual(password.fetchValue(), "admin"))
            return result;
        result.accmulate(authenticate(ObjectChecker.toStringOrEmpty(LoginScreen.username.fetchValue()),
                LoginScreen.password.fetchValue()));
        return result;
    }

    public static Result authenticate(String userName, String password)
    {
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
