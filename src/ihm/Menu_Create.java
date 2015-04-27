package ihm;

import ihm_form.AlgoForm;
import ihm_form.ParametrerRepForm;
import ihm_form.SelectPretraitForm;
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
		/* *************** AFFICHAGE **************** ***/
		//Menu bar items
		
		//Cr�er un nouvel mod�le
		MenuItem newModel = new MenuItem("Cr�er un nouvel mod�le");
		createNewModel();
		//Charger un mod�le
		MenuItem loadModel = new MenuItem("Charger un mod�le");
		loadModel();
		//Savegrder le mod�le
		MenuItem saveModel = new MenuItem("Savegrder le mod�le");
		saveModel();
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
					GUI.subMainView.setLeft(createMenuInfo);
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
					GUI.scrollingView.setContent(loadMenu);
					GUI.subMainView.setLeft(loadMenuInfo);
					loadMenuOn = true;
					saveMenuOn = false;
					createMenuOn = false;
				}
			}
		});
		//Savegrder le mod�le
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
		int line=0;
		Label infoLabel = new Label("Cr�er d'un mod�le");
		infoLabel.setId("title");
		createMenuInfo.getChildren().add(infoLabel);
		
		//Selection et pr�traitement
		SelectPretraitForm spForm = new SelectPretraitForm();
		createMenu.add(spForm, 0, ++line, 2, 1);
		
		//Repr�sentation
		ParametrerRepForm repForm = new ParametrerRepForm();
		createMenu.add(repForm, 0, ++line, 2, 1);
		
		//Algorithme
		AlgoForm algoForm = new AlgoForm();
		createMenu.add(algoForm, 0, ++line, 2, 1);
		
		//Cr�er
		btnCreate = new Button("Cr�er");
		createMenu.add(btnCreate, 1, ++line, 2, 1);
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

}