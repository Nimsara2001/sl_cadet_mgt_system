package viewController;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class platoonPanelFormController {
    public AnchorPane searchOptionPane;
    public Pane addPlatoonPane;

    public void initialize(){
        searchOptionPane.setVisible(false);
        searchOptionPane.setDisable(true);
    }

    public void btnSearchOptionOnClicked(MouseEvent mouseEvent) {
        if (searchOptionPane.isVisible()) {
            searchOptionPane.setVisible(false);
            searchOptionPane.setDisable(true);
        }else {
            searchOptionPane.setVisible(true);
            searchOptionPane.setDisable(false);
        }
    }

    public void closeOptionsOnClicked(MouseEvent mouseEvent) {
        if (!( mouseEvent.getTarget()).equals(searchOptionPane)) {
            searchOptionPane.setVisible(false);
            searchOptionPane.setDisable(true);
        }
    }
}
