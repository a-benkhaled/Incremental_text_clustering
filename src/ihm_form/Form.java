package ihm_form;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Form extends GridPane{
	public Stage formTmpStage;
	public Form() {
		// TODO Auto-generated constructor stub
		super();
		formTmpStage = new Stage();
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(0, 10, 0, 10));
	}
	
	public void configDefaultDirectory(FileChooser fc){
		fc.setInitialDirectory(new File(System.getProperty("user.home")));
	}
}
