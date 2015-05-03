package ihm_form;

import weka.classifiers.trees.REPTree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
/**
 * Formulaire "Paramètre de représentation"
 */
public class ParametrerRepForm extends Form{
	
	public ParametrerRepForm() {
		// TODO Auto-generated constructor stub
		super();
		lblRep = new Label("Types de représentation");lblRep.setId("title");
		rbFreq = new RadioButton("Fréquences d'apparition des termes");
		rbPoids = new RadioButton("Poids des termes");
		rbWordSet = new RadioButton("Ensemble de termes fréquents");
		tgRep = new ToggleGroup();
		rbFreq.setToggleGroup(tgRep);
		rbPoids.setToggleGroup(tgRep);
		
		//Ensemble de termes fréquents
		rbWordSet.setToggleGroup(tgRep);
		tgWSfp = new ToggleGroup();//fréq ou poi
		tgWSmc = new ToggleGroup();//max ou closed
		tfMinSupp = new TextField();tfMinSupp.setDisable(true);
		tfMaxSupp = new TextField();tfMaxSupp.setDisable(true);
		tfMaxNb = new TextField();tfMaxNb.setDisable(true);
		tfMinNb = new TextField();tfMinNb.setDisable(true);
		
		rbWSFreq = new RadioButton("Fréquences d'apparition des ensembles");
		rbWSPoi = new RadioButton("Poids des ensembles");
		rbWSMax = new RadioButton("Ensemble maximal");
		rbWSCLos = new RadioButton("Ensemble fermé");
		rbWSFreq.setToggleGroup(tgWSfp);rbWSFreq.setDisable(true);
		rbWSPoi.setToggleGroup(tgWSfp);rbWSPoi.setDisable(true);
		rbWSCLos.setToggleGroup(tgWSmc);rbWSCLos.setDisable(true);
		rbWSMax.setToggleGroup(tgWSmc);rbWSMax.setDisable(true);

		lblMinSupp = new Label("Support minimal");lblMinSupp.setDisable(true);
		lblMaxSupp = new Label("Support maximal");lblMaxSupp.setDisable(true);
		lblMinNb = new Label("Nombre minimal de term");lblMinNb.setDisable(true);
		lblMaxNb = new Label("Nombre maximal de term");lblMaxNb.setDisable(true);
		
		// Paramètres de représentation
		this.add(lblRep, 0, ++line, 2, 1);
		this.add(rbFreq, 0, ++line);
		this.add(rbPoids, 0, ++line);
		this.add(rbWordSet, 0, ++line);
		this.addRow(++line, lblMinSupp, tfMinSupp);
		this.addRow(++line, lblMaxSupp, tfMaxSupp);
		this.add(rbWSFreq, 0, ++line);
		this.add(rbWSPoi, 0, ++line);
		this.addRow(++line, lblMinNb, tfMinNb);
		this.addRow(++line, lblMaxNb, tfMaxNb);
		this.add(rbWSMax, 0, ++line);
		this.add(rbWSCLos, 0, ++line);
		this.add(new Separator(), 0, ++line, 2, 1);

		//Valeurs par défaut
		rbWSMax.setSelected(true);
		tfMinSupp.setText("20");
		tfMaxSupp.setText("50");
		tfMinNb.setText("1");
		tfMaxNb.setText("3");
		rbWSPoi.setSelected(true);
		rbPoids.setSelected(true);
		runtimeEventsHandler();
	}

	/**
	 * Prise en charge des événements de type temps réel
	 */
	private void runtimeEventsHandler() {
		
		//RadioButton Fréquences
		rbFreq.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rbWSFreq.setDisable(true);
				rbWSPoi.setDisable(true);
				rbWSMax.setDisable(true);
				rbWSCLos.setDisable(true);
				tfMinSupp.setDisable(true);
				tfMaxSupp.setDisable(true);
				tfMinNb.setDisable(true);
				tfMaxNb.setDisable(true);
				lblMinSupp.setDisable(true);
				lblMaxSupp.setDisable(true);
				lblMinNb.setDisable(true);
				lblMaxNb.setDisable(true);
				
			}
		});
		
		//RadioButton Poids
		rbPoids.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rbWSFreq.setDisable(true);
				rbWSPoi.setDisable(true);
				rbWSMax.setDisable(true);
				rbWSCLos.setDisable(true);
				tfMinSupp.setDisable(true);
				tfMaxSupp.setDisable(true);
				tfMinNb.setDisable(true);
				tfMaxNb.setDisable(true);
				lblMinSupp.setDisable(true);
				lblMaxSupp.setDisable(true);
				lblMinNb.setDisable(true);
				lblMaxNb.setDisable(true);
				
			}
		});
		
		//RadioButton Frequent words set
		rbWordSet.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				rbWSFreq.setDisable(false);
				rbWSPoi.setDisable(false);
				rbWSMax.setDisable(false);
				rbWSCLos.setDisable(false);
				tfMinSupp.setDisable(false);
				tfMaxSupp.setDisable(false);
				tfMinNb.setDisable(false);
				tfMaxNb.setDisable(false);
				lblMinSupp.setDisable(false);
				lblMaxSupp.setDisable(false);
				lblMinNb.setDisable(false);
				lblMaxNb.setDisable(false);
			}
		});
	}
	public char getRepType(){
		if(rbWordSet.isSelected())
			return  'e';
		if (rbFreq.isSelected())
			return 'f';
		if (rbPoids.isSelected())
			return 'p';
		return 'n';
	}
	/**
	 * Valeur retournée
	 * Fréquence: 0
	 * Poids : 1
	 * @return
	 */
	
	public int getRepTypeWS(){
		if(rbWSFreq.isSelected())
			return 0;
		if(rbWSPoi.isSelected())
			return 1;
		return -1;
	}
	

	public float getMinSupp() {
		// TODO Auto-generated method stub
		if(tfMinSupp.getText().isEmpty())
			return -1;
		else
			return Float.valueOf(tfMinSupp.getText());
	}

	public float getMaxSupp() {
		// TODO Auto-generated method stub
		if(tfMaxSupp.getText().isEmpty())
			return -1;
		else
			return Float.valueOf(tfMaxSupp.getText());
	}

	public int getMinTermNb() {
		// TODO Auto-generated method stub
		if(tfMinNb.getText().isEmpty())
			return -1;
		else
			return Integer.valueOf(tfMinNb.getText());
	}

	public int getMaxTermNb() {
		// TODO Auto-generated method stub
		if(tfMaxNb.getText().isEmpty())
			return -1;
		else
			return Integer.valueOf(tfMaxNb.getText());
	}
	/**
	 * Valeurs retournées
	 * c:closed
	 * m:max
	 * @return
	 */
	public char getWordSetForm() {
		// TODO Auto-generated method stub
		if(rbWSCLos.isSelected())
			return 'c';
		
		return 'm';
	}
	
	protected Label lblRep;
	protected ToggleGroup tgRep;
	protected ToggleGroup tgWSfp;
	protected ToggleGroup tgWSmc;
	protected TextField tfMinSupp;
	protected TextField tfMaxSupp;
	protected TextField tfMinNb;
	protected TextField tfMaxNb;
	protected RadioButton rbFreq;
	protected RadioButton rbPoids;
	protected RadioButton rbWordSet;
	protected RadioButton rbWSFreq;
	protected RadioButton rbWSPoi;
	protected RadioButton rbWSMax;
	protected RadioButton rbWSCLos;
	protected Label lblMinSupp;
	protected Label lblMaxSupp;
	protected Label lblMinNb;
	protected Label lblMaxNb;
	
}