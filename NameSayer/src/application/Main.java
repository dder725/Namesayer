package application;

import GUI.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Pane p = fxmlLoader.load(getClass().getResource("/GUI/MainWindow.fxml").openStream());
			MainWindowController mainWindowController = (MainWindowController) fxmlLoader.getController();
			
			BorderPane root = new BorderPane();
			root.getChildren().add(p);
			Scene scene = new Scene(root,726,573);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
