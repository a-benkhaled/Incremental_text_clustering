package ihm;
	
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GUI extends Application {
	@Override
	public void start(Stage primaryStage) {
		TextArea console = new TextArea();
		ToolBar toolbar = new ToolBar();

		
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    HBox statusbar = new HBox();
		    statusbar.setId("statusbar");
		    root.setTop(toolbar);
		    root.setCenter(console);
		    root.setBottom(statusbar);
			
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
