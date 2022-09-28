package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class Contact {

    private int contactId;
    private String contactName;
    private String contactEmail;

    // CONSTRUCTOR
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    // GETTERS AND SETTERS
    public int getContactId() { return contactId; }

    public void setContactId(int contactId) { this.contactId = contactId; }

    public String getContactName() { return contactName; }

    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactEmail() { return contactEmail; }

    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }


    /**
     * OBSERVABLE LIST OF DATA ENTRIES IN CONTACTS SCHEMA
     * @return CONTACTS
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
