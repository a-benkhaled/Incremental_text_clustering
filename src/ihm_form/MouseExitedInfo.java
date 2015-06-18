package ihm_form;
import ihm.GUI;
import ihm.Menu_File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MouseExitedInfo implements EventHandler{

	@Override
	public void handle(Event arg0) {
		// TODO Auto-generated method stub
		VBox empty = new VBox();
		Menu_File.lblTooltip.setText("");
	}

}
