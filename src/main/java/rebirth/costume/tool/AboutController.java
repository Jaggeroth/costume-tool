package rebirth.costume.tool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AboutController {
//#AboutExitAction

  @FXML
  private void aboutExitAction(ActionEvent event) {
    if (event.getSource() instanceof Button) {
      Button exitB = (Button)event.getSource();
      exitB.getParent().getScene().getWindow().hide();
    }
  }
}
