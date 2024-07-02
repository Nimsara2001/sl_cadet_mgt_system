package controller;

import db.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class appUserController {
    public static boolean matchUserPassword(String password) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().
                prepareStatement("SELECT password FROM AppUser WHERE password='" + password + "'");
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            if (rst.getString(1).equals(password)) {
                System.out.println("true");
                return true;
            }
        }
        return false;
    }
}
