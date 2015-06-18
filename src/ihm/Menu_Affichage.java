package ihm;

import ihm_chart.HierarchicalView;
import ihm_chart.TermTable;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import text_clustering.IncrementalClustering;

public class Menu_Affichage extends Menu {
	protected IncrementalClustering currentModel;

	public Menu_Affichage() {}

	public Menu_Affichage(String s) {
		// TODO Auto-generated constructor stub
		super(s);
		// Afficher la hiérarchie du modèle
		MenuItem viewGraph = new MenuItem("Hiérarchie des documents");

		// Afficher les stats en relation avec le modèle
		MenuItem viewStat = new MenuItem("Statistiques du modèle");

		this.getItems().addAll(viewGraph, viewStat);
		
		viewGraph.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showGraph();
			}
		});
		
		viewStat.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				showStat();
			}
		});
	}

	public void setCurrentModel(IncrementalClustering cm) {
		this.currentModel = cm;
		this.setDisable(false);
	}

	private ScrollPane sourceContainer;
	private GridPane sourceContent;
	public void setSourcePanel(ScrollPane cSource, GridPane pSource){
		sourceContainer = cSource;
		sourceContent = pSource;
	}
	private void showStat(){
		//WordApperence chartWord = new WordApperence(currentModel.getIndex(), currentModel.getRepType());
		//TermInfoChart chartTerm = new TermInfoChart(currentModel.getIndex());
		HierarchicalView treeView = new HierarchicalView(currentModel);
		TermTable termTable = new TermTable(currentModel);
		GUI.subMainView.setRight(null);
		
		VBox vbTermTable = new VBox();
		vbTermTable.getChildren().add(termTable.termTable());
		if(currentModel.getRepType() =='e'){
			vbTermTable.getChildren().add(termTable.wordSetTable());
		}
		BorderPane bpSubView = new BorderPane();
		
		bpSubView.setScaleShape(true);
		//bpSubView.setCenter(chartWord);
		//bpSubView.setBottom(chartTerm);
		//chartWord.setScaleShape(true);
		//chartTerm.setScaleShape(true);
		
		ScrollPane spMainView = new ScrollPane();
		spMainView.setContent(bpSubView);
		GUI.subMainView.setLeft(treeView);
		GUI.subMainView.setRight(vbTermTable);
		GUI.subMainView.setCenter(spMainView);
		HBox hbBtnRet = new HBox();
		Button btnReturn = new Button("Retour");
		hbBtnRet.getChildren().add(btnReturn);
		btnReturn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				GUI.subMainView.setCenter(sourceContainer);
			}
		});
		

		GUI.subMainView.setTop(btnReturn);
	}
	
	private void showGraph() {
		String dotPath = "data\\graphviz\\dot.exe";
		String[] cmd = { dotPath, "-Tgif",
				"data\\graph\\" + currentModel.getModelName() + ".graph" };
		Runtime run = Runtime.getRuntime();
		try {
			Process pr = run.exec(cmd);
			Stage stage = new Stage();
			Image image = new Image(pr.getInputStream());
			ImageView iv = new ImageView(image);
			ScrollPane box = new ScrollPane();
			box.setContent(iv);
			Scene scene = new Scene(box);
			scene.setFill(Color.WHITE);
			stage.setTitle("Hiérarchie des documents");
			stage.setWidth(415);
			stage.setHeight(200);
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
