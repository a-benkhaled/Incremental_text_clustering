package ihm;


import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/*
 * Classe du menu "Fichier"
 * */
public class IHMenu extends MenuBar{
	private Menu fileMenu;
	private Menu affichageMenu;
	private Menu helpMenu;
	
	IHMenu(){
		super();
		fileMenu = new Menu_File("Fichier", this);
		affichageMenu = new Menu_Affichage("Affichage");
		affichageMenu.setDisable(true);
		helpMenu = new Menu_About("Aide");
		this.getMenus().addAll(fileMenu, affichageMenu,helpMenu);
	}

	public void setAffichageMenu(Menu am) {
		this.affichageMenu = am;
	}

	public Menu getAffichageMenu() {
		return affichageMenu;
	}
	
	
	
	
}
