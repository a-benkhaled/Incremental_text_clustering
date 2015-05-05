package ihm;

import ihm_form.AlgoForm;
import ihm_form.ConfirmForm;
import ihm_form.ModelInfoForm;
import ihm_form.ParametrerRepForm;
import ihm_form.SelectPretraitForm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import text_clustering.IncrementalClustering;
import word_mining.PatternMiner;
import doc.Indexer;

/**
 * Menu "Fichier"
 **/

public class Menu_File extends Menu {

	private IncrementalClustering classit;
	public Menu_File(String name) {
		// TODO Auto-generated constructor stub
		super(name);
		/* *************** AFFICHAGE **************** ***/
		//Menu bar items
		
		//Cr�er un nouvel mod�le
		MenuItem newModel = new MenuItem("Cr�er un nouvel mod�le");
		createNewModel();
		//Charger un mod�le
		MenuItem loadModel = new MenuItem("Charger un mod�le");
		
		//Savegrder le mod�le
		MenuItem saveModel = new MenuItem("Savegrder le mod�le");
		
		//Quitter
		MenuItem exit = new MenuItem("Quitter");
		
		this.getItems().addAll(newModel, new SeparatorMenuItem(), loadModel,
				saveModel, new SeparatorMenuItem(), exit);
		
		/* *******************ACTIONS*******************************/
		//Cr�er un nouvel mod�le
		newModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!createMenuOn) {
					GUI.scrollingView.setContent(createMenu);
					//GUI.subMainView.setLeft(createMenuInfo);
					createMenuOn = true;
					loadMenuOn = false;
					saveMenuOn = false;
				}
			}
		});
		
		//Charger un mod�le
		loadModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!loadMenuOn) {
					//GUI.scrollingView.setContent(loadMenu);
					//GUI.subMainView.setLeft(loadMenuInfo);
					//loadMenuOn = true;
					//saveMenuOn = false;
					//createMenuOn = false;
					loadModel();
				}
			}
		});
		
		//Savegrder le mod�le
		saveModel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!saveMenuOn) {
					//GUI.scrollingView.setContent(saveMenu);
					//GUI.subMainView.setLeft(saveMenuInfo);
					//saveMenuOn = true;
					//createMenuOn = false;
					//loadMenuOn = false;
					
					saveModel();
				}
			}
		});

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
		if(f != null){
			try {
				ObjectOutputStream OS;
				OS = new ObjectOutputStream(new FileOutputStream(f));
				OS.writeObject(classit);
				OS.flush();
				OS.close();
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
		loadMenu = createMenu;
		//loadMenuInfo = new VBox();
		//loadMenuInfo.setPrefWidth(200);
		FileChooser loadFile = new FileChooser();
		loadFile.setTitle("Sauvegarder");
		File f = loadFile.showOpenDialog(new Stage());
		if(f != null){
			try {
				ObjectInputStream IS = new ObjectInputStream(new FileInputStream(f)) ;
				classit = (IncrementalClustering) IS.readObject() ; 
				IS.close();
				System.out.println(classit.getModelName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println("Erreur lors du chargement du modele");
			}
		}
	}
	
	/**
	 * Mise en forme de la fen�tre "cr�er un modele"
	 **/
	
	private void createNewModel() {
		createMenu = new GridPane();
		//createMenuInfo = new VBox();
		//createMenuInfo.setPrefWidth(200);
		int line=0;
		
		//Titre principale "Cr�ation d'un nouvel mod�le"
		Label infoLabel = new Label("Cr�ation d'un nouvel mod�le");
		infoLabel.setId("title");
		createMenu.add(infoLabel, 0, line, 2, 1);
		createMenu.add(new Separator(), 0, ++line, 2 , 1 );
		//createMenuInfo.getChildren().add(infoLabel);
		
		//Nommer
		formInfo = new ModelInfoForm();
		createMenu.add(formInfo, 0, ++line);
		
		//Selection et pr�traitement
		formSelectPret = new SelectPretraitForm();
		createMenu.add(formSelectPret, 0, ++line, 2, 1);
		
		//Repr�sentation
		formRep = new ParametrerRepForm();
		createMenu.add(formRep, 0, ++line, 2, 1);
		
		//Algorithme
		formAlgo = new AlgoForm();
		createMenu.add(formAlgo, 0, ++line, 2, 1);
		
		//Cr�er
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
	 * Teste la validit� du param�trage du mod�le
	 * Extraction des options du mod�le
	 * */
	
	protected void extractValidateParam(){
		extractionParam();
		createConfirmWin();
	}
	
	private void extractionParam() {
		// TODO Auto-generated method stub
		//Nom du mod�le
		modelName = formInfo.getModelName();
		
		//Selection et pr�traitement
		pathLearningSet = formSelectPret.getPathLearnSet();
		pathTermSpaceSet = formSelectPret.getPathUniverse();
		pathStopListe = formSelectPret.getPathStopListe();
		pathScript = formSelectPret.getPathScript();
		stemming = formSelectPret.getRadicalisation();
		
		//Param�tres de repr�sentation
		repType = formRep.getRepType();

		if (repType == 'e'){
			repTypeWS = formRep.getRepTypeWS();
			minSupp = formRep.getMinSupp();
			maxSupp = formRep.getMaxSupp();
			minTermNb = formRep.getMinTermNb();
			maxTermNb = formRep.getMaxTermNb();
			repTypeWSForm = formRep.getWordSetForm();
		}
		
		//Param�tres de l'algorithme
		acuity = formAlgo.getAcuity();
		cutoff = formAlgo.getCutOff();
	}
	
	protected void createConfirmWin() {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		BorderPane main = new BorderPane();
		Scene scene = new Scene(main);
		Button btnLaunch = new Button("Lancer");
		ProgressBar pbSystem  = new ProgressBar();
		
		ConfirmForm confirm = new ConfirmForm();
		confirm.setModelName(modelName);
		confirm.setPathLearningSet(pathLearningSet);
		confirm.setPathTermSpaceSet(pathTermSpaceSet);
		confirm.setStemming(stemming);
		confirm.setPathStopListe(pathStopListe);
		confirm.setPathScript(pathScript);
		confirm.setRepType(repType);
		confirm.setRepTypeWS(repTypeWS);
		confirm.setRepTypeWSForm(repTypeWSForm);
		confirm.setMinSupp(minSupp);
		confirm.setMaxSupp(maxSupp);
		confirm.setMinTermNb(minTermNb);
		confirm.setMaxTermNb(maxTermNb);
		confirm.setCutoff(cutoff);
		confirm.setAcuity(acuity);
		confirm.create();
		
		btnLaunch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				startTheSystem();
			}
		});
		
		stage.setResizable(false);
		main.setTop(confirm);
		main.setCenter(btnLaunch);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Lancer le syst�me de clustering
	 */
	protected void startTheSystem() {
		// TODO Auto-generated method stub
		
		Indexer index = new Indexer();
		index.setStemming(stemming);
		System.out.println(pathLearningSet);
		index.init(pathLearningSet);
		//index.printVocabulary();

		classit = new IncrementalClustering(modelName);
		classit.setAcuity(acuity);
		classit.setCutoff(cutoff);//min cu
		
		if(repType =='e'){
			PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
			fpgrowth.setTargetItemType(repTypeWSForm);
			fpgrowth.setMinSupport((float) minSupp);
			fpgrowth.setMaxSupport((float) maxSupp);
			fpgrowth.setMinItemSize(minTermNb);
			fpgrowth.setMaxItemSize(maxTermNb);
			fpgrowth.mine();
			classit.prepareInstances(fpgrowth.getPatternsList(), null, index.getListOfDocument(), repType, repTypeWS);
			classit.printInstances();
		}else{
			classit.prepareInstances(null, index.getTermSpace(), index.getListOfDocument(), repType, 0);
		}

		try {
			classit.startClustering();
			System.out.println(classit.graph());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Segments de la fen�tre
	protected ModelInfoForm formInfo;
	protected SelectPretraitForm formSelectPret;
	protected ParametrerRepForm formRep;
	protected AlgoForm formAlgo;
	
	//Param�tres du mod�le
	protected String modelName;
	protected String pathLearningSet;
	protected String pathTermSpaceSet;
	protected boolean stemming = false;
	protected String pathStopListe;
	protected String pathScript;
	protected char repType = 'f';//Type de repr�sentation ('f', 'p', 'e')
	protected int repTypeWS = 0;//ensemble de mots fr�quents
	protected char repTypeWSForm ;//ensemble de mots fr�quents
	protected float minSupp;
	protected float maxSupp;
	protected int minTermNb;
	protected int maxTermNb;
	protected float cutoff ;
	protected float acuity;
	
	//Composants grahiques
	protected boolean createMenuOn = false;
	protected boolean loadMenuOn = false;
	protected boolean saveMenuOn = false;
	protected GridPane createMenu;
	protected GridPane loadMenu;
	protected GridPane saveMenu;
	protected Button btnCreate;
}