package application;

import java.util.HashMap;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Persister;
import restfulcontrollers.MobileController;
import utilities.ResourceUtility;

@SpringBootApplication
@ComponentScan(basePackageClasses = MobileController.class)
public class EntryPoint extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		HashMap<String, Object> params = new HashMap<>();
		params.put("server.port", 8888);
		new SpringApplicationBuilder().properties(params).sources(EntryPoint.class).headless(false).run();
		ResourceUtility.cachePrimaryStage(primaryStage);
		primaryStage.setTitle(ResourceUtility.translate("kotab"));
		primaryStage.setScene(LoginScreen.fetchScreen());
		primaryStage.setOnCloseRequest(e -> Persister.stopDBConnection());
		primaryStage.setWidth(ResourceUtility.fetchScreenWidth());
		primaryStage.setHeight(ResourceUtility.fetchScreenHeight());
		primaryStage.setMaximized(true);
		primaryStage.getIcons().add(new Image("/logo.jpg"));
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		Persister.startDBConnection();
		launch(args);
	}
}
