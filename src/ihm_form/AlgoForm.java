package ihm_form;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class AlgoForm extends Form {

	public AlgoForm() {
		// TODO Auto-generated constructor stub
		super();
		
		lblAlgo = new Label("Param�tres de l'algorithme de clustering");
		lblAlgo.setId("title");
		tfCutoff = new TextField();
		tfAcquity = new TextField();
		
		this.add(lblAlgo, 0, ++line, 2, 1);
		this.addRow(++line, new Label("Cutoff"), tfCutoff);
		this.addRow(++line, new Label("Acquity"), tfAcquity);
		this.add(new Separator(), 0, ++line, 2, 1);
		
		//Valeurs par d�faut
		tfAcquity.setText("0.1");
		tfCutoff.setText("0.002");
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