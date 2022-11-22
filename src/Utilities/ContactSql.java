package Utilities;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class ContactSql {

    // EXECUTES STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Contact> getContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
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

    // EXECUTES STATEMENT GETTING CONTACT ID
    public static Contact getContactId(Integer contact) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM contacts WHERE Contact_Name=?");
        pStmt.setInt(1, contact);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Contact newContact = new Contact(
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Contact_Email"));
                return newContact;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}
