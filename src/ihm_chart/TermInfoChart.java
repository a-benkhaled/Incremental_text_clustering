package ihm_chart;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import doc.Document;
import doc.Indexer;

public class TermInfoChart extends BorderPane {

	public TermInfoChart(Indexer index) {
		TextField tfTerm = new TextField("Term");
		Button btnAff = new Button("Afficher");
		HBox hb = new HBox();
		hb.setPadding(new Insets(10, 10, 10, 10));
		hb.getChildren().add(tfTerm);
		hb.getChildren().add(btnAff);
		ArrayList<Document> docList = index.getListOfDocument();
		
		btnAff.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				CategoryAxis xAxis = new CategoryAxis();
				NumberAxis yAxis = new NumberAxis();
				BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
				bc.setScaleShape(true);
				XYChart.Series series = new XYChart.Series();
				xAxis.setLabel("Dcuments");
				yAxis.setLabel("Fréquences");
				float width = 0;
				if(!tfTerm.getText().isEmpty())
					for (Document doc : docList) {
						//System.out.println(doc.getFileName());
						//System.out.print(tfTerm.getText());
						//System.out.println(doc.getTokenFreq(tfTerm.getText()));
						width+=10.0;
						series.getData().add(
								new XYChart.Data(doc.getFileName(), doc.getTokenFreq(tfTerm.getText())));
					}
				bc.setPrefWidth(width);
				bc.getData().add(series);
				((BorderPane)((HBox)((Button)arg0.getSource()).getParent()).getParent()).setCenter(bc);
			}
		});
		this.setTop(hb);
	}

}
