package ihm;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Fenêtre à propos
 */
public class Menu_About extends Menu {
	public Menu_About(String name) {
		// TODO Auto-generated constructor stub
		super(name);
		MenuItem about = new MenuItem("À propos de cette application");
		MenuItem guide = new MenuItem("Guide d'utilisation");

		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				aboutWindow();
			}

		});
		this.getItems().addAll(guide, about);
	}

	protected void aboutWindow() {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		stage.setTitle("À propos de cette application");
		Scene scene = new Scene(root, 350, 350);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.show();
	}
}
