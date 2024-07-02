package controller;

import db.DbConnection;
import tm.companyDetailNcoTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class cadetController {

    public static ArrayList<String> getCadetNcoRegCode() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT cdtRegNo FROM Cadet WHERE ((cdtRank=?) OR (cdtRank=?) OR (cdtRank=?) OR (cdtRank=?)) ");
        stm.setObject(1,"CQ");
        stm.setObject(2,"CSM");
        stm.setObject(3,"RQ");
        stm.setObject(4,"RSM");
        ResultSet rst = stm.executeQuery();
        ArrayList<String> list=new ArrayList<>();
        while (rst.next()) {
            list.add(rst.getString(1));
        }
        return list;
    }

    public static companyDetailNcoTM  getNcoDetail(String regNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT cdtRegNo,cdtRank,nameWithInit FROM Cadet WHERE cdtRegNo='" + regNo + "' ");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            return new companyDetailNcoTM(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;
    }
}
