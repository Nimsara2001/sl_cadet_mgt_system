package viewController;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class cadetPanelFormController {
    public AnchorPane searchOptionPane;

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
        if (!(mouseEvent.getTarget()).equals(searchOptionPane)) {
            searchOptionPane.setVisible(false);
            searchOptionPane.setDisable(true);
        }
    }

    public void EditDetailsOnClicked(ActionEvent actionEvent) {
    }

    public void RemoveOnClicked(ActionEvent actionEvent) {
    }

    public void singleCadetSavePdfOnClicked(ActionEvent actionEvent) {
    }

    public void singleCadetPrintOnClicked(ActionEvent actionEvent) {
    }

    public void closeOnClicked(MouseEvent mouseEvent) {
    }
}
