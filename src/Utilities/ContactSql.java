package Utilities;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class ContactSql {

    public static ObservableList<Contact> contacts = FXCollections.observableArrayList();

    // EXECUTES SQL STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Contact> getContacts() {
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM contacts");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Contact newContact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Email"));
                contacts.add(newContact);
            }
            return contacts;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}
