package Controller;

import Model.Appointment;
import Utilities.AppointmentQuery;
import Utilities.CustomerQuery;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

    public Appointment SA;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentTableView.setItems(AppointmentQuery.getAppointments());
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

    public void onCancelAppt() {

        if(appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            SA = appointmentTableView.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Appointment");
        alert.setHeaderText("Cancel Appointment");
        alert.setContentText("Would you like to cancel the appointment " + SA.getTitle() + " from the records?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                try {
                    CustomerQuery.deleteCustomer(SA.getCustomerId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                appointmentTableView.setItems(AppointmentQuery.getAppointments());
            }
        }));

    }
}
