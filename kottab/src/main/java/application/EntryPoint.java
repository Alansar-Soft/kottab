package application;

import entities.*;
import javafx.application.*;
import javafx.scene.image.Image;
import javafx.stage.*;
import model.Persister;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import restfulcontrollers.MobileController;
import utilities.*;

import java.math.BigDecimal;
import java.util.HashMap;

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
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.setTitle(Translator.translate("Project"));
        primaryStage.setScene(LoginScreen.fetchScreen());
        primaryStage.getIcons().add(new Image("/logo.jpg"));
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
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
        System.setProperty("prism.lcdtext", "false");
        System.setProperty("prism.text", "t2k");
        launch(args);
    }
}
