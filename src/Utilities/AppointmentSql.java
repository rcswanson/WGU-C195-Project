package Utilities;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;

import static Main.JDBC.connection;


public class AppointmentSql {

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
                        rs.getInt("Contact_ID"),
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

    // EXECUTES INSERT STATEMENT TO ADD AN APPOINTMENT TO THE SCHEMA
    public static void insertAppointment(String title, String description,
                                         String location, int contactId,
                                         String type, LocalDateTime start,
                                         LocalDateTime end,
                                         int customerId,
                                         int userId) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, Contact_ID, User_ID) VALUES (?,?,?,?,?,?,?,?,?)");

        pStmt.setString(1, title);
        pStmt.setString(2, description);
        pStmt.setString(3, location);
        pStmt.setString(4, type);
        pStmt.setTimestamp(5, Timestamp.valueOf(start));
        pStmt.setTimestamp(6, Timestamp.valueOf(end));
        pStmt.setInt(7, customerId);
        pStmt.setInt(8, contactId);
        pStmt.setInt(9, userId);

        pStmt.execute();
    }

    // EXECUTES UPDATE STATEMENT TO UPDATE AN APPOINTMENT IN THE SCHEMA
    public static void updateAppointment(String title, String description,
                                         String location, int contactId,
                                         String type, LocalDateTime start,
                                         LocalDateTime end,
                                         int customerId,
                                         int userId, int appointmentId) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, Contact_ID=?, User_ID=? WHERE Appointment_ID=?");

        pStmt.setString(1, title);
        pStmt.setString(2, description);
        pStmt.setString(3, location);
        pStmt.setString(4, type);
        pStmt.setTimestamp(5, Timestamp.valueOf(start));
        pStmt.setTimestamp(6, Timestamp.valueOf(end));
        pStmt.setInt(7, customerId);
        pStmt.setInt(8, contactId);
        pStmt.setInt(9, userId);
        pStmt.setInt(10, appointmentId);

        pStmt.execute();
    }

    // EXECUTES SQL STATEMENT TO DELETE APPOINTMENT FROM DATABASE
    public static void deleteAppointment(Appointment appointment) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");
        pStmt.setInt(1, appointment.getAppointmentId());
        pStmt.executeUpdate();

    }

    // EXECUTES STATEMEMT GATHERING APPOINTMENTS IN A WEEK
    public static ObservableList<Appointment> getApptByWeek() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now().plusDays(3);
        LocalDateTime lastWeek = today.minusDays(4);
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?");
        pStmt.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
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
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                appointments.add(newAppointment);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return appointments;
    }

    // EXECUTES STATEMENT GATHERING APPOINTMENTS BY MONTH
    public static ObservableList<Appointment> getApptByMonth() throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        LocalDateTime today = LocalDateTime.now().plusDays(15);
        LocalDateTime lastMonth = today.minusDays(15);
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Start < ? AND Start > ?;");
        pStmt.setDate(1, java.sql.Date.valueOf(today.toLocalDate()));
        pStmt.setDate(2, java.sql.Date.valueOf(lastMonth.toLocalDate()));
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Appointment newAppointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                appointments.add(newAppointment);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return appointments;
    }

    // EXECUTES STATEMENT GATHERING APPOINTMENTS BY CUSTOMER ID
    public static ObservableList<Appointment> getApptByCustomerId(int customer) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Customer_ID=?");
        pStmt.setInt(1, customer);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Appointment newAppointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
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

    // EXECUTES STATEMENT GATHERING APPOINTMENTS BY CONTACT ID
    public static ObservableList<Appointment> getApptByContactId(int contact) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Contact_ID=?");
        pStmt.setInt(1, contact);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Appointment newAppointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
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

    // EXECUTES STATEMENT GATHERING APPOINTMENTS BY APPOINTMENT ID
    public static Appointment getApptByApptId(int apptId) throws SQLException {
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM appointments AS a INNER JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE Appointment_ID=?");
        pStmt.setInt(1, apptId);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Appointment newAppointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"));
                appointments.add(newAppointment);
                return newAppointment;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

}
