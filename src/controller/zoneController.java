package controller;

import db.DbConnection;
import model.School;
import model.Zone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class zoneController {

    public static boolean addZone(Zone z) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("INSERT INTO Zone VALUES(?,?,?,?)");
        stm.setObject(1,z.getZoneCode());
        stm.setObject(2,z.getZoneName());
        stm.setObject(3,z.getTotalScl());
        stm.setObject(4,"15Ncc");

        return stm.executeUpdate() > 0;
    }
    //For zone Section without notAssigned
    public static LinkedList<Zone> getAllZones() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Zone");
        ResultSet rst = stm.executeQuery();
        LinkedList<Zone> zoneList=new LinkedList<>();
        while (rst.next()){
            if (!rst.getString(1).equals("notAssigned")) {
                zoneList.add( new Zone(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3)
                ));
            }

        }
        return zoneList;
    }

    /*public static LinkedList<Zone> getAllZoneDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Zone");
        ResultSet rst = stm.executeQuery();
        LinkedList<Zone> zoneList=new LinkedList<>();
        while (rst.next()){
                zoneList.add( new Zone(
                        rst.getString(1),
                        rst.getString(2),
                        rst.getInt(3)
                ));

        }
        return zoneList;
    }*/

    public static List<String> getAllZoneName() throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT zoneName FROM Zone");
        ResultSet rst = stm.executeQuery();
        List<String> zoneNameList=new ArrayList<>();
        while (rst.next()){
            zoneNameList.add(rst.getString(1));
        }
        return zoneNameList;
    }

    public static ArrayList<School> getAllSclOfZone(String zoneCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM School WHERE zoneCode='" + zoneCode + "'");
        ResultSet rst = stm.executeQuery();
        ArrayList<School> arrScl=new ArrayList<>();
        while (rst.next()) {
            arrScl.add(new School(
               rst.getString(1),
               rst.getString(2),
               rst.getString(3),
               rst.getString(4),
               rst.getString(5),
               rst.getString(6)
            ));
        }
        return arrScl;
    }

    public static boolean removeZone(String zoneCode)  {
        Connection con=null;
        try {
            con=DbConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            PreparedStatement stm = con.
                    prepareStatement("UPDATE School SET zoneCode='" + "notAssigned" + "' WHERE zoneCode='" + zoneCode + "'");
            if (stm.executeUpdate()>0){
                if (removeZoneFromDb(zoneCode)){
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
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean removeZoneFromDb(String zoneCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("DELETE FROM Zone WHERE zoneCode='" + zoneCode + "'");
        return stm.executeUpdate()>0;

    }

    public static boolean updateZone(Zone z) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("UPDATE Zone SET zoneName=? , totalNumOfScl=? WHERE zoneCode=?");
        stm.setObject(1,z.getZoneName());
        stm.setObject(2,z.getTotalScl());
        stm.setObject(3,z.getZoneCode());
        return stm.executeUpdate()>0;
    }


    public static String getZoneCodeForName(String zoneName) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Zone  WHERE zoneName='" + zoneName + "'");
        ResultSet rst = stm.executeQuery();
        String zCode="notAssigned";
        if (rst.next()) {
            zCode=rst.getString(1);
        }
        return zCode;
    }

    public static String getZoneNameForCode(String zCode) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT zoneName FROM Zone  WHERE zoneCode='" + zCode + "'");
        ResultSet rst = stm.executeQuery();
        String zName="notAssigned";
        if (rst.next()) {
            zName=rst.getString(1);
        }
        return zName;
    }


    public static String suggestZoneCode() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT zoneCode FROM Zone ORDER BY zoneCode DESC LIMIT 1").executeQuery();
        String finalCode="Z-001";
        if (rst.next()){
            String lastCode = rst.getString(1);
            if(lastCode.equals("notAssigned")){
                return finalCode;
            }

            String newCode = String.valueOf(Integer.parseInt(lastCode.split("-")[1]) + 1);
            int length = newCode.length();
            switch (length){
                case 1 :
                    finalCode="Z-00" +newCode;
                    break;
                case 2 :
                    finalCode="Z-0" +newCode;
                    break;
                case 3 :
                    finalCode="Z-" +newCode;
                    break;
            }
        }
        return finalCode;
    }
}
