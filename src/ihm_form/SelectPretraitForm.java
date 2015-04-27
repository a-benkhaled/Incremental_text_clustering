package ihm_form;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

public class SelectPretraitForm extends Form {
	public SelectPretraitForm() {
		// TODO Auto-generated constructor stub
		lblSelect = new Label("S�lection et pr�traitement de document");lblSelect.setId("title");
		fcLearn = new Button("Ajouter");
		fcUniverse = new Button("Ajouter");
		cbStem = new CheckBox("Radicalisation (Stemming)");
		fcSL = new Button("Ajouter");
		fcScript = new Button("Ajouter");

		// S�lection de document
		this.add(lblSelect, 0, ++line, 2, 1);
		this.addRow(++line, new Label(
				"Ajouter � l'ensemble d'apprentissage(*)"), fcLearn);
		this.addRow(++line, new Label(
				"Ajouter au vocabulaire"), fcUniverse);

		// Param�tres de pr�traitement
		this.add(cbStem, 0, ++line);
		this.addRow(++line, new Label("Ajouter une liste de mots vides"),
				fcSL);
		this.addRow(++line, new Label(
				"Ajouter un script de pr�traitement"), fcScript);
		this.add(new Separator(), 0, ++line, 2, 1);
	}
	protected Label lblSelect;
	protected Button fcLearn;
	protected Button fcUniverse;
	protected Label lblPret;
	protected CheckBox cbStem;
	protected Button fcSL;
	protected Button fcScript;
}
