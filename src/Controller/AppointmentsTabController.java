package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Utilities.AppointmentSql;
import Utilities.ContactSql;
import Utilities.CustomerSql;
import Utilities.FunctionLibrary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Utilities.AppointmentSql.*;

// CONTROLLER FOR APPOINTMENT TAB
public class AppointmentsTabController implements Initializable {

    public TableView<Appointment> appointmentTableView;

    // TABLE COLUMNS
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

    // BUTTONS
    public Button addAppointment;
    public Button editAppointment;
    public Button cancelAppointment;

    // COMBBOXES
    public ComboBox<CharSequence> startTimeComboBox;
    public ComboBox<CharSequence> endTimeComboBox;
    public ComboBox<Integer> contactComboBox;
    public ComboBox<Integer> customerComboBox;
    public ComboBox<String> typeComboBox;

    // INPUT FIELDS
    public TextField apptIdTextField;
    public TextField titleTextField;
    public TextField descTextField;
    public TextField locationTextField;
    public TextField userIdTextField;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;

    // APPOINTMENT FILTERS
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

        // POPULATES COMBO BOX SELECTIONS
        setTimeComboBox();
        setCustomerComboBox();
        setContactComboBox();
        typeComboBox.getItems().addAll("Consult", "Customer Meeting", "English Meeting", "French Meeting");
    }

    // CONVERTS TIMES TO SYSTEM TIMEZONE
    public ZonedDateTime convertToTimeZone(LocalDateTime time, String zoneId) {
        return ZonedDateTime.of(time, ZoneId.of(zoneId));
    }

    /**
     * RUNS THROUGH IF STATEMENTS CHECKING FOR ERRORS
     * @return true
     * @throws SQLException
     */
    public boolean addErrorCheck() throws SQLException {
        // CHECKS FOR EMPTY INPUT FIELDS
        if (titleTextField.getText().isEmpty() ||
                descTextField.getText().isEmpty() ||
                locationTextField.getText().isEmpty() ||
                contactComboBox.getSelectionModel().isEmpty() ||
                typeComboBox.getSelectionModel().isEmpty() ||
                startDatePicker.getValue() == null ||
                startTimeComboBox.getSelectionModel().isEmpty() ||
                endDatePicker.getValue() == null ||
                endTimeComboBox.getSelectionModel().isEmpty() ||
                customerComboBox.getSelectionModel().isEmpty() ||
                userIdTextField.getText().isEmpty()) {
            FunctionLibrary.displayAlert(6);
            return false;
        }

        LocalDate startD = startDatePicker.getValue();
        LocalDate endD = endDatePicker.getValue();

        if (!startD.equals(endD)) {
            FunctionLibrary.displayAlert(7);
            return false;
        }

        LocalTime startT = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem());
        LocalTime endT = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem());

        if (endT.isBefore(startT) || endT == startT) {
            FunctionLibrary.displayAlert(8);
            return false;
        }

        LocalDateTime apptStart;
        LocalDateTime apptEnd;
        LocalDateTime selectedStart = startD.atTime(startT);
        LocalDateTime selectedEnd = endD.atTime(endT);

        ObservableList<Appointment> appointments = AppointmentSql.getApptByCustomerId(customerComboBox.getSelectionModel().getSelectedItem());
        for (Appointment appointment : appointments) {
            apptStart = appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime());
            apptEnd = appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime());
            if (apptStart.isAfter(selectedStart) && apptStart.isBefore(selectedEnd)) {
                FunctionLibrary.displayAlert(9);
            } else if (apptEnd.isAfter(selectedStart) && apptEnd.isBefore(selectedEnd)) {
                FunctionLibrary.displayAlert(9);
            }
        }
        return true;
    }

    /**
     * TAKES ALL INPUTTED INFO AND PUTS IT IN AN SQL INSERT STATEMENT
     * @param event ADD BUTTON PRESSED
     * @throws SQLException
     */
    public void onAddAppt(ActionEvent event) throws SQLException {

        boolean valid = addErrorCheck();

        if (valid) {
            if (apptIdTextField.getText().equals("Appointment ID - Auto Generated")) {
                AppointmentSql.insertAppointment(
                        titleTextField.getText(),
                        descTextField.getText(),
                        locationTextField.getText(),
                        contactComboBox.getSelectionModel().getSelectedItem(),
                        typeComboBox.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem())),
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(userIdTextField.getText()));
            }
            else {
                AppointmentSql.updateAppointment(
                        titleTextField.getText(),
                        descTextField.getText(),
                        locationTextField.getText(),
                        contactComboBox.getSelectionModel().getSelectedItem(),
                        typeComboBox.getSelectionModel().getSelectedItem(),
                        LocalDateTime.of(startDatePicker.getValue(), LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem())),
                        LocalDateTime.of(endDatePicker.getValue(), LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem())),
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        Integer.parseInt(userIdTextField.getText()),
                        Integer.parseInt(apptIdTextField.getText()));
            }
            clearTextFields();
            appointmentTableView.getItems().clear();
            appointmentTableView.setItems(AppointmentSql.getAppointments());
        }
    }

    /**
     * CLEARS ALL INPUT FIELDS
     * @param actionEvent CANCEL BUTTON PRESSED
     */
    public void onCancelB(ActionEvent actionEvent) {
        clearTextFields();
    }

    public void clearTextFields() {
        apptIdTextField.setText("Appointment ID - Auto Generated");
        titleTextField.clear();
        descTextField.clear();
        locationTextField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        startTimeComboBox.getSelectionModel().clearSelection();
        startDatePicker.getEditor().clear();
        endTimeComboBox.getSelectionModel().clearSelection();
        endDatePicker.getEditor().clear();
        customerComboBox.getSelectionModel().clearSelection();
        contactComboBox.getSelectionModel().clearSelection();
        userIdTextField.clear();
    }

    /**
     * CLEARS TABLE AND FILLS WITH ALL APPOINTMENTS FROM DATABASE
     * @param event RADIO BUTTON PRESSED
     */
    public void onAllApptB(ActionEvent event) {
        appointmentTableView.getItems().clear();
        appointmentTableView.setItems(getAppointments());
    }

    /**
     * CLEARS TABLE AND FILLS WITH ALL APPOINTMENTS OF THE CURRENT WEEK FROM DATABASE
     * @param event RADIO BUTTON PRESSED
     * @throws SQLException
     */
    public void onWeekApptB(ActionEvent event) throws SQLException {
        appointmentTableView.getItems().clear();
        appointmentTableView.setItems(getApptByWeek());
    }

    /**
     * CLEARS TABLE AND FILLS WITH ALL APPOINTMENTS OF THE CURRENT MONTH FROM DATABASE
     * @param event RADIO BUTTON PRESSED
     * @throws SQLException
     */
    public void onMonthApptB(ActionEvent event) throws SQLException {
        appointmentTableView.getItems().clear();
        appointmentTableView.setItems(getApptByMonth());
    }

    /**
     * GATHERS INFORMATION FROM DATABASE AND FILLS INPUT FIELDS
     * @param event EDIT BUTTON PRESSED
     */
    public void onEditAppt(ActionEvent event) throws SQLException {
        SA = appointmentTableView.getSelectionModel().getSelectedItem();

        try {
            Appointment appointment = AppointmentSql.getApptByApptId(SA.getAppointmentId());

            ZonedDateTime start = convertToTimeZone(appointment.getStartDate().atTime(appointment.getStartTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            ZonedDateTime end = convertToTimeZone(appointment.getEndDate().atTime(appointment.getEndTime().toLocalTime()), String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));
            if (appointment != null) {
                apptIdTextField.clear();
                apptIdTextField.setText(String.valueOf(appointment.getAppointmentId()));
                titleTextField.setText(appointment.getTitle());
                descTextField.setText(appointment.getDescription());
                locationTextField.setText(appointment.getLocation());
                typeComboBox.getSelectionModel().select(appointment.getType());
                startDatePicker.setValue(appointment.getStartDate());
                startTimeComboBox.getSelectionModel().select(String.valueOf(start.toLocalTime()));
                endDatePicker.setValue(appointment.getEndDate());
                endTimeComboBox.getSelectionModel().select(String.valueOf(end.toLocalTime()));
                customerComboBox.getSelectionModel().select(appointment.getCustomerId());
                contactComboBox.getSelectionModel().select(appointment.getContactId());
                userIdTextField.setText(String.valueOf(appointment.getUserId()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * DELETES DATABASE ENTRY OF SELECTED APPOINTMENT
     * @param event DELETE APPOINTMENT CLICKED
     */
    public void onDeleteAppt(ActionEvent event) {

        Appointment SA = appointmentTableView.getSelectionModel().getSelectedItem();
        if (SA == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select an appointment to delete.");
            alert.showAndWait();
        } else if (appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to delete " + SA.getAppointmentId() + ": " + SA.getType() + " from appointment records?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    AppointmentSql.deleteAppointment(SA);
                    appointmentTableView.getItems().clear();
                    appointmentTableView.setItems(AppointmentSql.getAppointments());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * POPULATES COMBO BOX WITH TIME SELECTIONS BETWEEN 8AM AND 10PM
     */
    public void setTimeComboBox() {
        ObservableList<CharSequence> time = FXCollections.observableArrayList();
        LocalTime startTime = LocalTime.of(8,0);
        LocalTime endTime = LocalTime.of(22,0);

        time.add(startTime.toString());
        while (startTime.isBefore(endTime)) {
            startTime = startTime.plusMinutes(15);
            time.add(startTime.toString());
        }
        startTimeComboBox.setItems(time);
        endTimeComboBox.setItems(time);
    }

    /**
     * POPULATES COMBOBOX WITH CUSTOMER IDs
     */
    public void setCustomerComboBox() {
        ObservableList<Integer> customerIdList = FXCollections.observableArrayList();
        try {
            ObservableList<Customer> customers = CustomerSql.getCustomers();
            if (customers != null) {
                for (Customer customer : customers) {
                    customerIdList.add(customer.getCustomerId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        customerComboBox.setItems(customerIdList);
    }

    /**
     * POPULATES COMBOBOX WITH CONTACT IDs
     */
    public void setContactComboBox() {
        ObservableList<Integer> contactIdList = FXCollections.observableArrayList();
        try {
            ObservableList<Contact> contacts = ContactSql.getContacts();
            if (contacts != null) {
                for (Contact contact : contacts) {
                    contactIdList.add(contact.getContactId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        contactComboBox.setItems(contactIdList);
    }
}
