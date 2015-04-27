package ihm_form;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class Form extends GridPane{
	protected int line;
	public Form() {
		// TODO Auto-generated constructor stub
		super();
		line =0;
		this.setHgap(10);
		this.setVgap(10);
		this.setPadding(new Insets(0, 10, 0, 10));
	}
}
