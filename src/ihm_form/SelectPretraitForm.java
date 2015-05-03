package ihm_form;

import ihm.GUI;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class SelectPretraitForm extends Form {

	protected StringBuilder pathLearnSet;
	protected StringBuilder pathUniverse;
	protected StringBuilder pathStopListe;
	protected StringBuilder pathScript;

	public SelectPretraitForm() {
		// TODO Auto-generated constructor stub
		lblSelect = new Label("S�lection et pr�traitement de document");
		lblSelect.setId("title");
		fcLearn = new Button("Ajouter");
		fcUniverse = new Button("Ajouter");
		cbStem = new CheckBox("Radicalisation (Stemming)");
		fcSL = new Button("Ajouter");
		fcScript = new Button("Ajouter");
		pathLearnSet = new StringBuilder();
		pathUniverse = new StringBuilder();
		pathStopListe = new StringBuilder();
		pathScript = new StringBuilder();
		
		// S�lection de document
		this.add(lblSelect, 0, ++line, 2, 1);
		this.addRow(++line,
				new Label("R�pertoire de l'ensemble d'apprentissage"), fcLearn);
		this.addRow(++line, new Label("R�pertoire du vocabulaire"), fcUniverse);

		// Param�tres de pr�traitement
		this.add(cbStem, 0, ++line);
		this.addRow(++line, new Label("Ajouter une liste de mots vides"), fcSL);
		this.addRow(++line, new Label("Ajouter un script de pr�traitement"),
				fcScript);
		this.add(new Separator(), 0, ++line, 2, 1);
		
		//Valeurs par d�faut
		pathLearnSet.append("data\\train\\");
		cbStem.setSelected(true);
		
		runtimeEventsHandler();
	}

	/**
	 * Prise en charge des �v�nements de type temps r�el
	 */
	private void runtimeEventsHandler() {
		// TODO Auto-generated method stub
		// Ensemble d'apprentissage
		fcLearn.setOnMouseEntered(
				new MouseEnteredInfo("Choisir le r�pertoire contenant\n"
									+"votre corpus.\nLe r�pertoire par\n"
									+"d�faut est data\\train\n"));
		fcLearn.setOnMouseExited(new MouseExitedInfo());
		fcLearn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// Choisir un r�pertoire
				DirectoryChooser  dc = new DirectoryChooser ();
				dc.setTitle("Ajouter � l'ensemble d'apprentissage");
				File selectedFile = dc.showDialog(formTmpStage);
				if(selectedFile != null){
						pathLearnSet.replace(0, pathLearnSet.length(), 
								selectedFile.getAbsolutePath()+"\\");
					}
			}
		});

		// Vocabulaire
		fcUniverse.setOnMouseEntered(new MouseEnteredInfo("Il est possible d'�tendre le \n"
				+ "vocabulaire du syst�me en ajoutant\ndes documents.\n"
				+ "Le vocabulaire par d�faut est\ncelui de l'ensemble d'apprentissage"));
		fcUniverse.setOnMouseExited(new MouseExitedInfo());
		fcUniverse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DirectoryChooser  dc = new DirectoryChooser ();
				dc.setTitle("Ajouter au vocabulaire");
				File selectedFile = dc.showDialog(formTmpStage);
				if(selectedFile != null){
					pathUniverse.append(selectedFile.getAbsolutePath()+"\\");
				}
			}
		});
		
		//liste de mots vides
		fcSL.setOnMouseEntered(new MouseEnteredInfo("Si le domaine d'application du\n"
				+ "syst�me contient des mots\n"
				+ "vides qui lui sont propre.\n"
				+ "Vous avez la possibilit� de\nles ajouter"));
		fcSL.setOnMouseExited(new MouseExitedInfo());
		fcSL.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DirectoryChooser  dc = new DirectoryChooser ();
				dc.setTitle("Ajouter une stop list");
				File selectedFile = dc.showDialog(formTmpStage);
				if(selectedFile != null){
					pathStopListe.append(selectedFile.getAbsolutePath()+"\\");
				}
			}
		});
		
		//script de pr�traitementfcSL.setOnMouseEntered(
		fcScript.setOnMouseEntered(new MouseEnteredInfo("Il est possible d'int�grer votre\n"
				+ "propre script de pr�traitement.\n"
				+ "Cette option permet d'adapter\n"
				+ "le pr�traitement\n � diff�rentes\n"
				+ "natures types de corpus"));
		fcScript.setOnMouseExited(new MouseExitedInfo());
		fcScript.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DirectoryChooser  dc = new DirectoryChooser ();
				dc.setTitle("Ajouter un script de pr�traitement");
				File selectedFile = dc.showDialog(formTmpStage);
				if(selectedFile != null){
					pathScript.append(selectedFile.getAbsolutePath()+ "\\");
				}
			}
		});
		
	}
	
	public String getPathLearnSet() {
		return pathLearnSet.toString();
	}

	public String getPathUniverse() {
		return pathUniverse.toString();
	}

	public String getPathStopListe() {
		return pathStopListe.toString();
	}

	public String getPathScript() {
		return pathScript.toString();
	}
	
	public boolean getRadicalisation() {
		return cbStem.isSelected();
	}

	protected Label lblSelect;
	protected Button fcLearn;
	protected Button fcUniverse;
	protected Label lblPret;
	protected CheckBox cbStem;
	protected Button fcSL;
	protected Button fcScript;
}
