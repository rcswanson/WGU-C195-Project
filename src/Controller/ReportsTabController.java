package Controller;

import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Utilities.AppointmentSql;
import Utilities.ContactSql;
import Utilities.CustomerSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportsTabController implements Initializable {
    public ToggleGroup ReportFilter;
    public ComboBox<String> monthComboBox;
    public ComboBox<Integer> contactComboBox;
    public ComboBox<Integer> customerComboBox;
    public RadioButton monthRadio;
    public RadioButton contactRadio;
    public RadioButton customerRadio;
    public Button generateMonthType;
    public Button generateContact;
    public Button generateCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monthComboBox.getItems().addAll("Month", "Type");
        setContactComboBox();
        setCustomerComboBox();
    }

    public void onMonth(ActionEvent actionEvent) {
        monthComboBox.setDisable(false);
        generateMonthType.setDisable(false);
        contactComboBox.setDisable(true);
        generateContact.setDisable(true);
        customerComboBox.setDisable(true);
        generateCustomer.setDisable(true);
    }

    public void onContact(ActionEvent actionEvent) {
        monthComboBox.setDisable(true);
        generateMonthType.setDisable(true);
        contactComboBox.setDisable(false);
        generateContact.setDisable(false);
        customerComboBox.setDisable(true);
        generateCustomer.setDisable(true);
    }

    public void onCustomer(ActionEvent actionEvent) {
        monthComboBox.setDisable(true);
        generateMonthType.setDisable(true);
        contactComboBox.setDisable(true);
        generateContact.setDisable(true);
        customerComboBox.setDisable(false);
        generateCustomer.setDisable(false);
    }

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

    public void generateMonthType(ActionEvent actionEvent) throws SQLException {
        // HOLDS APPOINTMENTS PER MONTH
        ObservableList<Integer> January = FXCollections.observableArrayList();
        ObservableList<Integer> February = FXCollections.observableArrayList();
        ObservableList<Integer> March = FXCollections.observableArrayList();
        ObservableList<Integer> April = FXCollections.observableArrayList();
        ObservableList<Integer> May = FXCollections.observableArrayList();
        ObservableList<Integer> June = FXCollections.observableArrayList();
        ObservableList<Integer> July = FXCollections.observableArrayList();
        ObservableList<Integer> August = FXCollections.observableArrayList();
        ObservableList<Integer> September = FXCollections.observableArrayList();
        ObservableList<Integer> October = FXCollections.observableArrayList();
        ObservableList<Integer> November = FXCollections.observableArrayList();
        ObservableList<Integer> December = FXCollections.observableArrayList();

        // HOLDS APPOINTMENTS PER TYPE
        ObservableList<String> Consult = FXCollections.observableArrayList();
        ObservableList<String> CustomerMeeting = FXCollections.observableArrayList();
        ObservableList<String> English = FXCollections.observableArrayList();
        ObservableList<String> French = FXCollections.observableArrayList();

        ObservableList<Appointment> appointments = AppointmentSql.getAppointments();

        if (appointments != null) {
            for (Appointment appointment : appointments) {
                LocalDate date = appointment.getStartDate();
                String type = appointment.getType();

                // IF STATEMENTS FOR GATHERING APPOINTMENTS BY MONTH
                if (date.getMonth().equals(Month.of(1))) {
                    January.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(2))) {
                    February.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(3))) {
                    March.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(4))) {
                    April.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(5))) {
                    May.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(6))) {
                    June.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(7))) {
                    July.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(8))) {
                    August.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(9))) {
                    September.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(10))) {
                    October.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(11))) {
                    November.add(date.getMonthValue());
                }
                if (date.getMonth().equals(Month.of(12))) {
                    December.add(date.getMonthValue());
                }
                // IF STATEMENTS FOR GATHERING APPOINTMENTS BY TYPE
                if (type.equals("Consult")) {
                    Consult.add(type);
                }
                if (type.equals("Customer Meeting")) {
                    CustomerMeeting.add(type);
                }
                if (type.equals("English Meeting")) {
                    English.add(type);
                }
                if (type.equals("French Meeting")) {
                    French.add(type);
                }
            }
        }
        if (monthComboBox.getValue().equals("Month")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointments Per Month");
            alert.setHeaderText("REPORT GENERATED");
            alert.setContentText(
                    "January: " + January.size() +
                            "\nFebruary: " + February.size() +
                            "\nMarch: " + March.size() +
                            "\nApril: " + April.size() +
                            "\nMay: " + May.size() +
                            "\nJune: " + June.size() +
                            "\nJuly: " + July.size() +
                            "\nAugust: " + August.size() +
                            "\nSeptember: " + September.size() +
                            "\nOctober: " + October.size() +
                            "\nNovember: " + November.size() +
                            "\nDecember : " + December.size()
            );
            alert.showAndWait();
        }
        if (monthComboBox.getValue().equals("Type")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointments Per Type");
            alert.setHeaderText("REPORT GENERATED");
            alert.setContentText(
                    "Consult: " + Consult.size() +
                            "\nCustomer Meeting: " + CustomerMeeting.size() +
                            "\nEnglish Meeting: " + English.size() +
                            "\nFrench Meeting: " + French.size());
            alert.showAndWait();
        }
    }

    public void generateContact(ActionEvent actionEvent) throws SQLException {
        int contactId = contactComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> apptByContact = AppointmentSql.getApptByContactId(contactId);
        if (apptByContact != null) {
            for (Appointment appointment : apptByContact) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointments of Contact ID: " + contactId);
                alert.setHeaderText("REPORT GENERATED");
                alert.setContentText(
                        "Appointment ID: " + appointment.getAppointmentId() +
                                "\nTitle: " + appointment.getTitle() +
                                "\nDescription: " + appointment.getDescription() +
                                "\nType: " + appointment.getType() +
                                "\nStart: " + appointment.getStartTime() +
                                "\nEnd: " + appointment.getEndTime() +
                                "\nCustomer ID: " + appointment.getCustomerId()
                );
                alert.showAndWait();
            }
        }
        if (apptByContact.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointments of Contact ID: " + contactId);
            alert.setHeaderText("REPORT GENERATED");
            alert.setContentText("No appointments found for selected contact");
            alert.showAndWait();
        }
    }

    public void generateCustomer(ActionEvent actionEvent) throws SQLException {
        int customerId = customerComboBox.getSelectionModel().getSelectedItem();
        ObservableList<Appointment> apptByCustomer = AppointmentSql.getApptByCustomerId(customerId);
        if (apptByCustomer != null) {
            for (Appointment appointment : apptByCustomer) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointments of Customer ID: " + customerId);
                alert.setHeaderText("REPORT GENERATED");
                alert.setContentText(
                        "Appointment ID: " + appointment.getAppointmentId() +
                                "\nTitle: " + appointment.getTitle() +
                                "\nDescription: " + appointment.getDescription() +
                                "\nType: " + appointment.getType() +
                                "\nStart: " + appointment.getStartTime() +
                                "\nEnd: " + appointment.getEndTime() +
                                "\nContact ID: " + appointment.getContactId()
                );
                alert.showAndWait();
            }
        }
        if (apptByCustomer.size() < 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Appointments of Customer ID: " + customerId);
            alert.setHeaderText("REPORT GENERATED");
            alert.setContentText("No appointments found for selected customer");
            alert.showAndWait();
        }
    }
}
