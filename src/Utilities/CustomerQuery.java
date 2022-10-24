package Utilities;

import Main.JDBC;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Main.JDBC.connection;

public class CustomerQuery {

    // OBSERVABLE LIST OF DATA ENTRIES IN CUSTOMERS SCHEMA
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

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

    public static void addCustomer(String name, String address, String zipCode, String phone, String country, String division) throws SQLException {
        Statement statement = JDBC.getConnection().createStatement();
        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Country, Division) " +
                    "VALUES (?,?,?,?,?,?)";

        DBQuery.setPreparedStatement(JDBC.getConnection(), query);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, zipCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setString(5, country);
        preparedStatement.setString(6, division);

        try {

        preparedStatement.execute();


        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
    }

    // EXECUTES SQL STATEMENT TO DELETE CUSTOMER FROM DATABASE
    public static boolean deleteCustomer(int customerId) throws SQLException {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "DELETE FROM customers WHERE Customer_ID = " + customerId;
            int update = statement.executeUpdate(query);
            if(update == 1) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return false;
    }
}
