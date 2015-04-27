package ihm_form;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;

public class SelectPretraitForm extends Form {
	public SelectPretraitForm() {
		// TODO Auto-generated constructor stub
		lblSelect = new Label("Sélection et prétraitement de document");lblSelect.setId("title");
		fcLearn = new Button("Ajouter");
		fcUniverse = new Button("Ajouter");
		cbStem = new CheckBox("Radicalisation (Stemming)");
		fcSL = new Button("Ajouter");
		fcScript = new Button("Ajouter");

		// Sélection de document
		this.add(lblSelect, 0, ++line, 2, 1);
		this.addRow(++line, new Label(
				"Ajouter à l'ensemble d'apprentissage(*)"), fcLearn);
		this.addRow(++line, new Label(
				"Ajouter au vocabulaire"), fcUniverse);

		// Paramètres de prétraitement
		this.add(cbStem, 0, ++line);
		this.addRow(++line, new Label("Ajouter une liste de mots vides"),
				fcSL);
		this.addRow(++line, new Label(
				"Ajouter un script de prétraitement"), fcScript);
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
