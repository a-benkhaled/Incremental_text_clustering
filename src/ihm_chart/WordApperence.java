package ihm_chart;

import ihm.GUI;

import java.util.Comparator;
import java.util.HashMap;

import doc.Indexer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class WordApperence extends BorderPane{
	/**
	 * Centre: chart,
	 * Top: button
	 * @param index
	 * @param reptype
	 */
	public WordApperence(Indexer index, char reptype){
		CategoryAxis xAxis = new CategoryAxis();
		NumberAxis yAxis = new NumberAxis();
		HashMap<String, Integer> spaceFreq = index.getCollectionFreq();
		Button btnFreq = new Button("Fréquences");
		Button btnDst = new Button("Densité");
		HBox hb = new HBox();
		hb.setPadding(new Insets(10,10,10,10));
		hb.getChildren().add(btnFreq);
		hb.getChildren().add(btnDst);
		/** ***ACTIONS ************** */
		btnFreq.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				float termPixel = 0;
				BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
				XYChart.Series series = new XYChart.Series();
				xAxis.setLabel("Terms");
				yAxis.setLabel("Fréquences");
				for(String term:spaceFreq.keySet()){
					series.getData().add(new XYChart.Data(term, spaceFreq.get(term)));
					termPixel += 7;
				}
				bc.setPrefWidth(termPixel);
				bc.getData().add(series);
				((BorderPane)((HBox)((Button)arg0.getSource()).getParent()).getParent()).setCenter(bc);
					
			}
		});
		btnDst.setOnAction(new EventHandler<ActionEvent>() {
					
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				float termPixel = 0;
				BarChart<String, Number> bc = new BarChart<>(xAxis,yAxis);
				XYChart.Series series = new XYChart.Series();
				xAxis.setLabel("Terms");
				yAxis.setLabel("Densité");
				for(String term:spaceFreq.keySet()){
					series.getData().add(new XYChart.Data(term,(float) spaceFreq.get(term)/index.getNumberOfDoc()));
					termPixel += 7;
				}
				bc.setPrefWidth(termPixel);
				series.setData(series.getData().sorted());
				bc.getData().add(series);
				System.out.println(((HBox)((Button)arg0.getSource()).getParent()).getParent());
					((BorderPane)((HBox)((Button)arg0.getSource()).getParent()).getParent()).setCenter(bc);
			}
		});
		this.setTop(hb);
	}
}