package ihm;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/*
 * Classe du menu "Fichier"
 * */
public class IHMenu extends MenuBar{
	private Menu fileMenu;
	private Menu helpMenu;
	
	IHMenu(){
		super();
		fileMenu = initFileMenu();
		helpMenu = initHelpMenu();
		this.getMenus().addAll(fileMenu, helpMenu);
	}


	private Menu initHelpMenu() {
		Menu tmp = new Menu("Aide");
		MenuItem about = new MenuItem("À propos de cette application");
		MenuItem guide = new MenuItem("Guide d'utilisation");
		/*
		about.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Label title = new Label("À propos de cette application");
				title.setFont(new Font("Verdana Bold", 22));
				Label text = new Label("aaaaaaaaaaaa aaaa aaa");
				text.setWrapText(true);
				text.setTextAlignment(TextAlignment.JUSTIFY);
				output.getChildren().addAll(title, text);
				
			}
			
		});
		*/
		tmp.getItems().addAll(guide, about);
		return tmp;
	}

	/**
	 * Menu "Fichier"
	 * 
	 */
	private Menu initFileMenu() {
		Menu tmp = new Menu("Fichier");
		MenuItem newModel = new MenuItem("Créer un nouvel modèle");
		MenuItem loadModel = new MenuItem("Charger un modèle");
		MenuItem saveModel = new MenuItem("Savegrder le modèle");
		MenuItem exit = new MenuItem("Quitter");
		tmp.getItems().addAll(newModel, loadModel, saveModel, new SeparatorMenuItem(), exit);
		
		newModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
		return tmp;
	}
}
