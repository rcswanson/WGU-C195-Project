package Controller;

import Main.JDBC;
import Model.Appointment;
import Model.Country;
import Model.Customer;
import Model.Division;
import Utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomersTabController implements Initializable {
    // TABLE VIEW OF DATABASE CUSTOMERS
    public TableView<Customer> customerTableView;

    public Button addCustomer;
    public Button editCustomer;
    public Button deleteCustomer;
    public Button cancelText;

    public TextField idTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipCodeTextField;
    public TextField phoneTextField;

    public ComboBox<String> countryComboBox;
    public ComboBox<String> divisionComboBox;

    // COLUMNS OF THE CUSTOMER TABLE VIEW
    public TableColumn<Object, Object> idCol;
    public TableColumn<Object, Object> nameCol;
    public TableColumn<Object, Object> addressCol;
    public TableColumn<Object, Object> postalCodeCol;
    public TableColumn<Object, Object> phoneCol;
    public TableColumn<Object, Object> divisionCol;
    public TableColumn<Object, Object> countryCol;

    private static Customer SC;

    //--------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // SETS EACH COLUMN TO CUSTOMER INFO
        customerTableView.setItems(CustomerSql.getCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        customerTableView.sort();
        // SETS COUNTRIES AND DIVISIONS TO THEIR RESPECTIVE COMBO BOX
        setCountryComboBox();
        setDivisionComboBox();
    }

    public boolean addErrorCheck() {
        // CHECKS FOR EMPTY INPUT FIELDS
        if (nameTextField.getText().isEmpty() ||
                addressTextField.getText().isEmpty() ||
                zipCodeTextField.getText().isEmpty() ||
                phoneTextField.getText().isEmpty() ||
                divisionComboBox.getSelectionModel().isEmpty() ||
                countryComboBox.getSelectionModel().isEmpty())
        {
            FunctionLibrary.displayAlert(4);
            return false;
        }
        return true;
    }

    /**
     * PARSES INPUT INTO NEW CUSTOMER ENTRY IN DATABASE
     * @param event ADD CUSTOMER button pressed
     */
    public void onAddCustomerB(ActionEvent event) throws SQLException {

        boolean valid = addErrorCheck();

        if (valid) {
            if (idTextField.getText().equals("User ID - Auto Generated")) {
                CustomerSql.insertCustomer(
                        nameTextField.getText(),
                        addressTextField.getText(),
                        zipCodeTextField.getText(),
                        phoneTextField.getText(),
                        divisionComboBox.getValue());
            }
            else {
                CustomerSql.updateCustomer(
                        nameTextField.getText(),
                        addressTextField.getText(),
                        zipCodeTextField.getText(),
                        phoneTextField.getText(),
                        divisionComboBox.getValue(),
                        Integer.parseInt(idTextField.getText()));
            }
            clearTextFields();
            customerTableView.getItems().clear();
            customerTableView.setItems(CustomerSql.getCustomers());
            disableFields();
        }
    }

    // CLEARS TEXT FIELDS
    public void clearTextFields() {
        idTextField.clear();
        idTextField.setText("User ID - Auto Generated");
        nameTextField.clear();
        addressTextField.clear();
        zipCodeTextField.clear();
        phoneTextField.clear();
        countryComboBox.getSelectionModel().clearSelection();
        divisionComboBox.getSelectionModel().clearSelection();
    }

    /**
     * CLEARS ALL INPUT FIELDS OF INFO
     * @param actionEvent CANCEL BUTTON PRESSED
     */
    public void onCancelB(ActionEvent actionEvent) {
        clearTextFields();
        disableFields();
    }

    /**
     * TAKES ALL INFO FROM SELECTED DATABASE ENTRY AND FILLS INPUT FIELDS WITH INFO
     * @param event EDIT CUSTOMER PRESSED
     */
    public void onEditCustomerB(ActionEvent event) {
        SC = customerTableView.getSelectionModel().getSelectedItem();

        idTextField.clear();
        idTextField.setText(String.valueOf(SC.getCustomerId()));
        nameTextField.setText(SC.getCustomerName());
        addressTextField.setText(SC.getAddress());
        zipCodeTextField.setText(SC.getPostalCode());
        phoneTextField.setText(SC.getPhoneNumber());
        countryComboBox.getSelectionModel().select(SC.getCountry());
        divisionComboBox.getSelectionModel().select(SC.getDivision());
    }

    /**
     * DELETES DATABASE ENTRY OF SELECTED CUSTOMER
     * @param event DELETE CUSTOMER CLICKED
     */
    public void onDeleteCustomerB(ActionEvent event) {
        SC = customerTableView.getSelectionModel().getSelectedItem();
        if (SC == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("You must select a customer to delete.");
            alert.showAndWait();
        } else if (customerTableView.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Would you like to delete " + SC.getCustomerName() + " from customer records?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && (result.get() == ButtonType.OK)) {
                try {
                    ObservableList<Appointment> appointments = AppointmentSql.getApptByCustomerId(SC.getCustomerId());
                    if (appointments != null && appointments.size() < 1) {
                        CustomerSql.deleteCustomer(SC);
                        customerTableView.getItems().clear();
                        customerTableView.setItems(CustomerSql.getCustomers());
                    } else {
                        Alert deleteAlert = new Alert(Alert.AlertType.ERROR);
                        deleteAlert.setTitle("UNSUCCESSFUL");
                        deleteAlert.setContentText("You must delete all appointments under Customer ID: " + SC.getCustomerId() + " before deleting this customer");
                        deleteAlert.showAndWait();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * POPULATES DIVISION COMBO BOX CONCURRENT WITH SELECTED COUNTRY
     * @param event COMBO BOX CLICKED
     */
    public void onSelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisionsByCountry = DivisionSql.getDivisionsByCountry(countryComboBox.getSelectionModel().getSelectedItem());
            if (divisionsByCountry != null) {
                for (Division division : divisionsByCountry) {
                    divisionList.add(division.getDivision());
                }
            }
            divisionComboBox.setItems(divisionList);
        } catch (NullPointerException | SQLException e) {
            System.out.print("NullPointer");
        }
    }

    /**
     * SETS COMBO BOX TO CONTAIN COUNTRIES
     */
    private void setCountryComboBox() {
        ObservableList<String> countryList = FXCollections.observableArrayList();
        ObservableList<Country> countries = CountrySql.getCountries();
        if (countries != null) {
            for (Country country : countries) {
                countryList.add(country.getCountry());
            }
        }
        countryComboBox.setItems(countryList);
    }

    /**
     * SETS THE COMBO BOX TO CONTAIN FIRST LEVEL DIVISIONS
     */
    public void setDivisionComboBox() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionSql.getDivisions();
            if (divisions != null) {
                for (Division division : divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        divisionComboBox.setItems(divisionList);
    }

    public void onUnlockFields(ActionEvent actionEvent) {
        nameTextField.setDisable(false);
        addressTextField.setDisable(false);
        zipCodeTextField.setDisable(false);
        phoneTextField.setDisable(false);
        countryComboBox.setDisable(false);
        divisionComboBox.setDisable(false);
    }

    public void disableFields() {
        nameTextField.setDisable(true);
        addressTextField.setDisable(true);
        zipCodeTextField.setDisable(true);
        phoneTextField.setDisable(true);
        countryComboBox.setDisable(true);
        divisionComboBox.setDisable(true);
    }
}
