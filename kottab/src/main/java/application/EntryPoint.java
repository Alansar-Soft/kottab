package application;

import java.util.HashMap;

import com.sun.javafx.application.LauncherImpl;
import javafx.stage.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import model.Persister;
import restfulcontrollers.MobileController;
import utilities.*;

@SpringBootApplication
@ComponentScan(basePackageClasses = MobileController.class)
public class EntryPoint extends Application
{
    @Override
    public void init() throws Exception
    {
        Persister.startDBConnection();
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("server.port", 8888);
        new SpringApplicationBuilder().properties(params).sources(EntryPoint.class).headless(false).run();
        ResourceUtility.cachePrimaryStage(primaryStage);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle(Translator.translate("kotab"));
        primaryStage.setScene(LoginScreen.fetchScreen());
        primaryStage.setWidth(ResourceUtility.fetchScreenWidth());
        primaryStage.setHeight(ResourceUtility.fetchScreenHeight());
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("/logo.jpg"));
        primaryStage.setOnCloseRequest(e ->
        {
            Persister.stopDBConnection();
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
