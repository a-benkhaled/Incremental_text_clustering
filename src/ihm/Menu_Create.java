package ihm;

import weka.classifiers.functions.supportVector.RBFKernel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Menu "Fichier"
 **/

public class Menu_Create extends Menu {

	public Menu_Create(String name) {
		// TODO Auto-generated constructor stub
		super(name);
		MenuItem newModel = new MenuItem("Cr�er un nouvel mod�le");
		createNewModel();
		MenuItem loadModel = new MenuItem("Charger un mod�le");
		loadModel();
		MenuItem saveModel = new MenuItem("Savegrder le mod�le");
		saveModel();
		MenuItem exit = new MenuItem("Quitter");
		this.getItems().addAll(newModel, new SeparatorMenuItem(), loadModel,
				saveModel, new SeparatorMenuItem(), exit);

		newModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!createMenuOn) {
					GUI.scrollingView.setContent(createMenu);
					GUI.subMainView.setLeft(createMenuInfo);
					createMenuOn = true;
					loadMenuOn = false;
					saveMenuOn = false;
				}
			}
		});

		loadModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!loadMenuOn) {
					GUI.scrollingView.setContent(loadMenu);
					GUI.subMainView.setLeft(loadMenuInfo);
					loadMenuOn = true;
					saveMenuOn = false;
					createMenuOn = false;
				}
			}
		});

		saveModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (!saveMenuOn) {
					GUI.scrollingView.setContent(saveMenu);
					GUI.subMainView.setLeft(saveMenuInfo);
					saveMenuOn = true;
					createMenuOn = false;
					loadMenuOn = false;
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

	private void saveModel() {
		// TODO Auto-generated method stub
		saveMenu = new GridPane();
		saveMenuInfo = new VBox();
		saveMenuInfo.setPrefWidth(200);
	}

	private void loadModel() {
		// TODO Auto-generated method stub
		loadMenu = new GridPane();
		loadMenuInfo = new VBox();
		loadMenuInfo.setPrefWidth(200);
	}
	/**
	 * Mise en forme de la fen�tre "cr�er un modele"
	 **/
	private void createNewModel() {
		createMenu = new GridPane();
		createMenuInfo = new VBox();
		createMenuInfo.setPrefWidth(200);
		Label infoLabel = new Label("Cr�er d'un mod�le");
		infoLabel.setId("title");
		createMenuInfo.getChildren().add(infoLabel);

		int line = 0;
		createMenu.setHgap(10);
		createMenu.setVgap(10);
		createMenu.setPadding(new Insets(0, 10, 0, 10));
		lblCreate = new Label("Cr�ation d'un nouvel mod�le");
		lblCreate.setId("title");
		tfName = new TextField();
		lblSelect = new Label("S�lection de document");
		lblSelect.setId("title");
		fcLearn = new Button("Ajouter");
		fcUniverse = new Button("Ajouter");
		lblPret = new Label("Param�tres de pr�traitement");
		lblPret.setId("title");
		cbStem = new CheckBox("Radicalisation (Stemming)");
		fcSL = new Button("Ajouter");
		fcScript = new Button("Ajouter");
		lblRep = new Label("Param�tres de repr�sentation");
		lblRep.setId("title");
		RadioButton rbFreq = new RadioButton("Fr�quences d'apparition des termes");
		RadioButton rbPoids = new RadioButton("Poids des termes");
		rbWordSet= new RadioButton("Ensemble de termes fr�quents");
		tgRep = new ToggleGroup();
		rbFreq.setToggleGroup(tgRep);
		rbPoids.setToggleGroup(tgRep);
		rbWordSet.setToggleGroup(tgRep);
		tgWSfp = new ToggleGroup();
		tgWSmc = new ToggleGroup();
		tfMinSupp = new TextField();tfMinSupp.setDisable(true);
		tfMaxSupp = new TextField();tfMaxSupp.setDisable(true);
		tfMaxNb= new TextField();tfMaxNb.setDisable(true);
		tfMinNb = new TextField();tfMinNb.setDisable(true);
		rbWSFreq = new RadioButton("Fr�quences d'apparition des ensembles");
		rbWSPoi = new RadioButton("Poids des ensembles");
		rbWSMax = new RadioButton("Ensemble maximal");
		rbWSCLos = new RadioButton("Ensemble ferm�");
		rbWSFreq.setToggleGroup(tgWSfp);rbWSFreq.setDisable(true);
		rbWSPoi.setToggleGroup(tgWSfp);rbWSPoi.setDisable(true);
		rbWSCLos.setToggleGroup(tgWSmc);rbWSCLos.setDisable(true);
		rbWSMax.setToggleGroup(tgWSmc);rbWSMax.setDisable(true);
		
		lblAlgo = new Label("Param�tres de l'algorithme de clustering");
		lblAlgo.setId("title");
		tfCutoff = new TextField();
		tfAcquity = new TextField();
		btnCreate = new Button("Cr�er");
		btnCreate.setId("createButton");

		// Cr�ation d'un nouvel mod�le
		createMenu.add(lblCreate, 0, line, 2, 1);
		createMenu.addRow(++line, new Label("Nom"), tfName);
		createMenu.add(new Separator(), 0, ++line, 2, 1);

		// S�lection de document
		createMenu.add(lblSelect, 0, ++line, 2, 1);
		createMenu.addRow(++line, new Label(
				"Ajouter � l'ensemble d'apprentissage"), fcLearn);
		createMenu.addRow(++line, new Label(
				"Ajouter au vocabulaire (optionnel)"), fcUniverse);
		createMenu.add(new Separator(), 0, ++line, 2, 1);

		// Param�tres de pr�traitement
		createMenu.add(lblPret, 0, ++line, 2, 1);
		createMenu.add(cbStem, 0, ++line);
		createMenu.addRow(++line, new Label("Ajouter une liste de mots vides"),
				fcSL);
		createMenu.addRow(++line, new Label(
				"Ajouter un script de pr�traitement"), fcScript);
		createMenu.add(new Separator(), 0, ++line, 2, 1);

		// Param�tres de repr�sentation
		createMenu.add(lblRep, 0, ++line, 2, 1);
		createMenu.add(rbFreq, 0, ++line);
		createMenu.add(rbPoids, 0, ++line);
		createMenu.add(rbWordSet, 0, ++line);
		createMenu.addRow(++line, new Label("Support minimal"), tfMinSupp);
		createMenu.addRow(++line, new Label("Support maximal"), tfMaxSupp);
		createMenu.add(rbWSFreq, 0, ++line);
		createMenu.add(rbWSPoi, 0, ++line);
		createMenu.addRow(++line, new Label("Nombre minimal de term"), tfMinNb);
		createMenu.addRow(++line, new Label("Nombre maximal de term"), tfMaxNb);
		createMenu.add(rbWSMax, 0, ++line);
		createMenu.add(rbWSCLos, 0, ++line);
		createMenu.add(new Separator(), 0, ++line, 2, 1);

		// Param�tres de l'algorithme de clustering
		createMenu.add(lblAlgo, 0, ++line, 2, 1);
		createMenu.addRow(++line, new Label("Cutoff"), tfCutoff);
		createMenu.addRow(++line, new Label("Acquity"), tfAcquity);
		createMenu.add(new Separator(), 0, ++line, 2, 1);

		createMenu.add(btnCreate, 1, ++line, 2, 1);
		runtimeEvents();
		
	}
	
	private void runtimeEvents() {
		rbWordSet.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rbWordSet.setDisable(false);
				rbWSFreq.setDisable(false);
				rbWSPoi.setDisable(false);
				rbWSMax.setDisable(false);
				rbWSCLos.setDisable(false);
				tfMinSupp.setDisable(false);
				tfMaxSupp.setDisable(false);
				tfMinNb.setDisable(false);
				tfMaxNb.setDisable(false);
			}
		});
		btnCreate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				createConfirmation();
			}
		});
	}

	/**
	 * Teste la validit� du param�trage du mod�le
	 * Extraction des options du mod�le
	 * */
	protected void createConfirmation() {
		// TODO Auto-generated method stub
		extractionParam�tres();
		Stage stage = new Stage();
		BorderPane root = new BorderPane();
		GridPane main = new GridPane();
		Button btnConfirm  = new Button("Confirmer");
		stage.setTitle("Confirmation");
		Scene scene = new Scene(root, 450, 250);
		scene.getStylesheets().add(
				getClass().getResource("application.css").toExternalForm());
		stage.setResizable(false);

		main.setHgap(10);
		main.setVgap(10);
		main.setPadding(new Insets(0, 10, 0, 10));
		
		int line = 0;
		main.add(new Label("Nom: "+tfName.getText()), 0, line);
		main.add(new Label(cbStem.getText()), 0, ++line);
		main.add(new Label(cbStem.isSelected()?"oui":"non"), 1, line);
		main.add(new Label("Type de repr�sentation: "), 0, ++line);
		//main.add(new Label(cbRep.getValue()), 1, line);
		main.add(new Label("Param�tre de l'algorithme de clustering: "), 0, ++line);
		main.add(new Label("Cutoff: "+tfCutoff.getText()), 1, ++line);
		main.add(new Label("Acquity: "+  tfAcquity.getText()), 1, ++line);
		//main.add(btnConfirm, 1, ++line);
		btnConfirm.setOnAction(new EventHandler<ActionEvent>(	) {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				startTheSystem();
			}
		});
		root.setTop(main);
		root.setCenter(btnConfirm);
		stage.setScene(scene);
		stage.show();
	}

	private void extractionParam�tres() {
		// TODO Auto-generated method stub
		stemming = cbStem.isSelected();//Stemming
		//Type de repr�sentation
		/*
		if(cbRep.getValue().equals("Fr�quences d'apparition des termes"))
			rep = 'f';
		else
			if(cbRep.getValue().equals("Poids des termes"))
				rep = 'p';
			else
				if(cbRep.getValue().equals("Ensemble de termes fr�quents"))
					rep = 'e';
		*/
		cutoff = Float.valueOf(tfCutoff.getText());
		acuity = Float.valueOf(tfAcquity.getText());
	}

	protected void startTheSystem() {
		// TODO Auto-generated method stub
		
		/*
		IncrementalClustering classit;
		Indexer index = new Indexer();
		index.setStemming(stemming);
		index.init("data\\train\\");
		index.printVocabulary();
		PatternMiner fpgrowth = new PatternMiner(index.getListOfDocument());
		fpgrowth.setTargetItemType('m');
		fpgrowth.setMinSupport((float) 9);
		fpgrowth.setMaxSupport((float) 10);
		fpgrowth.mine();
		
		if(fpgrowth.getNumberOfPatterns() == 0){//Pas d'ensemble fr�quent
			return;
		}
		System.out.println(fpgrowth.getNumberOfPatterns());
		classit = new IncrementalClustering();
		classit.setAcuity(acuity);
		classit.setCutoff(cutoff);//min cu
		classit.prepareInstances(fpgrowth.getPatternsList(), index.getListOfDocument(), 1);
		//classit.printInstances();
		try {
			classit.startClustering();
			System.out.println(classit.graph());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
	
	private boolean stemming = false;
	private char rep = 'f';
	private float cutoff ;
	private float acuity;
	protected boolean createMenuOn = false;
	protected GridPane createMenu;
	protected VBox createMenuInfo;
	protected boolean loadMenuOn = false;
	protected GridPane loadMenu;
	protected VBox loadMenuInfo;
	protected boolean saveMenuOn = false;
	protected GridPane saveMenu;
	protected VBox saveMenuInfo;
	protected Label lblCreate;
	protected TextField tfName;
	protected Label lblSelect;
	protected Button fcLearn;
	protected Button fcUniverse;
	protected Label lblPret;
	protected CheckBox cbStem;
	protected Button fcSL;
	protected Button fcScript;
	protected Label lblRep;
	protected ToggleGroup tgRep;
	protected TextField tfMinSupp;
	protected TextField tfMaxSupp;
	protected TextField tfMinNb;
	protected TextField tfMaxNb;
	protected ToggleGroup tgWSfp;
	protected ToggleGroup tgWSmc;
	protected Label lblAlgo;
	protected TextField tfCutoff;
	protected TextField tfAcquity;
	protected Button btnCreate;
	protected RadioButton rbWordSet;
	protected RadioButton rbWSFreq;
	protected RadioButton rbWSPoi;
	protected RadioButton rbWSMax;
	protected RadioButton rbWSCLos;

}