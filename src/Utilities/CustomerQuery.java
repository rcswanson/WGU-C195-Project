package Utilities;

import Main.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

import static Main.JDBC.connection;

public class CustomerQuery {

    // OBSERVABLE LIST OF DATA ENTRIES IN CUSTOMERS SCHEMA
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**
     * Gets data from result set and sets SQL data to customer object
     * @return customer
     * @throws SQLException
     */
    private static Customer getResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer();

        customer.setCustomerId(rs.getInt("Customer_ID"));
        customer.setCustomerName(rs.getString("Customer_Name"));
        customer.setAddress(rs.getString("Address"));
        customer.setPostalCode(rs.getString("Postal_Code"));
        customer.setPhoneNumber(rs.getString("Phone"));
        customer.setDivisionId(rs.getInt("Division_ID"));
        customer.setCountry(rs.getString("Country"));
        return customer;
    }


    // METHOD ADDS ALL ENTRIES IN DATABASE TO OBSERVABLE LIST
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


    public static int insertNewCustomer(Customer customer) throws SQLException {

        PreparedStatement pStmt = connection.prepareStatement("INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID, Country) VALUES (?,?,?,?,?,?)");

        pStmt.setString(1, customer.getCustomerName());
        pStmt.setString(2, customer.getAddress());
        pStmt.setString(3, customer.getPostalCode());
        pStmt.setString(4, customer.getPhoneNumber());
        pStmt.setInt(5, customer.getDivisionId());
        pStmt.setString(6, customer.getCountry());

        pStmt.executeUpdate();

        return 0;
    }

    public static int editCustomer(Customer customer) throws SQLException {

        PreparedStatement pStmt = connection.prepareStatement("UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=?, Country=? WHERE Customer_ID=?");

        pStmt.setInt(7,customer.getCustomerId());
        pStmt.setString(1, customer.getCustomerName());
        pStmt.setString(2, customer.getAddress());
        pStmt.setString(3, customer.getPostalCode());
        pStmt.setString(4, customer.getPhoneNumber());
        pStmt.setInt(5, customer.getDivisionId());
        pStmt.setString(6, customer.getCountry());

        pStmt.executeUpdate();

        return 0;
    }

    // EXECUTES SQL STATEMENT TO DELETE CUSTOMER FROM DATABASE
    public static int deleteCustomer(Customer customer) throws SQLException {

        PreparedStatement pStmt1 = connection.prepareStatement("DELETE FROM appointments WHERE Customer_ID = ?");
        PreparedStatement pStmt2 = connection.prepareStatement("DELETE FROM customers WHERE Customer_ID = ?");

        pStmt1.setInt(1, customer.getCustomerId());
        pStmt2.setInt(1, customer.getCustomerId());

        int result1 = pStmt1.executeUpdate();
        int result2 = pStmt2.executeUpdate();

        return result2;

    }
}
