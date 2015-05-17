package ihm_form;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import text_clustering.IncrementalClustering;

public class LoadForm extends Form {

	private IncrementalClustering classit;
	private int line = 0;
	private String[] setValues  = {"Fréquences", "Poids (tf-idf)"};
	public LoadForm(IncrementalClustering c) {
		// Mise en forme
		Button btnAddDocx = new Button("Ajouter des documents");
		classit = c;
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
		}
		
		this.addRow(++line, new Label("Cutoff: "), new Label((float)classit.getCutoff() + ""));
		this.addRow(++line, new Label("Acuity: "), new Label(classit.getAcuity() + ""));
		this.addRow(++line, new Label("Nombre de documents: "), 
				new Label(classit.getIndex().getNumberOfDoc() + ""));
		this.addRow(++line, new Label("Taille du vocabulaire: "), 
				new Label(classit.getIndex().getTermSpace().size() + ""));
		
		this.addRow(++line, btnAddDocx);
		btnAddDocx.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				DirectoryChooser  dc = new DirectoryChooser ();
				String path;
				dc.setTitle("Ajouter ...");
				Stage stage = new Stage();
				File selectedFile = dc.showDialog(stage);
				if(selectedFile != null){
					path = selectedFile.getAbsolutePath()+"\\";
					classit.getIndex().incIndex(path);
					//Clustering incrémental
					classit.incClusterInstances(classit.getIndex());
					FileWriter fstream;
					try {
						System.out.println(classit.graph());
						fstream = new FileWriter("data\\graph\\"+classit.getModelName()+".graph");
						BufferedWriter out = new BufferedWriter(fstream);
						out.write(classit.graph());
						out.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

}
