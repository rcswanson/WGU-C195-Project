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

}