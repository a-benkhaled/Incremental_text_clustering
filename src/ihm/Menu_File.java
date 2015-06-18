package ihm;

import ihm_form.AlgoForm;
import ihm_form.ConfirmForm;
import ihm_form.LoadForm;
import ihm_form.ModelInfoForm;
import ihm_form.ParametrerRepForm;
import ihm_form.SelectPretraitForm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StringReader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.JFrame;

import text_clustering.IncrementalClustering;
import weka.gui.graphvisualizer.GraphVisualizer;
import doc.Indexer;

/**
 * Menu "Fichier"
 **/

public class Menu_File extends Menu {

	private IncrementalClustering classit;
	
	private IHMenu sourceMenu;
	private BorderPane infoMainView;
	private ScrollPane scrollingView;
	public static TextArea taOutput;
	public static Label lblTooltip;
	protected ProgressBar pbClustering;
	
	public Menu_File(String name, IHMenu sm) {
		// TODO Auto-generated constructor stub
		super(name);
		sourceMenu = sm;
		infoMainView = new BorderPane();
		infoMainView.setPrefWidth(200);
		Label lblHelp = new Label("Aide");lblHelp.setId("title");
		infoMainView.setTop(lblHelp);
		scrollingView = new ScrollPane();
		scrollingView.setMinWidth(470);
		
		VBox hbOutput = new VBox();
		lblTooltip=new Label();
		hbOutput.getChildren().add(lblTooltip);
		hbOutput.getChildren().add(new Label("Console"));
		taOutput = new TextArea();
		taOutput.setEditable(false);
		hbOutput.getChildren().add(taOutput);
		
		infoMainView.setCenter(hbOutput);
		//taOutput.appendText("**************************");
		
		/* *************** AFFICHAGE **************** ** */
		// Menu bar items

		// Cr�er un nouvel mod�le
		MenuItem newModel = new MenuItem("Cr�er un nouvel mod�le");newModel.setId("menuitem");

		// Charger un mod�le
		MenuItem loadModel = new MenuItem("Charger un mod�le");loadModel.setId("menuitem");

		// Savegrder le mod�le
		MenuItem saveModel = new MenuItem("Savegrder le mod�le");saveModel.setId("menuitem");

		// Quitter
		MenuItem exit = new MenuItem("Quitter");exit.setId("menuitem");

		this.getItems().addAll(newModel, new SeparatorMenuItem(), loadModel,
				saveModel, new SeparatorMenuItem(), exit);

		/* *******************ACTIONS****************************** */
		// Cr�er un nouvel mod�le
		newModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				createNewModel();
				GUI.subMainView.setCenter(scrollingView);
				GUI.subMainView.setRight(infoMainView);
				GUI.subMainView.setLeft(null);
				scrollingView.setContent(createMenu);
			}
		});

		// Charger un mod�le
		loadModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
					loadModel();
					GUI.subMainView.setCenter(scrollingView);
					GUI.subMainView.setRight(infoMainView);
					scrollingView.setContent(loadMenu);
			}
		});

		// Sauvegarder le mod�le
		saveModel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
					saveModel();
			}
		});
		
		//Quitter
		exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}

	/**
	 * Mise en forme de la fen�tre "sauvegarder un modele"
	 **/
	
	private void saveModel() {
		// TODO Auto-generated method stub
		saveMenu = new GridPane();
		FileChooser saveFile = new FileChooser();
		saveFile.setTitle("Sauvegarder");
		File f = saveFile.showSaveDialog(new Stage());
		if (f != null) {
			try {
				ObjectOutputStream OS;
				OS = new ObjectOutputStream(new FileOutputStream(f));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.getDialogPane().getChildren().remove(2);
				ProgressIndicator pin = new ProgressIndicator();
				alert.getDialogPane().getChildren().add(pin);
				alert.setTitle("Sauvegarde en cours");
				alert.setHeaderText("Sauvegarde du mod�le en cours... Veuillez patientez.");
				alert.show();
				OS.writeObject(classit);
				OS.flush();
				OS.close();
				alert.hide();
				alert.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Erreur lors de la sauvegarde du modele");
			}
		}
	}

	/**
	 * Mise en forme de la fen�tre "Charger un modele"
	 **/
	private void loadModel() {
		// TODO Auto-generated method stub
		if(loadClusterer() != null){
			loadMenu = new LoadForm(classit, null, null);
			//Activer le menu Affichage
			((Menu_Affichage) sourceMenu.getAffichageMenu()).setCurrentModel(classit);
		}
	}
	/**
	 * 
	 * Chargement du mod�le
	 */
	private Object loadClusterer(){
		FileChooser loadFile = new FileChooser();
		loadFile.setTitle("Charger");
		File f = loadFile.showOpenDialog(new Stage());//r�cup�rer le fichier
		if (f != null) {
			try {
				ObjectInputStream IS = new ObjectInputStream(
						new FileInputStream(f));
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.getDialogPane().getChildren().remove(2);
				ProgressIndicator pin = new ProgressIndicator();
				alert.getDialogPane().getChildren().add(pin);
				alert.setTitle("Chargement");
				alert.setHeaderText("Chargement du mod�le en cours... Veuillez patientez.");
				alert.show();
				classit = (IncrementalClustering) IS.readObject();
				IS.close();
				alert.hide();
				alert.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Erreur lors du chargement du mod�le");
			}
			return f;
		}else
			return null;
	}
	
	/**
	 * Mise en forme de la fen�tre "cr�er un modele"
	 **/
	
	private void createNewModel() {
		createMenu = new GridPane();
		int line = 0;
		//classit = new IncrementalClustering();
		// Titre principale "Cr�ation d'un nouvel mod�le"
		Label infoLabel = new Label("Cr�ation d'un nouvel mod�le");
		infoLabel.setId("title");
		createMenu.add(infoLabel, 0, line, 2, 1);
		createMenu.add(new Separator(), 0, ++line, 2, 1);
		// createMenuInfo.getChildren().add(infoLabel);

		// Nommer
		formInfo = new ModelInfoForm();
		createMenu.add(formInfo, 0, ++line);

		// Selection et pr�traitement
		formSelectPret = new SelectPretraitForm();
		createMenu.add(formSelectPret, 0, ++line, 2, 1);

		// Repr�sentation
		formRep = new ParametrerRepForm();
		createMenu.add(formRep, 0, ++line, 2, 1);

		// Algorithme
		formAlgo = new AlgoForm();
		createMenu.add(formAlgo, 0, ++line, 2, 1);

		// Cr�er
		btnCreate = new Button("Cr�er");
		createMenu.add(btnCreate, 1, ++line, 2, 1);

		btnCreate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				extractValidateParam();
			}
		});
	}

	/**
	 * Teste la validit� du param�trage du mod�le Extraction des options du
	 * mod�le
	 * */

	protected void extractValidateParam() {
		extractionParam();
		createConfirmWin();
	}

	private void extractionParam() {
		// TODO Auto-generated method stub
		// Nom du mod�le
		modelName = formInfo.getModelName() ;

		// Selection et pr�traitement
		pathLearningSet = formSelectPret.getPathLearnSet();
		pathTermSpaceSet = formSelectPret.getPathUniverse();
		pathStopListe = formSelectPret.getPathStopListe();
		pathScript = formSelectPret.getPathScript();
		stemming = formSelectPret.getRadicalisation();

		// Param�tres de repr�sentation
		repType = formRep.getRepType();

		if (repType == 'e') {
			repTypeWS = formRep.getRepTypeWS();
			minSupp = formRep.getMinSupp();
			maxSupp = formRep.getMaxSupp();
			minTermNb = formRep.getMinTermNb();
			maxTermNb = formRep.getMaxTermNb();
			repTypeWSForm = formRep.getWordSetForm();
		}

		// Param�tres de l'algorithme
		acuity = formAlgo.getAcuity();
		cutoff = formAlgo.getCutOff();
	}

	protected void createConfirmWin() {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		stage.setResizable(true);
		BorderPane main = new BorderPane();
		Scene scene = new Scene(main);
		stage.setScene(scene);
		Button btnLaunch = new Button("Lancer");
		pbClustering = new ProgressBar();
		settingClassit();
		
		ConfirmForm confirm = new ConfirmForm(classit);
		confirm.create();
		btnLaunch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				startTheSystem();
				((Button)event.getSource()).getScene().getWindow().hide();
			}
		});
		main.setTop(confirm);
		main.setCenter(btnLaunch);
		main.setBottom(pbClustering);
		//main.setAlignment(pbClustering, Pos.BOTTOM_CENTER);
		
		stage.show();
	}

	private void settingClassit() {
		// TODO Auto-generated method stub
		Indexer index = new Indexer();
		index.setStemming(stemming);
		index.setPathScript(pathScript);
		//System.out.println(pathLearningSet);
		index.setAdditionnelVocabulary(pathTermSpaceSet);
		index.init(pathLearningSet);
		
		classit = new IncrementalClustering(modelName, index);
		classit.setModelName(modelName);
		classit.setPathLearningSet(pathLearningSet);
		classit.setPathTermSpaceSet(pathTermSpaceSet);
		classit.setStemming(stemming);
		classit.setPathStopListe(pathStopListe);
		classit.setPathScript(pathScript);
		classit.setRepType(repType);
		classit.setRepTypeWS(repTypeWS);
		classit.setRepTypeWSForm(repTypeWSForm);
		classit.setMinSupp(minSupp);
		classit.setMaxSupp(maxSupp);
		classit.setMinTermNb(minTermNb);
		classit.setMaxTermNb(maxTermNb);
		classit.setCutoff(cutoff);
		classit.setAcuity(acuity);
	}

	/**
	 * Lancer le syst�me de clustering
	 */
	
	protected void startTheSystem() {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		classit.prepareInstances();
		try {
			//classit.setState(pbClustering);
			classit.startClustering();
			long endTime = System.currentTimeMillis();
			System.out.println(((float)endTime - endTime)/1000);
			//Sauvegarder la description DOT du graph
			//Pour l'utiliser dans l'affichage
			FileWriter fstream = new FileWriter("data\\graph\\"+classit.getModelName()+".graph");
			
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(classit.graph());
			out.close();
			((Menu_Affichage) sourceMenu.getAffichageMenu()).setCurrentModel(classit);
			((Menu_Affichage) sourceMenu.getAffichageMenu()).setSourcePanel(scrollingView, createMenu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createMenu = new LoadForm(classit, scrollingView, createMenu);
		scrollingView.setContent(createMenu);
	}

	// Segments de la fen�tre
	protected ModelInfoForm formInfo;
	protected SelectPretraitForm formSelectPret;
	protected ParametrerRepForm formRep;
	protected AlgoForm formAlgo;

	// Param�tres du mod�le
	protected String modelName;
	protected String pathLearningSet;
	protected String pathTermSpaceSet;
	protected boolean stemming = false;
	protected String pathStopListe;
	protected String pathScript;
	protected char repType = 'f';// Type de repr�sentation ('f', 'p', 'e')
	protected int repTypeWS = 0;// ensemble de mots fr�quents
	protected char repTypeWSForm;// ensemble de mots fr�quents
	protected float minSupp;
	protected float maxSupp;
	protected int minTermNb;
	protected int maxTermNb;
	protected float cutoff;
	protected float acuity;

	// Composants grahiques
	protected GridPane createMenu;
	protected GridPane loadMenu;
	protected GridPane saveMenu;
	protected Button btnCreate;
}