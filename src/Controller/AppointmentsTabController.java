package Controller;

import Model.Appointment;
import Utilities.AppointmentSql;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Filter;

import static Utilities.AppointmentSql.*;

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

    public TextField apptIdTextField;
    public TextField titleTextField;
    public TextField descTextField;
    public TextField locationTextField;
    public TextField contactTextField;
    public TextField typeTextField;
    public TextField startTextField;
    public TextField endTextField;
    public TextField customerIdTextField;
    public TextField userIdTextField;
    public RadioButton allApptB;
    public RadioButton filterWeekB;
    public RadioButton filterMonthB;
    public ToggleGroup Filter;
    Appointment SA;

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

    public void onCancelB(ActionEvent actionEvent) {
        SA = appointmentTableView.getSelectionModel().getSelectedItem();
        apptIdTextField.setText(String.valueOf(SA.getAppointmentId()));
        titleTextField.setText(SA.getTitle());
        descTextField.setText(SA.getDescription());
        locationTextField.setText(SA.getLocation());
        contactTextField.setText(SA.getContactId());
        typeTextField.setText(SA.getType());

        customerIdTextField.setText(String.valueOf(SA.getCustomerId()));
        userIdTextField.setText(String.valueOf(SA.getUserId()));
    }

    /*
    public void filterByTime() {
        if (allApptB.isSelected()) {
            getAppointments();
        } else if (Filter.getSelectedToggle().equals(filterWeekB)) {
            try {

            } catch (SQLException se) {
                se.printStackTrace();
            }
        } else if (Filter.getSelectedToggle().equals(filterMonthB)) {
            try {

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    */

    public void onEditAppt(ActionEvent event) {
        SA = appointmentTableView.getSelectionModel().getSelectedItem();
        apptIdTextField.setText("Appointment ID - Auto Generated");
        titleTextField.clear();
        descTextField.clear();
        locationTextField.clear();
        contactTextField.clear();
        typeTextField.clear();

        customerIdTextField.clear();
        userIdTextField.clear();
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
                    AppointmentSql.cancelAppointment(SA);
                    appointmentTableView.getItems().clear();
                    appointmentTableView.setItems(AppointmentSql.getAppointments());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
