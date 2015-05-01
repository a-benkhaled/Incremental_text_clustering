package ihm_form;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ConfirmForm extends Form{
	protected String modelName;
	protected String pathLearningSet;
	protected String pathTermSpaceSet;
	protected boolean stemming = false;
	protected String pathStopListe;
	protected String pathScript;
	protected char repType = 'f';//Type de représentation ('f', 'p', 'e')
	protected int repTypeWS = 0;//ensemble de mots fréquents
	protected float minSupp;
	protected float maxSupp;
	protected int minTermNb;
	protected int maxTermNb;
	protected float cutoff ;
	protected float acuity;
	
	public ConfirmForm() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public void create(){
		//Mise en forme
		this.addRow(++line, new Label("Nom du modèle: "), new Label(modelName));
		this.addRow(++line, new Label("Ensemble d'apprentissage: "), new Label(pathLearningSet));
		this.addRow(++line, new Label("Ensemble Vocabulaire: "), new Label(pathTermSpaceSet));
		this.addRow(++line, new Label("Radicalisation: "), new Label(stemming?"oui":"non"));
		this.addRow(++line, new Label("Stop list additionnelle: "), new Label(pathStopListe));
		this.addRow(++line, new Label("Script de prétraitement additionnel: "), new Label(pathScript));
		this.addRow(++line, new Label("Type de représentation: "), new Label(repType + ""));
		this.addRow(++line, new Label("Support minimal: "), new Label(minSupp +""));
		this.addRow(++line, new Label("Support maximal: "), new Label(maxSupp +""));
		this.addRow(++line, new Label("Nombre de terms minimal: "), new Label(minTermNb +""));
		this.addRow(++line, new Label("Nombre de terms maximal: "), new Label(maxTermNb +""));
		this.addRow(++line, new Label("Nombre de terms minimal: "), new Label(minTermNb +""));
		this.addRow(++line, new Label("Nombre de terms maximal: "), new Label(maxTermNb +""));
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
