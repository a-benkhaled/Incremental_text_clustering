package ihm_form;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class AlgoForm extends Form {
	public int line = 0;
	public AlgoForm() {
		// TODO Auto-generated constructor stub
		super();
		
		lblAlgo = new Label("Paramètres de l'algorithme de clustering");
		lblAlgo.setId("title");
		tfCutoff = new TextField();
		tfAcquity = new TextField();
		
		this.add(lblAlgo, 0, ++line, 2, 1);
		this.addRow(++line, new Label("Cutoff"), tfCutoff);
		this.addRow(++line, new Label("Acquity"), tfAcquity);
		this.add(new Separator(), 0, ++line, 2, 1);
		
		//Valeurs par défaut
		tfAcquity.setText("0.002");
		tfCutoff.setText("1.0");
	}
	
	public float getCutOff(){
		if(tfCutoff.getText().isEmpty())
			return -1;
		else
			return Float.valueOf(tfCutoff.getText());
	}
	
	public float getAcuity(){
		if(tfAcquity.getText().isEmpty())
			return -1;
		else
			return Float.valueOf(tfAcquity.getText());
	}
	
	protected Label lblAlgo;
	protected TextField tfCutoff;
	protected TextField tfAcquity;
}
