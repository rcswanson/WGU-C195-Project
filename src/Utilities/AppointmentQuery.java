package Utilities;

import Main.JDBC;
import Model.Appointment;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Main.JDBC.connection;

public class AppointmentQuery {

    static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    // OBSERVABLE LIST OF DATA ENTRIES IN APPOINTMENTS SCHEMA
    public static ObservableList<Appointment> getAppointments() {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Appointment newAppointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getString("Contact_ID"),
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    // EXECUTES SQL STATEMENT TO DELETE APPOINTMENT FROM DATABASE
    public static boolean cancelAppointment(int appointmentId) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "DELETE FROM appointments WHERE Customer_ID = " + appointmentId;
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
