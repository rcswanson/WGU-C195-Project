package Utilities;

import Main.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

import static Main.JDBC.connection;


public class AppointmentSql {

    // OBSERVABLE LIST OF DATA ENTRIES IN APPOINTMENTS SCHEMA
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getAppointments() {
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
    public static int cancelAppointment(Appointment appointment) throws SQLException {

        PreparedStatement pStmt = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");

        pStmt.setInt(1, appointment.getAppointmentId());

        pStmt.executeUpdate();

        return 0;
    }

    public static ObservableList<Appointment> getApptByWeek() throws SQLException {

        return null;
    }
}
