package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static Main.JDBC.connection;

public class Appointment {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalDateTime startTime;
    private LocalDate endDate;
    private LocalDateTime endTime;
    private int customerId;
    private int userId;
    private int contactId;

    public Appointment(int appointmentId, String title, String description,
                       String location, String type, LocalDate startDate,
                       LocalDateTime startTime, LocalDate endDate, LocalDateTime endTime,
                       int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactId = contactId;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
    }

    public int getAppointmentId() { return appointmentId; }

    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public int getContactId() { return contactId; }

    public void setContactId(int contactId) { this.contactId = contactId; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public LocalDate getStartDate() { return startDate; }

    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDateTime getStartTime() { return startTime; }

    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDate getEndDate() { return endDate; }

    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDateTime getEndTime() { return endTime; }

    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public int getCustomerId() { return customerId; }

    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

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
                        rs.getString("Type"),
                        rs.getDate("Start").toLocalDate(),
                        rs.getTimestamp("Start").toLocalDateTime(),
                        rs.getDate("End").toLocalDate(),
                        rs.getTimestamp("End").toLocalDateTime(),
                        rs.getInt("Customer_ID"),
                        rs.getInt("User_ID"),
                        rs.getInt("Contact_ID"));
                appointments.add(newAppointment);
            }
            return appointments;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

}
