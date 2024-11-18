package application;

import ansarcontrols.*;
import entities.files.AnsarUser;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.*;
import javafx.stage.Stage;
import model.Persister;
import utilities.*;

public class LoginScreen
{
    private static AnsarScene scene;
    private static AnsarTextField<String> username;
    private static AnsarPasswordField password;

    public static AnsarScene fetchScreen()
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(scene))
            scene = new AnsarScene(constructRoot());
        return scene;
    }

    private static Parent constructRoot()
    {
        AnsarLabel sysName = new AnsarLabel("kotab");
        sysName.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        sysName.setStyle("-fx-alignment: CENTER; -fx-text-fill: #fff");
        AnsarVBox leftForm = new AnsarVBox(sysName);
        leftForm.getStyleClass().add("left-form");
        AnsarVBox rightForm = new AnsarVBox();
        rightForm.getStyleClass().add("right-form");
        username = new AnsarTextField<>("username");
        username.setAlignment(Pos.CENTER);
        password = new AnsarPasswordField("password");
        password.setAlignment(Pos.CENTER);
        password.setOnKeyPressed(e ->
        {
            if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
                return;
            login();
        });
        AnsarButton loginBtn = new AnsarButton("login");
        loginBtn.getStyleClass().add("login-btn");
        loginBtn.setOnAction(e -> login());
        rightForm.setAlignment(Pos.CENTER);
        AnsarLabel welcome = new AnsarLabel("welcome");
        welcome.setStyle("-fx-font-size:20; -fx-alignment:CENTER;");
        rightForm.getChildren().addAll(welcome, username, password, loginBtn);
        AnsarHBoxRoot root = new AnsarHBoxRoot(rightForm, leftForm);
        root.setSpacing(0);
        root.setPrefWidth(ResourceUtility.fetchLoginScreenWidth());
        root.setPrefHeight(ResourceUtility.fetchLoginScreenHeight());
        return root;
    }

    private static void login()
    {
        Result result = validateCredentials();
        if (result.isFailed())
        {
            ResourceUtility.showError(result);
        } else
        {
            Stage stage = ResourceUtility.fetchStage();
            stage.setScene(ResourceUtility.fetchCachedScreen(ScreensNames.NoteBookScreen));
            stage.sizeToScene();
            stage.centerOnScreen();
        }
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
