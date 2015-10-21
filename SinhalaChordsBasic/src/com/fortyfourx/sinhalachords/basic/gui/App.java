package com.fortyfourx.sinhalachords.basic.gui;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class App extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("app.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, Color.WHITE);
			scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
			
			AppController controller = loader.getController();
			controller.setStage(primaryStage);
			controller.setScene(scene);			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
