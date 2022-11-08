package Utilities;

import Main.JDBC;
import Model.Customer;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Main.JDBC.connection;


public class CustomerSql {

    // OBSERVABLE LIST OF DATA ENTRIES IN CUSTOMERS SCHEMA
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    // EXECUTES SQL STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Customer> getCustomers() {
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

    public static void insertCustomer(String name, String address, String postalCode, String phone, String division, String country) throws SQLException {
        Division newDivision = DivisionSql.getDivisionId(division);
        PreparedStatement pStmt = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Country) VALUES (?,?,?,?,?,?");

        pStmt.setString(1, name);
        pStmt.setString(2, address);
        pStmt.setString(3, postalCode);
        pStmt.setString(4, phone);
        pStmt.setInt(5, newDivision.getDivisionId());
        pStmt.setString(6, country);

        pStmt.execute();
    }

    public static void updateCustomer(String name, String address, String postalCode, String phone, String division, String country) throws SQLException {
        Division newDivision = DivisionSql.getDivisionId(division);
        PreparedStatement pStmt = connection.prepareStatement("UPDATE customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Country) VALUES (?,?,?,?,?,?");

        pStmt.setString(1, name);
        pStmt.setString(2, address);
        pStmt.setString(3, postalCode);
        pStmt.setString(4, phone);
        pStmt.setInt(5, newDivision.getDivisionId());
        pStmt.setString(6, country);

        pStmt.execute();
    }

    // EXECUTES SQL STATEMENT TO DELETE CUSTOMER FROM SCHEME
    public static int deleteCustomer(Customer customer) throws SQLException {

        PreparedStatement pStmt = connection.prepareStatement("DELETE FROM customers WHERE Customer_ID=?");

        pStmt.setInt(1, customer.getCustomerId());

        pStmt.executeUpdate();

        return 0;

    }
}
