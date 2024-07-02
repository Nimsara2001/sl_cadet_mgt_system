package viewController;

import com.jfoenix.controls.JFXButton;
import controller.zoneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.School;
import model.Zone;
import tm.ZoneSchoolTM;
import util.Validation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class zonePanelFormController {
    public AnchorPane zone1Pane;
    public AnchorPane zone2Pane;
    public AnchorPane zone3Pane;

    public Label zone1Code;
    public Label zone1Name;
    public Label zone1TtlScl;
    public Label zone1CdtScl;
    public Label zone1NonCdtScl;
    public Label zone1TtlCadet;

    public Label zone2Code;
    public Label zone2Name;
    public Label zone2TtlScl;
    public Label zone2CdtScl;
    public Label zone2NonCdtScl;
    public Label zone2TtlCadet;

    public Label zone3Code;
    public Label zone3Name;
    public Label zone3TtlScl;
    public Label zone3CdtScl;
    public Label zone3NonCdtScl;
    public Label zone3TtlCadet;

    public TableView<ZoneSchoolTM> tblZoneSchool;
    public Label selectedZoneName;
    public ScrollPane ScrollPane;
    public AnchorPane addOrEditPane;
    public TextField txtZoneCode;
    public TextField txtZoneName;
    public TextField txtTotalScl;
    public JFXButton btnAddOrEdit;
    public Label lblTitleTxt;
    public AnchorPane workingPane;
    public AnchorPane zoneSchoolPane;
    public Pane addOrEditNotificationPane;
    public Label lblAddOrEditNotification;
    public PieChart pieChart1;
    public PieChart pieChart2;
    public PieChart pieChart3;

    LinkedList<AnchorPane> zonePaneList=new LinkedList<>();
    LinkedList<Label> zonePaneLabelList=new LinkedList<>();

    public void initialize(){
        addOrEditPane.setVisible(false);
        addOrEditPane.setDisable(true);
        clearZonePaneData();
        zoneSchoolPane.setVisible(false);
        storeNodes();
        addOrEditNotificationPane.setVisible(false);


        try {
            LinkedList<Zone> allZones = zoneController.getAllZones();
            setZonePaneDetails(0,allZones,zone1Pane,zone1Code,zone1Name,zone1TtlScl,zone1CdtScl,zone1NonCdtScl,zone1TtlCadet);
            setZonePaneDetails(1,allZones,zone2Pane,zone2Code,zone2Name,zone2TtlScl,zone2CdtScl,zone2NonCdtScl,zone2TtlCadet);
            setZonePaneDetails(2,allZones,zone3Pane,zone3Code,zone3Name,zone3TtlScl,zone3CdtScl,zone3NonCdtScl,zone3TtlCadet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        tblZoneSchool.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("sclCode"));
        tblZoneSchool.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("sclName"));
        tblZoneSchool.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("sclAddress"));
        tblZoneSchool.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("numOfPlatoon"));

        setPieChart(pieChart1,zone1CdtScl,zone1NonCdtScl);
        setPieChart(pieChart2,zone2CdtScl,zone2NonCdtScl);
        setPieChart(pieChart3,zone3CdtScl,zone3NonCdtScl);
    }

    private void setZonePaneDetails(int index,LinkedList<Zone> zoneList,AnchorPane pane, Label zCode,Label zName,Label ttlScl,Label ttlCdtScl,Label ttlNonCdtScl,Label ttlCdt) throws SQLException, ClassNotFoundException {
        if (zoneList.size()>=(index+1)){
            ObservableList<Node> children = pane.getChildren();
            for (Node child : children) {
                child.setVisible(true);
            }
            Zone zone = zoneList.get(index);
            zCode.setText(zone.getZoneCode());
            zName.setText(zone.getZoneName());
            ttlScl.setText(String.valueOf(zone.getTotalScl()));

            int size = zoneController.getAllSclOfZone(zone.getZoneCode()).size();
            ttlCdtScl.setText(String.valueOf(size));
            ttlNonCdtScl.setText(String.valueOf(zone.getTotalScl()-size));
        }

    }


    public void btnAddZoneOnClicked(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        if (!addOrEditPane.isVisible()){
            addOrEditPane.setVisible(true); addOrEditPane.setDisable(false);
            effectAddOrEdit("-fx-opacity:0.5",true);
            clearTxtField();
            btnAddOrEdit.setText("Add");
            lblTitleTxt.setText("ADD ZONE");
            addOrEditNotificationPane.setVisible(false);
            txtZoneCode.setText(zoneController.suggestZoneCode());
            txtZoneName.requestFocus();

        }else{
            addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
            effectAddOrEdit("-fx-opacity:0.1",false);

        }

    }

    public void zoneRemoveOnClicked(ActionEvent mouseEvent) {
        String zoneCode = ((Label) ((AnchorPane) ((MenuItem) mouseEvent.getSource()).getParentPopup().getOwnerNode().getParent()).getChildren().get(1)).getText();
        int ttlCdtScl =Integer.parseInt( ((Label) ((AnchorPane) ((MenuItem) mouseEvent.getSource()).getParentPopup().getOwnerNode().getParent()).getChildren().get(6)).getText() );

        Alert alert=new Alert(Alert.AlertType.
                WARNING,"Sure to delete ? ",ButtonType.NO,ButtonType.YES);
        if (alert.showAndWait().get()==ButtonType.YES){
            if (ttlCdtScl!=0){
                if (zoneController.removeZone(zoneCode)) {
                    for (Label label: zonePaneLabelList) {
                        if (label.getText().equals(zoneCode)) {
                            label.setText("Z-000");
                            ObservableList<Node> children = ((AnchorPane) label.getParent()).getChildren();
                            for (Node child : children) {
                                child.setVisible(false);
                            }
                            break;
                        }
                    }
                    new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted..").showAndWait();
                }else {
                    new Alert(Alert.AlertType.ERROR,"Try Again..").showAndWait();
                }
            }else {
                try {
                    if (zoneController.removeZoneFromDb(zoneCode)) {
                        for (Label label: zonePaneLabelList) {
                            if (label.getText().equals(zoneCode)) {
                                label.setText("Z-000");
                                ObservableList<Node> children = ((AnchorPane) label.getParent()).getChildren();
                                for (Node child : children) {
                                    child.setVisible(false);
                                }
                                break;
                            }
                        }
                        new Alert(Alert.AlertType.INFORMATION,"Successfully Deleted..").show();
                    }else {
                        new Alert(Alert.AlertType.ERROR,"Try Again..").show();
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void zoneEditOnClicked(ActionEvent mouseEvent) {
        txtZoneCode.setEditable(false);
        clearTxtField();
        String zoneCode = ((Label) ((AnchorPane) ((MenuItem) mouseEvent.getSource()).getParentPopup().getOwnerNode().getParent()).getChildren().get(1)).getText();
        String zoneName = ((Label) ((AnchorPane) ((MenuItem) mouseEvent.getSource()).getParentPopup().getOwnerNode().getParent()).getChildren().get(2)).getText();
        String totalScl = ((Label) ((AnchorPane) ((MenuItem) mouseEvent.getSource()).getParentPopup().getOwnerNode().getParent()).getChildren().get(4)).getText();
        btnAddOrEdit.setText("Save");
        lblTitleTxt.setText("EDIT ZONE DETAILS");
        if (!addOrEditPane.isVisible()){
            addOrEditPane.setVisible(true); addOrEditPane.setDisable(false);
            effectAddOrEdit("-fx-opacity:0.5",true);

            txtZoneCode.setText(zoneCode);
            txtZoneName.setText(zoneName);
            txtTotalScl.setText(totalScl);
        }else{
            addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
            effectAddOrEdit("-fx-opacity:1.0",false);
        }
    }

    public void zoneAllSclDetailOnClicked(MouseEvent mouseEvent) {
        Label zoneCode = (Label) ((AnchorPane) ((JFXButton) mouseEvent.getSource()).getParent()).getChildren().get(1);
        Label zoneName = (Label) ((AnchorPane) ((JFXButton) mouseEvent.getSource()).getParent()).getChildren().get(2);
        int ttlCdtScl = Integer.parseInt(((Label) ((AnchorPane) ((JFXButton) mouseEvent.getSource()).getParent()).getChildren().get(6)).getText());
        if (ttlCdtScl!=0){
            try {
                ArrayList<School> allSclOfZone = zoneController.getAllSclOfZone(zoneCode.getText());
                tblZoneSchool.getItems().clear();
                loadSchoolToTable(allSclOfZone);
                selectedZoneName.setText(zoneName.getText());
                zoneSchoolPane.setVisible(true);
                ScrollPane.setVvalue(1.0);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR,"No Any Cadet School Assigned..!").show();
        }

    }

    ObservableList<ZoneSchoolTM> obList = FXCollections.observableArrayList();
    private void loadSchoolToTable(ArrayList<School> allSclOfZone) {
        allSclOfZone.forEach(e->{
            obList.add(new ZoneSchoolTM(
                    e.getSclCode(),
                    e.getName(),
                    e.getAddress(),
                    25
            ));
        });
        tblZoneSchool.setItems(obList);
        tblZoneSchool.refresh();
    }


    private void clearZonePaneData(){
        zonePaneList.add(zone1Pane);
        zonePaneList.add(zone2Pane);
        zonePaneList.add(zone3Pane);

        zonePaneLabelList.add(zone1Code);
        zonePaneLabelList.add(zone2Code);
        zonePaneLabelList.add(zone3Code);

        for (Label label: zonePaneLabelList) {
            if (label.getText().equals("Z-000")) {
                for (AnchorPane pane: zonePaneList) {
                    ObservableList<Node> children = pane.getChildren();
                    for (Node child : children) {
                        child.setVisible(false);
                    }
                }
            }
        }

    }


    public void btnAddOrEditOnClicked(MouseEvent mouseEvent) {
        try {
            addOrEdit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void addOrEdit() throws SQLException, ClassNotFoundException {
        if (btnAddOrEdit.getText().equals("Add") & lblTitleTxt.getText().equals("ADD ZONE")) {
            if ((new Validation().checkTxtField(txtZoneCode,zCodePattern) &&
                new Validation().checkDuplicate(txtZoneCode,"SELECT zoneCode FROM Zone WHERE zoneCode=?")) &
                (new Validation().checkTxtField(txtZoneName,zNamePattern) &&
                new Validation().checkDuplicate(txtZoneName,"SELECT zoneName FROM Zone WHERE zoneName=?")) &
                new Validation().checkTxtField(txtTotalScl,ttlSclPattern)
            ){
                Zone zone=new Zone(
                        txtZoneCode.getText(),
                        txtZoneName.getText(),
                        Integer.parseInt(txtTotalScl.getText())
                );
                try {
                    if (controller.zoneController.addZone(zone)) {
                        clearTxtField();
                        txtZoneCode.setText(zoneController.suggestZoneCode());
                        txtZoneName.requestFocus();
                        setNotificationPaneStyle(true);
                    }else {
                        setNotificationPaneStyle(false);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }else {
                setNotificationPaneStyle(false);
            }

        }else if (btnAddOrEdit.getText().equals("Save") & lblTitleTxt.getText().equals("EDIT ZONE DETAILS")){
            if (
                    new Validation().checkTxtField(txtZoneName,zNamePattern) &
                    new Validation().checkTxtField(txtTotalScl,ttlSclPattern)
            ){

                Zone zone=new Zone(
                        txtZoneCode.getText(),
                        txtZoneName.getText(),
                        Integer.parseInt(txtTotalScl.getText())
                );
                try {
                    if (controller.zoneController.updateZone(zone)) {
                        clearTxtField();
                        txtZoneName.requestFocus();
                        setNotificationPaneStyle(true);
                    }else {
                        setNotificationPaneStyle(false);
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                setNotificationPaneStyle(false);
            }
        }
        clearZonePaneData();

        try {
            LinkedList<Zone> allZones = zoneController.getAllZones();
            setZonePaneDetails(0,allZones,zone1Pane,zone1Code,zone1Name,zone1TtlScl,zone1CdtScl,zone1NonCdtScl,zone1TtlCadet);
            setZonePaneDetails(1,allZones,zone2Pane,zone2Code,zone2Name,zone2TtlScl,zone2CdtScl,zone2NonCdtScl,zone2TtlCadet);
            setZonePaneDetails(2,allZones,zone3Pane,zone3Code,zone3Name,zone3TtlScl,zone3CdtScl,zone3NonCdtScl,zone3TtlCadet);
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
    private void clearTxtField(){
        txtZoneCode.clear();
        txtZoneName.clear();
        txtTotalScl.clear();
    }
    public void closeOnClicked(MouseEvent mouseEvent) {
        addOrEditPane.setVisible(false); addOrEditPane.setDisable(true);
        effectAddOrEdit("-fx-opacity:1.0",false);
        txtZoneCode.setEditable(true);
        setPieChart(pieChart1,zone1CdtScl,zone1NonCdtScl);
        setPieChart(pieChart2,zone2CdtScl,zone2NonCdtScl);
        setPieChart(pieChart3,zone3CdtScl,zone3NonCdtScl);
        resetAddOrEditOptionNode();
        addOrEditNotificationPane.setVisible(false);
    }

    public void workingPaneOnClicked(MouseEvent mouseEvent) {
        if (!mouseEvent.getTarget().equals(zoneSchoolPane)) {
            zoneSchoolPane.setVisible(false);
        }
    }


    Pattern zCodePattern=Pattern.compile("^(Z-)[0-9]{3}$");
    Pattern zNamePattern=Pattern.compile("^([A-Z])[a-z]{2,}$");
    Pattern ttlSclPattern=Pattern.compile("^[0-9]+$");



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

    private void resetAddOrEditOptionNode(){
        for (Node n:
                nodes) {
            n.getParent().setStyle("-fx-border-color: #F4D885");
            (((Pane) n.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #F4D885");
        }
    }

    LinkedList<Node> nodes =new LinkedList<>();

    private void storeNodes(){
        nodes.add(txtZoneCode);
        nodes.add(txtZoneName);
        nodes.add(txtTotalScl);
    }

    private void setNotificationPaneStyle(boolean b){
        addOrEditNotificationPane.setVisible(true);
        if (b){
            addOrEditNotificationPane.setStyle("-fx-background-color: #66cc66");
            lblAddOrEditNotification.setText("Successfully Saved..");
        }else {
            addOrEditNotificationPane.setStyle("-fx-background-color: #ff8880");
            lblAddOrEditNotification.setText("An error has occurred. Please try again..");
        }
    }

    private void setPieChart(PieChart p,Label v1,Label v2){
        ObservableList<PieChart.Data> schoolPieChart = FXCollections.observableArrayList(
                new PieChart.Data("Cadet School", Double.parseDouble(v1.getText())),
                new PieChart.Data("Non Cadet School",Double.parseDouble(v2.getText()))
        );
        /*Double.parseDouble(v1.getText())
        *  Double.parseDouble(v2.getText())*/
        p.setData(schoolPieChart);
    }
}
