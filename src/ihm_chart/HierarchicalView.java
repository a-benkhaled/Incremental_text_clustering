package ihm_chart;

import ihm.GUI;
import doc.Document;
import edu.stanford.nlp.dcoref.Dictionaries.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import text_clustering.IncrementalClustering;
import word_mining.WordsPattern;

public class HierarchicalView extends BorderPane{
	private TreeView H;
	private IncrementalClustering classit;
	public HierarchicalView(IncrementalClustering c) {
		// TODO Auto-generated constructor stub
		H = c.getTree();
		classit = c;
		HBox t = new HBox();
		t.setPadding(new Insets(10,10,10,10));
		Text text = new Text();
		text.setText("Explorateur de la hiérarchie");
		t.getChildren().add(text);
		H.setEditable(true);

		//        H.setCellFactory(cellFactory);
		H.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new TreeCellImpl();
            }
        });
		this.setTop(t);
		this.setCenter(H);
	}
	
	 private final class TreeCellImpl extends TreeCell<String> {
		 
	        private Button button;
	 
	        public TreeCellImpl() {
	        	button = new Button();
	        }
	        @Override
	        public void startEdit() {
	            super.startEdit();
	            if (button == null) {
	                //createTextField();
	            }
	            setGraphic(button);
	            button.setText(getString());
	            setText(null);
	        	button.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						String docName = ((Button)event.getSource()).getText();
						//Chercher le doc
						Document courant = null;
						for(Document d:classit.getIndex().getListOfDocument()){
							if(docName.equals(d.getFileName())){
								courant = d;
								break;
							}
						}
						if(courant != null){
							VBox vbStat = new VBox();
							HBox hbTxt = new HBox();
							//hbTxt.setPadding(new Insets(10,10,10,10));
							Text t =new Text("Fréquences de document (termes)");
							
							hbTxt.getChildren().add(t);
							vbStat.getChildren().add(hbTxt);
							vbStat.getChildren().add(termTable(courant));
							if(classit.getRepType() == 'e'){
								HBox hbTxtws = new HBox();
								Text ws =new Text("Fréquences de document (ensemble fréquents)");
								//hbTxtws.setPadding(new Insets(10,10,10,10));
								
								hbTxtws.getChildren().add(ws);
								vbStat.getChildren().add(hbTxtws);
								vbStat.getChildren().add(wordSetTable(courant));
							}
							
							GUI.subMainView.setCenter(vbStat);
						}
					}
					
					@SuppressWarnings("rawtypes")
					private TableView termTable(Document d){
						TableView tvTermStat = new TableView();
						TableColumn colTermNb = new TableColumn("Numéro");
						TableColumn colTerm = new TableColumn("Terme");
						TableColumn colF = new TableColumn("Fréquence");
						TableColumn colW = new TableColumn("Poids");
						colTermNb.setCellValueFactory(
								new PropertyValueFactory<>("N")
						);
						colTerm.setCellValueFactory(
								new PropertyValueFactory<>("term")
						);
						colF.setCellValueFactory(
								new PropertyValueFactory<>("frequency")
						);
						colW.setCellValueFactory(
								new PropertyValueFactory<>("poids")
						);
						ObservableList<TableLine> data = FXCollections.observableArrayList();
						int cpt = 1;
						for(String term: d.getTermFrequency().keySet()){
							data.add(new TableLine(cpt, term, 
									d.getTermFrequency().get(term),
									d.getTermWeights().get(term)));
								//System.out.println(courant.getTermWeights().get(term));
							cpt++;
						}
						tvTermStat.getColumns().addAll(colTermNb, colTerm, colF, colW);
						tvTermStat.setItems(data);
						return tvTermStat;
					}
					private TableView wordSetTable(Document d){
						TableView tvWs = new TableView();
						TableColumn colWsNb = new TableColumn("Numéro");
						TableColumn colWs = new TableColumn("Ensemble de mots fréquent");
						TableColumn colFreq = new TableColumn("Fréquence/Support");
						TableColumn colPoids = new TableColumn("Poids");
						colWsNb.setCellValueFactory(
								new PropertyValueFactory<>("N")
						);
						colWs.setCellValueFactory(
								new PropertyValueFactory<>("wordset")
						);
						colFreq.setCellValueFactory(
								new PropertyValueFactory<>("support")
						);
						colPoids.setCellValueFactory(
								new PropertyValueFactory<>("poids")
						);
						ObservableList<TableLine> data = FXCollections.observableArrayList();
						int cpt = 0;
						d.getWordSetWeight(classit.getIndex().getWordPatterns());
						d.getWordSetFreq(classit.getIndex().getWordPatterns());
						double[] wsWeight = d.getWeightAttr();
						double[] wsSupp = d.getFreqAttr();
						int i=0;
						for(WordsPattern ws: classit.getIndex().getWordPatterns()){
							if(wsSupp[i] > 0){
								data.add(new TableLine(cpt+1, ws.getWordSet(),
										(int)wsSupp[cpt],
										wsWeight[cpt]));
								cpt++;
							}
							i++;
						}
						tvWs.setItems(data);
						tvWs.getColumns().addAll(colWsNb, colWs, colFreq, colPoids);
						return tvWs;
					}
				});
	            //setText(null);
	            //textField.selectAll();
	        }
	 
	        @Override
	        public void cancelEdit() {
	            super.cancelEdit();
	            //setText((String) getItem());
	            //setGraphic(getTreeItem().getGraphic());
	        }
	 
	        private void createTextField() {
	            //textField = new TextField(getString());
	            button.setOnKeyReleased(new EventHandler<KeyEvent>() {
	 
	                @Override
	                public void handle(KeyEvent t) {
	                    if (t.getCode() == KeyCode.ENTER) {
	                        commitEdit(button.getText());
	                    } else if (t.getCode() == KeyCode.ESCAPE) {
	                        cancelEdit();
	                    }
	                }
	            });
	        }

	        @Override
     public void updateItem(String item, boolean empty) {
         super.updateItem(item, empty);

         if (empty) {
             setText(null);
             setGraphic(null);
         } else {
                 setText(getString());
                 setGraphic(getTreeItem().getGraphic());
         }
     }
	        private String getString() {
	            return getItem() == null ? "" : getItem().toString();
	        }
	        
	    }
	
}
