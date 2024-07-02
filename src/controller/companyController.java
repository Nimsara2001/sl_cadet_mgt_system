package controller;

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import tm.addCompanyNcoTM;
import tm.allCompanyTM;
import tm.companyDetailNcoTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class companyController {


    public static boolean addCompany(String companyCode, ObservableList<addCompanyNcoTM> obList) throws SQLException, ClassNotFoundException {
        boolean s1;
        boolean s2=false;
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Company VALUES(?)");
        stm.setObject(1,companyCode);
        s1=stm.executeUpdate()>0;
        PreparedStatement stm2= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO CadetNCO VALUES(?,?,?,?)");
        for (addCompanyNcoTM tm:
             obList) {
            stm2.setObject(1,tm.getRegNo());
            stm2.setObject(2,tm.getRank());
            stm2.setObject(3,tm.getName());
            stm2.setObject(4,companyCode);
            s2=stm2.executeUpdate()>0;
        }

        return (s1 & s2);
    }

    public static ObservableList<allCompanyTM> getAllCompanies() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Company");
        ResultSet rst = stm.executeQuery();
        ObservableList<allCompanyTM> obList= FXCollections.observableArrayList();
        while (rst.next()){
            obList.add(new allCompanyTM(
               new CheckBox(),
               rst.getString(1)
            ));
        }
        return obList;
    }
    public static ObservableList<companyDetailNcoTM> getCompanyNcos(String comCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM CadetNCO WHERE companyCode='" + comCode + "'");
        ResultSet rst = stm.executeQuery();
        ObservableList<companyDetailNcoTM> obList =FXCollections.observableArrayList();
        while (rst.next()){
            obList.add(new companyDetailNcoTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            ));
        }
        return obList;
    }

    public static boolean updateCompany(String comCode,ObservableList<addCompanyNcoTM> obList) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("DELETE FROM CadetNCO WHERE companyCode='" + comCode + "'");
        boolean s1=stm.executeUpdate()>0;
        boolean s2=false;
        PreparedStatement stm2= DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO CadetNCO VALUES(?,?,?,?)");
        for (addCompanyNcoTM tm:
                obList) {
            stm2.setObject(1,tm.getRegNo());
            stm2.setObject(2,tm.getRank());
            stm2.setObject(3,tm.getName());
            stm2.setObject(4,comCode);
            s2=stm2.executeUpdate()>0;
        }
        return (s1 & s2);
    }

    public static boolean removeCompany(String comCode){
        Connection con=null;
        try {
            con=DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.prepareStatement("UPDATE Platoon SET companyCode='" + "notAssigned" + "' WHERE companyCode='" + comCode + "'");
            if (stm.executeUpdate()>0){
                if (removeCompanyFromDB(comCode)){
                    con.commit();
                    return true;
                }else {
                    con.rollback();
                    return false;
                }
            }else {
                con.rollback();
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;

    }

    public static boolean removeCompanyFromDB(String comCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("DELETE FROM Company WHERE companyCode='" + comCode + "'");
        return stm.executeUpdate()>0;
    }

}
