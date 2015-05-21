package ihm_form;

import java.awt.Button;

import text_clustering.IncrementalClustering;
import word_mining.PatternMiner;
import doc.Indexer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ConfirmForm extends Form{
	public int line = 0;
	private String[] setValues  = {"Fréquences", "Poids (tf-idf)"};
	private IncrementalClustering classit;
	
	public ConfirmForm(IncrementalClustering c) {
		// TODO Auto-generated constructor stub
		super();
		classit = c;
	}
	
	/**
	 * Mise en place des widgets
	 */
	public void create(){
		//Mise en forme
		this.addRow(++line, new Label("Nom du modèle: "), new Label(classit.getModelName()));
		this.addRow(++line, new Label("Ensemble d'apprentissage: "), new Label(
				classit.getPathLearningSet()));
		this.addRow(++line, new Label("Radicalisation: "), new Label(
				classit.getStemming() ? "oui" : "non"));
		if (!classit.getPathTermSpaceSet().isEmpty())
			this.addRow(++line, new Label("Ensemble Vocabulaire: "), new Label(
					classit.getPathTermSpaceSet()));
		if (!classit.getPathStopListe().isEmpty())
			this.addRow(++line, new Label("Stop list additionnelle: "),
					new Label(classit.getPathStopListe()));
		if (!classit.getPathScript().isEmpty())
			this.addRow(++line, new Label(
					"Script de prétraitement additionnel: "), new Label(
							classit.getPathScript()));
		if (classit.getRepType() == 'e') {
			this.addRow(++line, new Label("Support minimal: "), new Label(
					classit.getMinSupp() + ""));
			this.addRow(++line, new Label("Support maximal: "), new Label(
					classit.getMaxSupp() + ""));
			this.addRow(++line, new Label("Nombre de terms minimal: "),
					new Label(classit.getMinTermNb() + ""));
			this.addRow(++line, new Label("Nombre de terms maximal: "),
					new Label(classit.getMaxTermNb() + ""));
			this.addRow(++line, new Label("Valeurs: "), new Label(
					setValues[classit.getRepTypeWS()]));
			if (classit.getRepTypeWSForm() == 'c')
				this.addRow(++line, new Label("Type: "), new Label(
						"Ensemble fermé"));
			if (classit.getRepTypeWSForm() == 'm')
				this.addRow(++line, new Label("Type: "), new Label(
						"Ensemble maximal"));
		} else {
			if (classit.getRepType() == 'f')
				this.addRow(++line, new Label("Type de représentation: "),
						new Label("Fréquences"));
			if (classit.getRepType() == 'p')
				this.addRow(++line, new Label("Type de représentation: "),
						new Label("Poids (tf-idf)"));

			this.addRow(++line, new Label("Cutoff: "), new Label(classit.getCutoff() + ""));
			this.addRow(++line, new Label("Acuity: "), new Label(classit.getAcuity() + ""));
		}
	}
}
