package ihm_form;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;

public class ModelInfoForm extends Form{
	
	public ModelInfoForm() {
		// TODO Auto-generated constructor stub
		super();
		tfName = new TextField();
		this.addRow(++line, new Label("Nom du modèle"), tfName);
		this.add(new Separator(), 0, ++line, 2, 1);
	}
	public String getModelName(){
		return tfName.getText();
	}
	protected TextField tfName;
}
