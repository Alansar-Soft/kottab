package application;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Persister;
import utilities.ResourceUtility;

public class EntryPoint extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		ResourceUtility.cachePrimaryStage(primaryStage);
		primaryStage.setTitle(ResourceUtility.translate("kotab"));
		primaryStage.setScene(HomeScreen.fetchScreen());
		primaryStage.setOnCloseRequest(e -> Persister.stopDBConnection());
		primaryStage.setWidth(ResourceUtility.fetchScreenWidth());
		primaryStage.setHeight(ResourceUtility.fetchScreenHeight());
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Persister.startDBConnection();
		launch(args);
	}
}
