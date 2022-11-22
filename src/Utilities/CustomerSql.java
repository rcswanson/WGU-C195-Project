package Utilities;

import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Main.JDBC.connection;


public class CustomerSql {

    // EXECUTES SQL STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Customer> getCustomers() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM customers");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Customer newCustomer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getString("Country"));
                customers.add(newCustomer);
            }
            return customers;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    // EXECUTES STATEMENT GATHERING CUSTOMERS BY ID
    public static Customer getCustomerId(String customer) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM customers WHERE Customer_Name=?");
        pStmt.setString(1, customer);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();

            while (rs.next()) {
                Customer newCustomer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getString("Country"));
                return newCustomer;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    // EXECUTES INSERT STATEMENT TO ADD A CUSTOMER TO SCHEMA
    public static void insertCustomer(String customerName, String address, String postalCode, String phoneNumber, String division, String country) throws SQLException {
        Division newDivision = DivisionSql.getDivisionId(division);
        PreparedStatement pStmt = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Country) VALUES (?,?,?,?,?,?)");

        pStmt.setString(1, customerName);
        pStmt.setString(2, address);
        pStmt.setString(3, postalCode);
        pStmt.setString(4, phoneNumber);
        pStmt.setInt(5, newDivision.getDivisionId());
        pStmt.setString(6, country);

        pStmt.execute();
    }

    // EXECUTES STATEMENT TO UPDATE A CUSTOMER IN THE SCHEMA
    public static void updateCustomer(String customerName, String address, String postalCode, String phoneNumber, String division, String country, int customerId) throws SQLException {
        Division newDivision = DivisionSql.getDivisionId(division);
        PreparedStatement pStmt = connection.prepareStatement("UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Country=? WHERE Customer_ID=?");

        pStmt.setString(1, customerName);
        pStmt.setString(2, address);
        pStmt.setString(3, postalCode);
        pStmt.setString(4, phoneNumber);
        pStmt.setInt(5, newDivision.getDivisionId());
        pStmt.setString(6, country);
        pStmt.setInt(7, customerId);

        pStmt.execute();
    }

    // EXECUTES SQL STATEMENT TO DELETE CUSTOMER FROM SCHEMA
    public static int deleteCustomer(Customer customer) throws SQLException {

        PreparedStatement pStmt = connection.prepareStatement("DELETE FROM customers WHERE Customer_ID=?");

        pStmt.setInt(1, customer.getCustomerId());

        pStmt.executeUpdate();

        return 0;

    }
}
