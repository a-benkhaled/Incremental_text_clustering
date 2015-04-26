package ihm;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI extends Application {
	
	static ScrollPane scrollingView;
	static BorderPane subMainView;
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage.setScene(initScene());
		primaryStage.show();
	}
	
	private Scene initScene() {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 620, 675);
		/*
		infoView = new VBox();
		infoView.setPrefWidth(200);
		*/
		subMainView = new BorderPane();
		scrollingView = new ScrollPane();
		/*
		mainView = new GridPane();
		mainView.setHgap(10);
		mainView.setVgap(10);
		mainView.setPadding(new Insets(0, 10, 0, 10));
		scrollingView.setContent(mainView);
		*/
		subMainView.setCenter(scrollingView);
		HBox statusbar = new HBox();
		IHMenu menuBar = new IHMenu();
		
		/*Charger CSS*/
		try {
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    statusbar.setId("statusbar");
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
