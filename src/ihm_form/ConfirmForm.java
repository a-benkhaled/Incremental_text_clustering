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
	protected String modelName;
	protected String pathLearningSet;
	protected String pathTermSpaceSet;
	protected boolean stemming;
	protected String pathStopListe;
	protected String pathScript;
	protected char repType = 'f';//Type de représentation ('f', 'p', 'e')
	protected int repTypeWS = 0;//type de rep ds ensemble de mots fréquents
	protected char repTypeWSForm ;//ensemble de mots fréquents
	protected float minSupp;
	protected float maxSupp;
	protected int minTermNb;
	protected int maxTermNb;
	protected float cutoff ;
	protected float acuity;
	
	private String[] setValues  = {"Fréquences", "Poids (tf-idf)"};
	
	public ConfirmForm() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * Mise en place des widgets
	 */
	public void create(){
		//Mise en forme
		this.addRow(++line, new Label("Nom du modèle: "), new Label(modelName));
		this.addRow(++line, new Label("Ensemble d'apprentissage: "), new Label(pathLearningSet));
		this.addRow(++line, new Label("Radicalisation: "), new Label(stemming?"oui":"non"));
		if(!pathTermSpaceSet.isEmpty())
			this.addRow(++line, new Label("Ensemble Vocabulaire: "), new Label(pathTermSpaceSet));
		if(!pathStopListe.isEmpty())
			this.addRow(++line, new Label("Stop list additionnelle: "), new Label(pathStopListe));
		if(!pathScript.isEmpty())
			this.addRow(++line, new Label("Script de prétraitement additionnel: "), new Label(pathScript));
		if(repType=='e'){
			this.addRow(++line, new Label("Support minimal: "), new Label(minSupp +""));
			this.addRow(++line, new Label("Support maximal: "), new Label(maxSupp +""));
			this.addRow(++line, new Label("Nombre de terms minimal: "), new Label(minTermNb +""));
			this.addRow(++line, new Label("Nombre de terms maximal: "), new Label(maxTermNb +""));
			this.addRow(++line, new Label("Valeurs: "), new Label(setValues[repTypeWS]));
			if(repTypeWSForm =='c')
				this.addRow(++line, new Label("Type: "), new Label("Ensemble fermé"));
			if(repTypeWSForm == 'm')
				this.addRow(++line, new Label("Type: "), new Label("Ensemble maximal"));
		}else{
			if(repType == 'f')
				this.addRow(++line, new Label("Type de représentation: "), new Label("Fréquences"));
			if(repType == 'p')
				this.addRow(++line, new Label("Type de représentation: "), new Label("Poids (tf-idf)"));
				
		}
		this.addRow(++line, new Label("Cutoff: "), new Label(cutoff +""));
		this.addRow(++line, new Label("Acuity: "), new Label(acuity +""));
		
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setPathLearningSet(String pathLearningSet) {
		this.pathLearningSet = pathLearningSet;
	}

	public void setPathTermSpaceSet(String pathTermSpaceSet) {
		this.pathTermSpaceSet = pathTermSpaceSet;
	}

	public void setStemming(boolean stemming) {
		this.stemming = stemming;
	}

	public void setPathStopListe(String pathStopListe) {
		this.pathStopListe = pathStopListe;
	}

	public void setPathScript(String pathScript) {
		this.pathScript = pathScript;
	}

	public void setRepType(char repType) {
		this.repType = repType;
	}

	public void setRepTypeWS(int repTypeWS) {
		this.repTypeWS = repTypeWS;
	}

	
	public void setRepTypeWSForm(char repTypeWSForm) {
		this.repTypeWSForm = repTypeWSForm;
	}

	public void setMinSupp(float minSupp) {
		this.minSupp = minSupp;
	}

	public void setMaxSupp(float maxSupp) {
		this.maxSupp = maxSupp;
	}

	public void setMinTermNb(int minTermNb) {
		this.minTermNb = minTermNb;
	}

	public void setMaxTermNb(int maxTermNb) {
		this.maxTermNb = maxTermNb;
	}

	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

	public void setAcuity(float acuity) {
		this.acuity = acuity;
	}
}
