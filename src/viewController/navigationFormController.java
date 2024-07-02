package viewController;

import animatefx.animation.SlideInLeft;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class navigationFormController {
    public StackPane rootPanel;
    public JFXButton btnDashboard;
    public JFXButton btnPlatoon;
    public JFXButton btnSchool;
    public JFXButton btnCadet;
    public JFXButton btnOfficer;
    public JFXButton btnZone;
    public JFXButton btnCamping;
    public JFXButton btnCashReport;
    public JFXButton btnSetting;
    public JFXButton btnSupport;
    public JFXButton btnCompanies;
    double x,y;

    public void initialize(){
        try {
            AnchorPane pane;
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/dashboardPanelForm.fxml"));
            pane = fxmlLoader.load();
            this.rootPanel.getChildren().setAll(pane);
            btnDashboard.setStyle("-fx-background-color: #101820FF; -fx-text-fill:#FEE715FF ; -fx-font-size: 16;");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closePaneOnMouseDragged(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    public void closePaneOnMousePressed(MouseEvent mouseEvent) {
        x= mouseEvent.getSceneX();
        y=mouseEvent.getSceneY();
    }

    public void closeOnClicked(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimizeOnClicked(MouseEvent mouseEvent) {
        Stage stage= (Stage) ((Node)mouseEvent.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    //navigation Button Actions-------------------------------------------------------------

    public void btnDashboardOnClicked(MouseEvent mouseEvent) {
        if (!btnDashboard.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/dashboardPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnDashboard);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void btnPlatoonOnClicked(MouseEvent mouseEvent) {
        if (!btnPlatoon.getStyle().equals(selectedBtnStyle)){
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/platoonPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnPlatoon);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnSchoolOnClicked(MouseEvent mouseEvent) {
        if (!btnSchool.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/schoolPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnSchool);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void btnCadetOnClicked(MouseEvent mouseEvent) {
        if (!btnCadet.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/cadetPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnCadet);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void btnOfficerOnClicked(MouseEvent mouseEvent) {
        if (!btnOfficer.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/officerPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnOfficer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void btnZoneOnClicked(MouseEvent mouseEvent) {
        if (!btnZone.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/zonePanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnZone);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void btnCampingOnClicked(MouseEvent mouseEvent) {
        if (!btnCamping.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/campingPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnCamping);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void btnCashReportOnClicked(MouseEvent mouseEvent) {
        if (!btnCashReport.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/cashReportPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnCashReport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnSettingOnClicked(MouseEvent mouseEvent) {
        if (!btnSetting.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/settingPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnSetting);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnSupportOnClicked(MouseEvent mouseEvent) {
        if (!btnSupport.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/supportPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnSupport);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnCompaniesOnClicked(MouseEvent mouseEvent) {
        if (!btnCompanies.getStyle().equals(selectedBtnStyle)) {
            try {
                AnchorPane pane;
                FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("../view/companyPanelForm.fxml"));
                pane = fxmlLoader.load();
                rootPanel.getChildren().setAll(pane);
                new SlideInLeft(rootPanel).play();
                setSelectedBtnStyle(btnCompanies);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    String selectedBtnStyle="-fx-background-color: #101820FF; -fx-text-fill:#FEE715FF ; -fx-font-size: 16;" ;

    private void setSelectedBtnStyle(JFXButton btn){

        btnDashboard.setStyle("");
        btnPlatoon.setStyle("");
        btnSchool.setStyle("");
        btnCadet.setStyle("");
        btnOfficer.setStyle("");
        btnZone.setStyle("");
        btnCamping.setStyle("");
        btnCashReport.setStyle("");
        btnSetting.setStyle("");
        btnSupport.setStyle("");
        btnCompanies.setStyle("");
        btn.setStyle(selectedBtnStyle);
    }

}
