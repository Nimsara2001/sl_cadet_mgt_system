package viewController;

import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import controller.schoolController;
import controller.zoneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import tm.sclSearchTM;
import util.Validation;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class schoolPanelFormController {
    public AnchorPane searchOptionPane;
    public AnchorPane workingPane;
    public AnchorPane addOrEditPane;
    public TextField txtSclCode;
    public ComboBox cmbZone;
    public TextField txtSclName;
    public TextField txtContactNo;
    public TextField txtEmail;
    public TextField txtSclAddress;
    public JFXButton btnAddOrEdit;
    public Label lblTitleTxt;
    public TableView<sclSearchTM> tblSclSearchResult;
    public Label lblSclCode;
    public Label lblSclName;
    public Label lblNumOfPlatoon;
    public Label lblNumOfCadet;
    public Label lblNumOfCadetNco;
    public Label lblNumOfOfficer;
    public Label lblZone;
    public Label lblAddress;
    public Label lblContactNo;
    public Label lblEmail;
    public Pane addOrEditNotificationPane;
    public Label lblAddOrEditNotification;
    public javafx.scene.control.ScrollPane ScrollPane;
    public Label lblSearchResults;
    public AnchorPane sclDetailPane;
    public ImageView backImage;

    public void initialize(){
        searchOptionPane.setVisible(false);
        addOrEditPane.setVisible(false);
        addOrEditNotificationPane.setVisible(false);
        visibleOfSclDetailPane(false);
        storeNodes();
        try {
            loadZoneNameToCmb();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblSclSearchResult.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        tblSclSearchResult.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("sclCode"));
        tblSclSearchResult.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("sclName"));

        tblSclSearchResult.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            setSchoolDetailsToPane((int)newValue);
        });

    }


    private void setSchoolDetailsToPane(int newValue) {
        if (newValue>=0) {
            try {
                School s= schoolController.getMatchedSchool(obList.get(newValue).getSclCode());
                if (s != null) {
                    visibleOfSclDetailPane(true);
                    lblSclCode.setText(s.getSclCode());
                    lblSclName.setText(s.getName());
                    lblZone.setText(zoneController.getZoneNameForCode(s.getZoneCode()));
                    lblAddress.setText(s.getAddress());
                    lblContactNo.setText(s.getContactNo());
                    lblEmail.setText(s.geteMail());
                }


            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private void visibleOfSclDetailPane(boolean b){
        ObservableList<Node> children = sclDetailPane.getChildren();
        for (Node n:
             children) {
            if (!n.equals(backImage)) {
                n.setVisible(b);
            }
        }
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
        if (!mouseEvent.getTarget().equals(searchOptionPane)) {
            searchOptionPane.setVisible(false);
            searchOptionPane.setDisable(true);
        }
    }

    public void btnAddOrEditOnClicked(MouseEvent mouseEvent)  {
        try {
            addOrEdit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addOrEdit() throws SQLException, ClassNotFoundException {
        if (lblTitleTxt.getText().equals("ADD SCHOOL") & btnAddOrEdit.getText().equals("Add")) {
            if ((new Validation().checkTxtField(txtSclCode,sclCodeP) &&
                    new Validation().checkDuplicate(txtSclCode,"SELECT sclCode FROM School WHERE sclCode=?")) &
                    new Validation().checkTxtField(txtContactNo,contactNoP) &
                    new Validation().checkTxtField(txtEmail,eMailP) &
                    new Validation().checkTxtField(txtSclName,sclNameP) &
                    new Validation().checkTxtField(txtSclAddress,addressP) &
                    new Validation().checkCmbBox(cmbZone)
            ){
                String zCode = zoneController.getZoneCodeForName((String) cmbZone.getValue());
                School scl =new School(
                        txtSclCode.getText(),
                        txtSclName.getText(),
                        txtSclAddress.getText(),
                        txtContactNo.getText(),
                        txtEmail.getText(),
                        zCode
                );

                if (schoolController.addSchool(scl)){
                    setNotificationPaneStyle(true);
                    clearTxtField();
                    txtSclCode.setText(schoolController.suggestSclCode());
                    cmbZone.getSelectionModel().clearSelection();
                    txtContactNo.requestFocus();
                    resetAddOrEditOptionNode();
                }else {
                    setNotificationPaneStyle(false);
                }
            }else {
                setNotificationPaneStyle(false);
            }


        } else if (lblTitleTxt.getText().equals("EDIT SCHOOL DETAILS") & btnAddOrEdit.getText().equals("Save")) {
            if (
                    new Validation().checkTxtField(txtContactNo,contactNoP) &
                            new Validation().checkTxtField(txtEmail,eMailP) &
                            new Validation().checkTxtField(txtSclName,sclNameP) &
                            new Validation().checkTxtField(txtSclAddress,addressP) &
                            new Validation().checkCmbBox(cmbZone)
            ){
                String zCode = zoneController.getZoneCodeForName((String) cmbZone.getValue());
                School scl1 =new School(
                        txtSclCode.getText(),
                        txtSclName.getText(),
                        txtSclAddress.getText(),
                        txtContactNo.getText(),
                        txtEmail.getText(),
                        zCode
                );

                if (schoolController.updateSchool(scl1)){
                    clearTxtField();
                    setNotificationPaneStyle(true);
                }else {
                    setNotificationPaneStyle(false);
                }
            }
        }else {
            setNotificationPaneStyle(false);
        }
    }

    public void closeOnClicked(MouseEvent mouseEvent) {
        addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
        effectAddOrEdit("-fx-opacity : 1.0",false);
        addOrEditNotificationPane.setVisible(false);
        resetAddOrEditOptionNode();

        try {
                ArrayList<School> allSchool = schoolController.getAllSchool();
                tblSclSearchResult.getSelectionModel().clearSelection();
                tblSclSearchResult.getItems().clear();
                loadSchoolToTable(allSchool);
                visibleOfSclDetailPane(false);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    private void effectAddOrEdit(String style,boolean value){
        ObservableList<Node> workingPaneChildren = workingPane.getChildren();
        for (Node n:
                workingPaneChildren) {
            if (!n.equals(addOrEditPane)){
                n.setStyle(style);
                n.setDisable(value);
            }
        }
    }

    public void addSclOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (!addOrEditPane.isVisible()){
            addOrEditPane.setVisible(true); addOrEditPane.setDisable(false);
            effectAddOrEdit("-fx-opacity : 0.5",true);
            new ZoomIn(addOrEditPane).play();

            txtSclCode.setEditable(true);
            btnAddOrEdit.setText("Add");
            lblTitleTxt.setText("ADD SCHOOL");
            clearTxtField();
            txtSclCode.setText(schoolController.suggestSclCode());
            txtContactNo.requestFocus();
            addOrEditNotificationPane.setVisible(false);

        }else{
            addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
            effectAddOrEdit("-fx-opacity : 1.0",false);
        }

    }
    private void loadZoneNameToCmb() throws SQLException, ClassNotFoundException {
        List<String> allZoneName = zoneController.getAllZoneName();
        cmbZone.getItems().addAll(allZoneName);
    }
    private void clearTxtField() {
        txtSclCode.clear();
        txtContactNo.clear();
        txtEmail.clear();
        txtSclName.clear();
        txtSclAddress.clear();
        cmbZone.getSelectionModel().clearSelection();
    }

    public void viewAllOnClicked(MouseEvent mouseEvent) {
        try {
            if (tblSclSearchResult.getItems().isEmpty()){
                ArrayList<School> allSchool = schoolController.getAllSchool();
                loadSchoolToTable(allSchool);
                ScrollPane.setVvalue(1.0);
            }else {
                tblSclSearchResult.getSelectionModel().clearSelection();
                tblSclSearchResult.getItems().clear();
                lblSearchResults.setText("00");
                visibleOfSclDetailPane(false);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    ObservableList<sclSearchTM> obList = FXCollections.observableArrayList();
    private void loadSchoolToTable(ArrayList<School> allSchool) {
        allSchool.forEach(e->{
            obList.add(new sclSearchTM(
                    new CheckBox(),
                    e.getSclCode(),
                    e.getName()
            ));
        });
        tblSclSearchResult.setItems(obList);
        tblSclSearchResult.refresh();
        lblSearchResults.setText("0"+obList.size());
    }
    
    
    Pattern sclCodeP=Pattern.compile("^(S-)[0-9]{4}$");
    Pattern eMailP=Pattern.compile("^[A-Za-z0-9+_.-]+@(.{4,})$");
    Pattern contactNoP=Pattern.compile("^(0?[0-9]{2}-?)[0-9]{7}$");
    Pattern sclNameP=Pattern.compile("^(Mo/[A-Z])[a-zA-Z .]{5,}$");
    Pattern addressP=Pattern.compile("^[A-z ,/.0-9]{5,}$");

    LinkedList<Node> nodes =new LinkedList<>();

    private void storeNodes(){
        nodes.add(txtSclCode);
        nodes.add(txtContactNo);
        nodes.add(txtEmail);
        nodes.add(txtSclName);
        nodes.add(txtSclAddress);
    }


    public void addTxtFieldOnKeyReleased(KeyEvent keyEvent) {
        resetAddOrEditOptionNode();
        addOrEditNotificationPane.setVisible(false);
        new Validation().changeNodesEffect(nodes,true);
        if (keyEvent.getCode()== KeyCode.ENTER){
            for (int i = 0; i < nodes.size(); i++) {
                if (keyEvent.getSource().equals(nodes.get(i))){
                    if (i+1<nodes.size()) {
                        nodes.get(i + 1).requestFocus();
                    }else {
                        try {
                            addOrEdit();
                            return;
                        } catch (SQLException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
    }


    //Close Panel Dragging Methods-------------------------------------------------------------------

    /*double x,y;*/
    public void closePaneOnMouseDragged(MouseEvent mouseEvent) {
       /* AnchorPane parent = (AnchorPane) ((AnchorPane) mouseEvent.getSource()).getParent();
        parent.setLayoutX(mouseEvent.getScreenX() - x);
        parent.setLayoutY(mouseEvent.getScreenY() - y);*/
    }

    public void closePaneOnMousePressed(MouseEvent mouseEvent) {
       /* x= mouseEvent.getX();
        y=mouseEvent.getY();*/
    }


    public void EditDetailsOnClicked(ActionEvent actionEvent) {
        lblTitleTxt.setText("EDIT SCHOOL DETAILS");
        btnAddOrEdit.setText("Save");
        if (!addOrEditPane.isVisible()){
            addOrEditPane.setVisible(true); addOrEditPane.setDisable(false);
            new ZoomIn(addOrEditPane).play();
            effectAddOrEdit("-fx-opacity:0.5",true);

           txtSclCode.setText(lblSclCode.getText());
           txtContactNo.setText(lblContactNo.getText());
           cmbZone.setValue(lblZone.getText());
           txtEmail.setText(lblEmail.getText());
           txtSclName.setText(lblSclName.getText());
           txtSclAddress.setText(lblAddress.getText());

           txtSclCode.setEditable(false);

        }else{
            addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
            effectAddOrEdit("-fx-opacity:1.0",false);
        }

    }

    public void RemoveOnClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Alert alert=new Alert(Alert.AlertType.
                WARNING,"Sure to delete ? ",ButtonType.NO,ButtonType.YES);

        if (alert.showAndWait().get()==ButtonType.YES) {
            if (Integer.parseInt(lblNumOfPlatoon.getText()) == 0 & Integer.parseInt(lblNumOfOfficer.getText()) == 0) {
                if (schoolController.removeSchoolFromDB(lblSclCode.getText())) {
                    visibleOfSclDetailPane(false);
                    ArrayList<School> allSchool = schoolController.getAllSchool();
                    tblSclSearchResult.getSelectionModel().clearSelection();
                    tblSclSearchResult.getItems().clear();
                    loadSchoolToTable(allSchool);
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted..").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try Again..").showAndWait();
                }


            } else {
                if (schoolController.removeSchool(lblSclCode.getText())) {
                    visibleOfSclDetailPane(false);
                    ArrayList<School> allSchool = schoolController.getAllSchool();
                    tblSclSearchResult.getSelectionModel().clearSelection();
                    tblSclSearchResult.getItems().clear();
                    loadSchoolToTable(allSchool);
                    new Alert(Alert.AlertType.INFORMATION, "Successfully Deleted..").showAndWait();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Try Again..").showAndWait();
                }
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

    private void resetAddOrEditOptionNode(){
        for (Node n:
                nodes) {
            n.getParent().setStyle("-fx-border-color: #F4D885");
            (((Pane) n.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #F4D885");
        }
        cmbZone.getParent().setStyle("-fx-border-color: #F4D885");
        (((Pane) cmbZone.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #F4D885");
    }
}
