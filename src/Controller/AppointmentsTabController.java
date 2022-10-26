package Controller;

import Model.Appointment;
import Model.Customer;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static Utilities.AppointmentQuery.appointments;
import static Utilities.AppointmentQuery.getAppointments;
import static Utilities.CustomerQuery.customers;

public class AppointmentsTabController implements Initializable {

    public TableView<Appointment> appointmentTableView;

    public TableColumn<Object, Object> idCol;
    public TableColumn<Object, Object> titleCol;
    public TableColumn<Object, Object> desCol;
    public TableColumn<Object, Object> locCol;
    public TableColumn<Object, Object> contactIdCol;
    public TableColumn<Object, Object> typeCol;
    public TableColumn<Object, Object> startDateTimeCol;
    public TableColumn<Object, Object> endDateTimeCol;
    public TableColumn<Object, Object> customerIdCol;
    public TableColumn<Object, Object> userIdCol;

    public Button addAppointment;
    public Button editAppointment;
    public Button cancelAppointment;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        // SETS EACH COLUMN TO APPOINTMENT INFO
        appointmentTableView.setItems(getAppointments());
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    public void onAddAppt(ActionEvent event) {
    }


    public void onEditAppt(ActionEvent event) {
    }

    /**
     * DELETES DATABASE ENTRY OF SELECTED APPOINTMENT
     * @param event DELETE APPOINTMENT CLICKED
     */
    public void onCancelAppt(ActionEvent event) {

        Appointment SA = appointmentTableView.getSelectionModel().getSelectedItem();
        if (SA == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to delete " + SA.getTitle() + " from appointment records?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    AppointmentQuery.cancelAppointment(SA.getAppointmentId());
                    appointmentTableView.getItems().clear();
                    appointmentTableView.setItems(AppointmentQuery.getAppointments());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
