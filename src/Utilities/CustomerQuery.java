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

    public static ObservableList<Customer> customers = FXCollections.observableArrayList();

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
                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getInt("Division_ID"),
                        rs.getInt("Country_ID"));
                customers.add(newCustomer);
            }
            return customers;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

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
