package controller;

import db.DbConnection;
import model.School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class schoolController {


   public static boolean addSchool(School s) throws SQLException, ClassNotFoundException {
       PreparedStatement stm = DbConnection.getInstance().getConnection().
               prepareStatement("INSERT INTO School VALUES(?,?,?,?,?,?)");
       stm.setObject(1,s.getSclCode());
       stm.setObject(2,s.getName());
       stm.setObject(3,s.getAddress());
       stm.setObject(4,s.getContactNo());
       stm.setObject(5,s.geteMail());
       stm.setObject(6,s.getZoneCode());
       return stm.executeUpdate()>0;

   }


   public static boolean updateSchool(School s) throws SQLException, ClassNotFoundException {
       PreparedStatement stm = DbConnection.getInstance().getConnection().
               prepareStatement("UPDATE School SET name=? , address=? , contactNo=? ,eMail=? , zoneCode=? WHERE sclCode=?");
       stm.setObject(1,s.getName());
       stm.setObject(2,s.getAddress());
       stm.setObject(3,s.getContactNo());
       stm.setObject(4,s.geteMail());
       stm.setObject(5,s.getZoneCode());
       stm.setObject(6,s.getSclCode());
       return stm.executeUpdate()>0 ;
   }

   public static ArrayList<School> getAllSchool() throws SQLException, ClassNotFoundException {
       PreparedStatement stm = DbConnection.getInstance().
               getConnection().prepareStatement("SELECT * FROM School");
       ArrayList<School> sclArr =new ArrayList<>();
       ResultSet rst = stm.executeQuery();
       while (rst.next()){
           if (!rst.getString(1).equals("notAssigned")){
               sclArr.add(new School(
                       rst.getString(1),
                       rst.getString(2),
                       rst.getString(3),
                       rst.getString(4),
                       rst.getString(5),
                       rst.getString(6)
               ));
           }
       }
       return sclArr;
   }

    public static School getMatchedSchool(String sclCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM School WHERE sclCode='"+sclCode+"'");
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            if (!rst.getString(1).equals("notAssigned")){
                return new School(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6)
                );
            }
        }
        return null;
    }

   public static String suggestSclCode() throws SQLException, ClassNotFoundException {
       ResultSet rst = DbConnection.getInstance().getConnection().
               prepareStatement("SELECT sclCode FROM School ORDER BY sclCode DESC LIMIT 1").executeQuery();
       String finalCode="S-0001";
       if (rst.next()){
           String lastCode = rst.getString(1);
           if (lastCode.equals("notAssigned")){
               return finalCode;
           }
           String newCode = String.valueOf(Integer.parseInt(lastCode.split("-")[1]) + 1);
           int length = newCode.length();
           switch (length){
               case 1 :
                   finalCode="S-000" +newCode;
                   break;
               case 2 :
                   finalCode="S-00" +newCode;
                   break;
               case 3 :
                   finalCode="S-0" +newCode;
                   break;
               case 4 :
                   finalCode="S-"+newCode;
                   break;
           }
       }
       return finalCode;
   }


   public static boolean removeSchool(String sclCode){
       Connection con=null;
       try {
           con=DbConnection.getInstance().getConnection();
           con.setAutoCommit(false);
           PreparedStatement stmP = con.
                   prepareStatement("UPDATE Platoon SET sclCode='" + "notAssigned" + "' WHERE sclCode='" + sclCode + "'");
           PreparedStatement stmO = con.
                   prepareStatement("UPDATE Officer SET sclCode='" + "notAssigned" + "' WHERE sclCode='" + sclCode + "'");
           if (stmP.executeUpdate()>0 | stmO.executeUpdate()>0){
               if (removeSchoolFromDB(sclCode)){
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

    public static boolean removeSchoolFromDB(String sclCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("DELETE FROM School WHERE sclCode='" + sclCode + "'");
        return stm.executeUpdate()>0;
    }
}
