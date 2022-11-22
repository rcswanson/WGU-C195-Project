package Utilities;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class UserSql {

    public static ObservableList<User> users = FXCollections.observableArrayList();

    // EXECUTES SQL STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<User> getUsers() {
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                User newUser = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"));
                users.add(newUser);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // EXECUTES STATEMENT TO GET USER BY ID
    public static User getUserId(String user) throws SQLException {
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM users WHERE User_Name=?");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                User newUser = new User(
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"),
                        rs.getString("Password"));
                return newUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // CHECKS FOR A MATCH BETWEEN INPUT AND USER DATA IN DATABASE
    public static int checkLogin(String username, String password) {

        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        return rs.getInt("User_ID");
                    }
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }

}
