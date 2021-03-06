package ihm;
	
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GUI extends Application {
	
	//static ScrollPane scrollingView;
	public static BorderPane subMainView;
	//public static BorderPane infoMainView;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(initScene());
		primaryStage.show();
	}
	
	private Scene initScene() {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 670, 675);
		subMainView = new BorderPane();subMainView.setId("mainPane");
		
		HBox statusbar = new HBox();
		IHMenu menuBar = new IHMenu();
		statusbar.setPrefHeight(20);
		/*Charger CSS*/
		try {
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    statusbar.setId("statusbar");
		    menuBar.setId("menubar");
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*InfoView*/
		
		
		/*MainView*/
		
		/*ROOT*/
		root.setTop(menuBar);
	    root.setCenter(subMainView);
		root.setBottom(statusbar);
		return scene;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
