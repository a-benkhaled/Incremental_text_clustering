package ihm_chart;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import text_clustering.IncrementalClustering;
import word_mining.WordsPattern;
import edu.stanford.nlp.dcoref.Dictionaries.Person;

public class TermTable{
	IncrementalClustering classit;
	public TermTable(IncrementalClustering c) {
		// TODO Auto-generated constructor stub
		classit = c;
	}
	
	public BorderPane termTable(){
		Callback<TableColumn, TableCell> cellFactory =
		        new Callback<TableColumn, TableCell>() {
		            public TableCell call(TableColumn p) {
		                TableCell cell = new TableCell<Person, String>() {
		                    @Override
		                    public void updateItem(String item, boolean empty) {
		                        super.updateItem(item, empty);
		                        setText(empty ? null : getString());
		                        setGraphic(null);
		                    }

		                    private String getString() {
		                        return getItem() == null ? "" : getItem().toString();
		                    }
		                };

		                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
		                    @Override
		                    public void handle(MouseEvent event) {
		                        if (event.getClickCount() > 1) {
		                            System.out.println("double clicked!");
		                            TableCell c = (TableCell) event.getSource();
		                            System.out.println("Cell text: " + c.getText());
		                        }
		                    }
		                });
		                return cell;
		            }
		        };
		BorderPane table = new BorderPane();
		TableView tvTerm = new TableView();
		tvTerm.setEditable(true);
		TableColumn colTermNb = new TableColumn("Numéro");
		TableColumn colTerm = new TableColumn("Terme");
		TableColumn colCF = new TableColumn("Frequence");
		colTermNb.setCellFactory(cellFactory);
		colTerm.setCellFactory(cellFactory);
		colCF.setCellFactory(cellFactory);
		colTermNb.setCellValueFactory(
				new PropertyValueFactory<>("N")
		);
		colTerm.setCellValueFactory(
				new PropertyValueFactory<>("term")
		);
		colCF.setCellValueFactory(
				new PropertyValueFactory<>("frequency")
		);
		ObservableList<TableLine> data = FXCollections.observableArrayList();
		int cpt = 1;
		for(String term: classit.getIndex().getTermSpace().keySet()){
			
			data.add(new TableLine(cpt, term, 
					classit.getIndex().getTermSpace().get(term)));
			cpt++;
		}
		HBox hbTxt = new HBox();
		Text txt =new Text("Fréquences de collection (termes)");
		hbTxt.setPadding(new Insets(10,10,10,10));
		hbTxt.getChildren().add(txt);
		tvTerm.setItems(data);
		tvTerm.getColumns().addAll(colTermNb, colTerm, colCF);
		
		table.setTop(hbTxt);
		table.setCenter(tvTerm);
		return table;
	}
	
	public BorderPane wordSetTable(){
		BorderPane table = new BorderPane();
		TableView tvWs = new TableView();
		TableColumn colWsNb = new TableColumn("Numéro");
		TableColumn colWs = new TableColumn("Ensenble de mots fréquent");
		TableColumn colSupp = new TableColumn("Support");
		colWsNb.setCellValueFactory(
				new PropertyValueFactory<>("N")
		);
		colWs.setCellValueFactory(
				new PropertyValueFactory<>("wordset")
		);
		colSupp.setCellValueFactory(
				new PropertyValueFactory<>("support")
		);
		ObservableList<TableLine> data = FXCollections.observableArrayList();
		int cpt = 1;
		for(WordsPattern ws: classit.getIndex().getWordPatterns()){
			data.add(new TableLine(cpt, ws.getWordSet(),(int)ws.getSupport()));
			cpt++;
		}
		HBox hbTxt = new HBox();
		Text txt =new Text("Fréquences de collection (ensemble fréquents)");
		hbTxt.setPadding(new Insets(10,10,10,10));
		hbTxt.getChildren().add(txt);
		tvWs.setItems(data);
		tvWs.getColumns().addAll(colWsNb, colWs, colSupp);
		
		table.setTop(hbTxt);
		table.setCenter(tvWs);
		return table;
	}
}
