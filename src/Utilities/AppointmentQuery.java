package Utilities;

import Main.JDBC;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import static Main.JDBC.connection;
import static Main.JDBC.getConnection;

public class AppointmentQuery {

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

    public static ObservableList<Appointment> getAppointmentsByMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        LocalDateTime todaysDate = LocalDateTime.now();
        LocalDateTime lastWeek = todaysDate.minusDays(7);

        String query = "SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?";

        DBQuery.setPreparedStatement(getConnection(), query);
        PreparedStatement pStmt = DBQuery.getPreparedStatement();

        pStmt.setDate(1, java.sql.Date.valueOf(todaysDate.toLocalDate()));
        pStmt.setDate(2, java.sql.Date.valueOf(lastWeek.toLocalDate()));

        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();

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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    // EXECUTES SQL STATEMENT TO DELETE APPOINTMENT FROM DATABASE
    public static boolean cancelAppointment(int appointmentId) {
        try {
            Statement statement = JDBC.getConnection().createStatement();
            String query = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentId;
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
