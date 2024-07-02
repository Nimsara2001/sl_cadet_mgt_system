package viewController;


import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import controller.cadetController;
import controller.companyController;
import controller.schoolController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.School;
import org.controlsfx.control.textfield.TextFields;
import tm.addCompanyNcoTM;
import tm.allCompanyTM;
import tm.companyDetailNcoTM;
import util.Validation;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class companyPanelFormController {
    
    public Label lblNumOfPlatoon;
    public Label lblCompanyCode;
    public TableView<companyDetailNcoTM> tblCompanyDetailNco;
    public Label lblNumOfCadet;
    public Label lblNumOfNco;
    public Label lblTotalCompany;
    public TableView<allCompanyTM> tblAllCompanies;
    public AnchorPane addOrEditPane;
    public TextField txtCompanyCode;
    public JFXButton btnAddOrEdit;
    public Pane addOrEditNotificationPane;
    public Label lblAddOrEditNotification;
    public TextField txtSearchCadet;
    public TableView<addCompanyNcoTM> tblAddCompanyNco;
    public Label lblTitleTxt;
    public AnchorPane workingPane;
    public AnchorPane companyDetailPane;
    public ImageView backImage;
    int x=0;
    public void initialize(){
        addOrEditPane.setVisible(false);
        addOrEditNotificationPane.setVisible(false);
        visibleOfDetailPane(false);
        storeNodes();
        loadAlCompanies();
        try {
            suggestCadetNco();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblAddCompanyNco.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("regNo"));
        tblAddCompanyNco.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("rank"));
        tblAddCompanyNco.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblAddCompanyNco.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("remove"));

        tblAllCompanies.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        tblAllCompanies.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("comCode"));

        tblCompanyDetailNco.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("regNo"));
        tblCompanyDetailNco.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("rank"));
        tblCompanyDetailNco.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("name"));

        tblAllCompanies.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                setCompanyDetailToPane((int)newValue);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        tblAddCompanyNco.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            x=(int)newValue;
        });
    }

    private void setCompanyDetailToPane(int newValue) throws SQLException, ClassNotFoundException {
        if (newValue>=0){
            String comCode = tblAllCompanies.getItems().get(newValue).getComCode();
            ObservableList<companyDetailNcoTM> companyNcos = companyController.getCompanyNcos(comCode);
            if (companyNcos!=null){
                visibleOfDetailPane(true);
                tblCompanyDetailNco.setItems(companyNcos);
                lblNumOfNco.setText(String.valueOf(companyNcos.size()));
                lblCompanyCode.setText(comCode);
            }
        }
    }


    public void btnAddCompanyOnClicked(MouseEvent mouseEvent)  {
        if (!addOrEditPane.isVisible()){
            effectAddOrEdit(true);
            new ZoomIn(addOrEditPane).play();

            txtCompanyCode.setEditable(true);
            btnAddOrEdit.setText("Add");
            lblTitleTxt.setText("ADD COMPANY");
            txtCompanyCode.clear();
            txtCompanyCode.setText("15/COM/");
            addOrEditNotificationPane.setVisible(false);
            new Validation().changeNodesEffect(nodes,true);
        }else{
            effectAddOrEdit(false);
        }
    }

    private void effectAddOrEdit(boolean value){
        ObservableList<Node> workingPaneChildren = workingPane.getChildren();
        for (Node n:
                workingPaneChildren) {
            if (!n.equals(addOrEditPane)){
                if (value){
                    n.setStyle("-fx-opacity : 0.5");
                    addOrEditPane.setVisible(true);
                }else {
                    n.setStyle("-fx-opacity : 1.0");
                    addOrEditPane.setVisible(false);
                }
            }
        }
    }

    public void EditDetailsOnClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        lblTitleTxt.setText("EDIT COMPANY DETAILS");
        btnAddOrEdit.setText("Save");
        if (!addOrEditPane.isVisible()){
            addOrEditPane.setVisible(true);
            new ZoomIn(addOrEditPane).play();
            effectAddOrEdit(true);
            txtCompanyCode.setText(lblCompanyCode.getText());
            obAddNco.clear();
            ObservableList<companyDetailNcoTM> companyNcos = companyController.getCompanyNcos(lblCompanyCode.getText());

            companyNcos.forEach(c -> {
                JFXButton btn = new JFXButton("Remove");
                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        tblAddCompanyNco.getItems().remove(x);
                    }
                });
                obAddNco.add(new addCompanyNcoTM(
                        c.getRegNo(),
                        c.getRank(),
                        c.getName(),
                        btn
                ));
            });
            tblAddCompanyNco.setItems(obAddNco);
            txtCompanyCode.setEditable(false);

        }else{
            addOrEditPane.setVisible(false);
            effectAddOrEdit(false);
        }

    }

    public void RemoveOnClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert=new Alert(Alert.AlertType.
                WARNING,"Sure to delete ? ",ButtonType.NO,ButtonType.YES);

        if (alert.showAndWait().get()==ButtonType.YES) {
            if (Integer.parseInt(lblNumOfPlatoon.getText()) == 0 ) {
                if (companyController.removeCompanyFromDB(lblCompanyCode.getText())) {
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted..").showAndWait();
                    visibleOfDetailPane(false);
                    loadAlCompanies();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try Again..").showAndWait();
                }


            } else {
                if (companyController.removeCompany(lblCompanyCode.getText())) {
                    visibleOfDetailPane(false);
                    loadAlCompanies();
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted..").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try Again..").showAndWait();
                }
            }
        }
    }

    public void singleCompanySavePdfOnClicked(ActionEvent actionEvent) {
    }

    public void singleCompanyPrintOnClicked(ActionEvent actionEvent) {
    }

    public void addTxtFieldOnKeyReleased(KeyEvent keyEvent) {
        addOrEditNotificationPane.setVisible(false);
        new Validation().changeNodesEffect(nodes,true);
        if (keyEvent.getCode()==KeyCode.ENTER){
            for (int i = 0; i < nodes.size(); i++) {
                if (keyEvent.getSource().equals(nodes.get(i))){
                    nodes.get(i+1).requestFocus();
                }
            }
        }
    }
    public void btnAddOrEditOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ObservableList<addCompanyNcoTM> Nco = tblAddCompanyNco.getItems();
        if (lblTitleTxt.getText().equals("ADD COMPANY") & btnAddOrEdit.getText().equals("Add")) {
            if ( (new Validation().checkTxtField(txtCompanyCode,companyCodeP) &&
                new Validation().checkDuplicate(txtCompanyCode,"SELECT companyCode FROM Company WHERE companyCode=?")) &
                !tblAddCompanyNco.getItems().isEmpty()
            ){
                if (companyController.addCompany(txtCompanyCode.getText(),Nco)){
                    txtCompanyCode.setText("15/COM/");
                    txtSearchCadet.clear();
                    tblAddCompanyNco.getItems().clear();
                    setNotificationPaneStyle(true);
                    new Validation().changeNodesEffect(nodes,true);
                    loadAlCompanies();
                    visibleOfDetailPane(false);
                }else {
                    setNotificationPaneStyle(false);
                }
            }else {
                setNotificationPaneStyle(false);
            }
        }else if (lblTitleTxt.getText().equals("EDIT COMPANY DETAILS") & btnAddOrEdit.getText().equals("Save")){
            if (!tblAddCompanyNco.getItems().isEmpty()){
                if (companyController.updateCompany(txtCompanyCode.getText(),tblAddCompanyNco.getItems())){
                    txtCompanyCode.clear();
                    txtSearchCadet.clear();
                    tblAddCompanyNco.getItems().clear();
                    setNotificationPaneStyle(true);
                    new Validation().changeNodesEffect(nodes,true);
                    loadAlCompanies();
                    visibleOfDetailPane(false);
                }else {
                    setNotificationPaneStyle(false);
                }
            }else {
                setNotificationPaneStyle(false);
            }
        }
    }

    private void loadAlCompanies(){
        try {
            ObservableList<allCompanyTM> allCompanies = companyController.getAllCompanies();
            tblAllCompanies.setItems(allCompanies);
            lblTotalCompany.setText(String.valueOf(allCompanies.size()));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    ObservableList<addCompanyNcoTM> obAddNco= FXCollections.observableArrayList();
    public void btnAddToTableOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (!txtSearchCadet.getText().isEmpty()){
            companyDetailNcoTM ncoDetail = cadetController.getNcoDetail(txtSearchCadet.getText());
            if (ncoDetail!=null){
                for (addCompanyNcoTM tm:
                     obAddNco) {
                    if (tm.getRegNo().equals(txtSearchCadet.getText())){
                        return;
                    }
                }
                JFXButton btn=new JFXButton("Remove");

                btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                            tblAddCompanyNco.getItems().remove(x);


                    }
                });
                obAddNco.add(new addCompanyNcoTM(
                   ncoDetail.getRegNo(),
                   ncoDetail.getRank(),
                   ncoDetail.getName(),
                   btn
                ));
                tblAddCompanyNco.setItems(obAddNco);
                txtSearchCadet.clear();
            }
        }
    }


    private void suggestCadetNco() throws SQLException, ClassNotFoundException {
        TextFields.bindAutoCompletion(txtSearchCadet, cadetController.getCadetNcoRegCode());
    }

    public void closeOnClicked(MouseEvent mouseEvent) {
        effectAddOrEdit(false);
        addOrEditNotificationPane.setVisible(false);
        txtSearchCadet.clear();
        tblAddCompanyNco.getItems().clear();
        obAddNco.clear();
    }


    LinkedList<Node> nodes =new LinkedList<>();
    Pattern companyCodeP=Pattern.compile("^(15/COM/)[A-Z]$");
    private void storeNodes(){
        nodes.add(txtCompanyCode);
        nodes.add(txtSearchCadet);
    }

    private void visibleOfDetailPane(boolean b){
        ObservableList<Node> children = companyDetailPane.getChildren();
        for (Node n:
                children) {
            if (!n.equals(backImage)) {
                n.setVisible(b);
            }
        }
    }

    private void setNotificationPaneStyle(boolean b){
        addOrEditNotificationPane.setVisible(true);
        if (b){
            addOrEditNotificationPane.setStyle("-fx-background-color: #66cc66");
            lblAddOrEditNotification.setText("Successfully Added..");
        }else {
            addOrEditNotificationPane.setStyle("-fx-background-color: #ff8880");
            lblAddOrEditNotification.setText("An error has occurred. Please try again..");
        }
    }


    public void closePaneOnMouseDragged(MouseEvent mouseEvent) {
    }

    public void closePaneOnMousePressed(MouseEvent mouseEvent) {
    }
}
