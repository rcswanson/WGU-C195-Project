package Model;

import Main.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    private static int userId;
    private static String username;
    private static String password;

    public User() {
        userId = 0;
        username = null;
        password = null;
    }

    public User(int userId, String username, String password) {
        User.userId = userId;
        User.username = username;
        User.password = password;

    }

    //The getters of the User class
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    // the setters of the User class
    public void setUserId(int userId) {
        User.userId = userId;
    }

    public void setUsername(String username) {
        User.username = username;
    }

    public void setPassword(String password) {
        User.password = password;
    }

}