package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Nowhere extends Application
{
	public static void main(String[] args)
	{
		Application.launch(Nowhere.class, (java.lang.String[])null);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		AnchorPane page = (AnchorPane) FXMLLoader.load(Nowhere.class.getResource("Nowhere.fxml"));
		page.getStylesheets().add("controller/stylesheet.css");
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Nowhere");
        primaryStage.show();
	}
}
