package util;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class Validation {

    /*public static Object validateAddOrEdit(LinkedHashMap<TextField,
            Pattern> map, JFXButton btn, ArrayList<ComboBox> cmbList,
            Pane errorPane, Label errorLabel, LinkedHashMap<TextField, String> sqlMap, String text) {

        btn.setDisable(true);
        String errorTxt1="Wrong Pattern Entered.." ;
        String errorTxt2="Cannot be null.." ;
        String errTxt3="Entered Data is already Exist..";
        for (TextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                    errorPane.setVisible(true);
                    errorLabel.setText(errorTxt2);
                    textFieldKey.getParent().setStyle("-fx-border-color: #ff8880");
                    (((Pane) textFieldKey.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");

                if (!textFieldKey.getText().isEmpty()) {
                    textFieldKey.getParent().setStyle("-fx-border-color: #ff8880");
                    (((Pane) textFieldKey.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
                    errorLabel.setText(errorTxt1);
                }

                return textFieldKey;
            }else {
                if (text.equals("Add")){
                    for (TextField txtF : sqlMap.keySet()) {
                        String sql = sqlMap.get(txtF);
                        if (txtF.equals(textFieldKey)) {
                            try {
                                PreparedStatement stm = DbConnection.getInstance().getConnection().
                                        prepareStatement(sql);
                                stm.setObject(1, textFieldKey.getText());
                                if (stm.executeQuery().next()) {
                                    errorPane.setVisible(true);
                                    errorLabel.setText(errTxt3);
                                    textFieldKey.getParent().setStyle("-fx-border-color: #ff8880");
                                    (((Pane) textFieldKey.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
                                    return textFieldKey;
                                }
                            } catch (SQLException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        if (cmbList.size()==0){
            btn.setDisable(false);
            errorPane.setVisible(false);
            return true;
        }else if (cmbList.size()>0){
            for (ComboBox cmb:
                 cmbList) {
                if (!cmb.getSelectionModel().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }*/

    public boolean checkDuplicate(TextField txt, String sql) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        stm.setObject(1,txt.getText());
        if (stm.executeQuery().next()){
            txt.getParent().setStyle("-fx-border-color: #ff8880");
            (((Pane) txt.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
            return false;
        }else {
            return true;
        }
    }

    public boolean checkTxtField(TextField txt,Pattern pattern){
        if (pattern.matcher(txt.getText()).matches()){
            return true;
        }else {
            txt.getParent().setStyle("-fx-border-color: #ff8880");
            (((Pane) txt.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
            return false;
        }
    }

    public void changeNodesEffect(LinkedList<Node> nodes,boolean b){
        if (!b){
            for (Node n:
                 nodes) {
                n.getParent().setStyle("-fx-border-color: #ff8880");
                (((Pane) n.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
            }
        }else{ //set style default
            for (Node n:
                    nodes) {
                n.getParent().setStyle("-fx-border-color: #F4D885");
                (((Pane) n.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #F4D885");
            }
        }
    }

    public boolean checkCmbBox(ComboBox box){
        if (box.getSelectionModel().isEmpty()){
            box.getParent().setStyle("-fx-border-color: #ff8880");
            (((Pane) box.getParent()).getChildren().get(0)).setStyle("-fx-background-color: #ff8880");
            return false;
        }else {
            return true;
        }
    }
}
