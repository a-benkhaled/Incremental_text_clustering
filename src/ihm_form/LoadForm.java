package ihm_form;

import ihm.Menu_File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import text_clustering.EvalNode;
import text_clustering.Evaluation;
import text_clustering.IncrementalClustering;

public class LoadForm extends Form {

	private IncrementalClustering classit;
	private int line = 0;
	private String[] setValues  = {"Fréquences", "Poids (tf-idf)"};
	
	public LoadForm(IncrementalClustering c, ScrollPane cSource, GridPane vSource) {
		// Mise en forme
		Button btnAddDocx = new Button("Ajouter des documents");
		Button btnEvaluate = new Button("Evaluer le modèle");
		classit = c;
		Button btnReturn2Config = new Button("Reconfigurer");
		btnReturn2Config.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				cSource.setContent(vSource);
			}
		});
		this.addRow(++line, btnReturn2Config);
		this.add(new Separator(), 0, ++line, 2, 1);
		
		this.addRow(++line, new Label("Nom du modèle: "), new Label(classit.getModelName()));
		this.add(new Separator(),0, ++line, 2, 1);
		
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
		this.add(new Separator(),0, ++line, 2, 1);
		
		this.addRow(++line, new Label("Cutoff: "), new Label((float)classit.getCutoff() + ""));
		this.addRow(++line, new Label("Acuity: "), new Label((float)classit.getAcuity() + ""));
		this.add(new Separator(),0, ++line, 2, 1);
		this.addRow(++line, new Label("Nombre de documents: "), 
				new Label(classit.getIndex().getNumberOfDoc() + ""));
		this.addRow(++line, new Label("Taille du vocabulaire: "), 
				new Label(classit.getIndex().getTermSpace().size() + ""));
		if(classit.getRepType() == 'e')
			this.addRow(++line, new Label("Nombre d'ensemble fréquents: "), 
					new Label(classit.getIndex().getWordPatterns().size() + ""));
		this.add(new Separator(),0, ++line, 2, 1);
		
		this.addRow(++line, btnAddDocx);
		this.addRow(++line, btnEvaluate);
		
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
					System.out.print("Apprentissage incrémental: ");
					classit.incClusterInstances(classit.getIndex());
					FileWriter fstream;
					try {
						System.out.println("FIN");
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
		btnEvaluate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				//Evaluation
				String fichier = "E:\\Corpus\\classinfotrain1.txt";
				Evaluation eval = new Evaluation(classit, fichier);
				System.out.println("Evaluation des clusters:");
				//System.out.println("Macro-moyenne (Fscore): "+  eval.clusterEvalMacroFscore());
				//System.out.println("Micro-moyenne (Fscore): "+  eval.clusterEvalMicroFscore());
				float cEvalMacroF = eval.clusterEvalMacroFscore();
				float cEvalMicroF = eval.clusterEvalMicroFscore();
				System.out.println(cEvalMacroF);
				System.out.println(cEvalMicroF);
				
				Menu_File.taOutput.appendText("Evaluation des clusters:\n");
				Menu_File.taOutput.appendText("Macro moyenne: "+cEvalMacroF+"\n");
				Menu_File.taOutput.appendText("Micro moyenne: "+cEvalMicroF+"\n");
				System.out.println("Evaluation de la hiérarchie:");
				/*
				System.out.println("Macro-moyenne (Precision)"+eval.HierarchieEvalMacro());
				System.out.println("Micro-moyenne (Precision)"+eval.HierarchieEvalMicro());
				*/
				ArrayList<ArrayList<EvalNode>> e = eval.levelPrecisions();
				float macro = 0;
				float tp =0, tn=0;
				float microtp =0, microtn=0;
				for(int i=0; i<e.size();i++){
					float ma =0;
					tp =0;
					tn=0;
					for(int j=0; j<e.get(i).size();j++){
						microtp +=tp;
						microtn +=tn;
						tp += e.get(i).get(j).getTp();
						tn += e.get(i).get(j).getTn();
						ma += e.get(i).get(j).getPrecision();
					}
					if(e.get(i).size()!= 0){
						ma = ma / e.get(i).size();
						macro = macro + ma;
						//System.out.println(ma);
						//System.out.println((tp/(tp+tn)));
					}
				}
				System.out.println((macro / e.size()));
				System.out.println((microtp/(microtp+microtn)));

				Menu_File.taOutput.appendText("Evaluation de la hiérarchie:\n");
				Menu_File.taOutput.appendText("Macro moyenne:"+ (macro / e.size()) + "\n");
				Menu_File.taOutput.appendText("Micro moyenne:"+ (microtp/(microtp+microtn)) + "\n");
			}
		});
	}
}