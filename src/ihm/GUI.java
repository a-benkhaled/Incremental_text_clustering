package ihm;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GUI extends Application {
	
	static GridPane mainView;
	static VBox infoView;
	
	@Override
	public void start(Stage primaryStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 800, 600);
		
		infoView = new VBox();
		infoView.setPrefWidth(200);
		BorderPane subMainView = new BorderPane();
		ScrollPane scrollingView = new ScrollPane();
		mainView = new GridPane();
		mainView.setHgap(10);
		mainView.setVgap(10);
		mainView.setPadding(new Insets(0, 10, 0, 10));
		scrollingView.setContent(mainView);
		subMainView.setLeft(infoView);
		subMainView.setCenter(scrollingView);
		HBox statusbar = new HBox();
		IHMenu menuBar = new IHMenu();
		
		/*Charger CSS*/
		try {
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    statusbar.setId("statusbar");
		} catch(Exception e) {
			e.printStackTrace();
		}
		/*InfoView*/
		
		
		/*MainView*/
		int line =0;
		Label lblCreate  = new Label("Création d'un nouvel modèle");lblCreate.setId("title");
		TextField tfName = new TextField();
		Label lblSelect  = new Label("Sélection de document");lblSelect.setId("title");
		Button fcLearn = new Button("Ajouter");
		Button fcUniverse = new Button("Ajouter");
		Label lblPret  = new Label("Paramètres de prétraitement");lblPret.setId("title");
		CheckBox cbStem = new CheckBox("Radicalisation (Stemming)");
		CheckBox cbToken = new CheckBox("Tokenization");
		Button fcSL = new Button("Ajouter");
		Button fcScript	 = new Button("Ajouter");
		Label lblRep  = new Label("Paramètres de représentation");lblRep.setId("title");
		CheckBox cbFreq = new CheckBox("Fréquences d'apparition des termes");
		CheckBox cbPoid = new CheckBox("Poids des termes");
		CheckBox cbFreqTerm = new CheckBox("Ensemble de termes fréquents");
		Label lblAlgo  = new Label("Paramètres de l'algorithme de clustering");lblAlgo.setId("title");
		TextField tfCutoff = new TextField();
		TextField tfAcquity = new TextField();
		Button btnCreate = new Button("Créer");btnCreate.setId("createButton");
		
		//Création d'un nouvel modèle
		mainView.add(lblCreate, 0, line, 2, 1);
		mainView.addRow(++line, new Label("Nom"), tfName);
		mainView.add(new Separator(), 0, ++line, 2, 1);
		
		//Sélection de document
		mainView.add(lblSelect, 0, ++line, 2, 1);
		mainView.addRow(++line, new Label("Ajouter à l'ensemble d'apprentissage"), fcLearn);
		mainView.addRow(++line, new Label("Ajouter au vocabulaire (optionnel)"), fcUniverse);
		mainView.add(new Separator(), 0, ++line, 2, 1);
		
		//Paramètres de prétraitement
		mainView.add(lblPret, 0, ++line, 2, 1);
		mainView.add(cbStem, 0, ++line);
		mainView.add(cbToken, 0, ++line);
		mainView.addRow(++line, new Label("Ajouter une liste de mots vides"), fcSL);
		mainView.addRow(++line, new Label("Ajouter un script de prétraitement"), fcScript);
		mainView.add(new Separator(), 0, ++line, 2, 1);
		
		//Paramètres de représentation
		mainView.add(lblRep, 0, ++line, 2, 1);
		mainView.add(cbFreq, 0, ++line);
		mainView.add(cbPoid, 0, ++line);
		mainView.add(cbFreqTerm, 0, ++line);
		mainView.add(new Separator(), 0, ++line, 2, 1);
		
		//Paramètres de l'algorithme de clustering
		mainView.add(lblAlgo, 0, ++line, 2, 1);
		mainView.addRow(++line, new Label("Cutoff"), tfCutoff);
		mainView.addRow(++line, new Label("Acquity"), tfAcquity);
		mainView.add(new Separator(), 0, ++line, 2, 1);
		
		mainView.add(btnCreate, 1, ++line, 2, 1);
		
		/*ROOT*/
		root.setTop(menuBar);
	    root.setCenter(subMainView);
		root.setBottom(statusbar);
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
