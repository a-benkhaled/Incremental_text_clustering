package ihm;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/*
 * Classe du menu "Fichier"
 * */
public class IHMenu extends MenuBar{
	private Menu fileMenu;
	private Menu helpMenu;
	
	IHMenu(){
		super();
		fileMenu = new Menu_Create("Fichier");
		helpMenu = new Menu_About("Aide");
		this.getMenus().addAll(fileMenu, helpMenu);
	}
}
