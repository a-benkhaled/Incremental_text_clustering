package ihm_form;
import ihm.GUI;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MouseEnteredInfo implements EventHandler{
	private String msg;
	public MouseEnteredInfo(String m) {
		// TODO Auto-generated constructor stub
		msg = m;
	}
	@Override
	public void handle(Event arg0) {
		// TODO Auto-generated method stub
		VBox tmpInfo = new VBox();
		Text txthelp = new Text(msg);
		txthelp.setId("lblhelp");
		tmpInfo.getChildren().add(txthelp);
		GUI.infoMainView.setCenter(tmpInfo);
	}

}
