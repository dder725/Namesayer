package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import Controllers.MainWindowController;
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

			DataBase.instantiateDataBase();
			mainWindowController.populateTableView();

			BorderPane root = new BorderPane();
			root.getChildren().add(p);
			Scene scene = new Scene(root, 700, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Name Sayer");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}

		fileSetUp();

	}


	public static void main(String[] args) {
		launch(args);
	}

	public void fileSetUp() {

		makeScript();
		try {
		String cmd = "$(realpath createFiles.sh)";
		ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
		Process process = builder.start();
		process.waitFor();
	} catch (IOException E) {
		E.printStackTrace();
	} catch (InterruptedException e1) {
		e1.printStackTrace();
	}

	}

	public void makeScript() {
		String workingDir = System.getProperty("user.dir");
		String code = "		#!/bin/bash\n" + 
				"\n" + 
				"\n" + 
				"		if [ ! -d UserAttempts ]; then\n" + 
				"		        mkdir "+workingDir+"/UserAttempts\n" + 
				"		        cd "+workingDir+"/names\n" + 
// Code to add a folder for each name into UserAttempts
//				"		        for x in ./*_1; do\n" + 
//				"					x=\"${x%_1}\"\n" + 
//				"		        	mkdir \"${x}_attempts\" && mv \"${x}_attempts\" "+workingDir+"/UserAttempts\n" + 
//				"		        done\n" + 
				"		fi";

		byte[] content = code.getBytes(Charset.forName("UTF-8"));
		FileOutputStream helper;
		try {
			helper = new FileOutputStream("createFiles.sh");
			helper.write(content);
			helper.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			String cmd = "chmod +x $(realpath createFiles.sh)";
			ProcessBuilder builder = new ProcessBuilder("bash", "-c", cmd);
			Process process = builder.start();
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
