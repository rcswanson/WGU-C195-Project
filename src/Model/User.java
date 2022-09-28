package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class User {

    private int userId;
    private String username;
    private String password;

    public User() {
        userId = 0;
        username = null;
        password = null;
    }

    // CONSTRUCTOR
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;

    }

    // GETTERS AND SETTERS
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * OBSERVABLE LIST OF DATA ENTRIES IN THE USERS SCHEMA
     * @return USERS
     */
    public static ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
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

    /**
     * CHECKS FOR A MATCH BETWEEN INPUT AND USER DATA IN DATABASE
     * @param username User_Name
     * @param password Password
     * @return User_ID
     */
    public static int checkCreds(String username, String password) {

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