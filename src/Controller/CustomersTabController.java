package Controller;

import Model.Country;
import Model.Customer;
import Model.Division;
import Utilities.CountryQuery;
import Utilities.CustomerQuery;
import Utilities.DivisionQuery;
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

import static Main.JDBC.*;
import static Utilities.CustomerQuery.customers;
import static Utilities.CustomerQuery.getCustomers;


public class CustomersTabController implements Initializable {

    private static ObservableList<Division> divisions = FXCollections.observableArrayList();
    private static ObservableList<Country> countries = FXCollections.observableArrayList();

    // TABLE VIEW OF DATABASE CUSTOMERS
    public TableView<Customer> customerTableView;

    public Button addCustomer;
    public Button editCustomer;
    public Button deleteCustomer;

    public TextField idTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipCodeTextField;
    public TextField phoneTextField;

    public ComboBox<Country> countryComboBox;
    public ComboBox<Division> divisionComboBox;

    // COLUMNS OF THE CUSTOMER TABLE VIEW
    public TableColumn<Object, Object> idCol;
    public TableColumn<Object, Object> nameCol;
    public TableColumn<Object, Object> addressCol;
    public TableColumn<Object, Object> postalCodeCol;
    public TableColumn<Object, Object> phoneCol;
    public TableColumn<Object, Object> divisionIdCol;
    public TableColumn<Object, Object> divisionCol;
    public TableColumn<Object, Object> countryIdCol;
    public TableColumn<Object, Object> countryCol;

    //--------------------------------------------------------------------------------

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // SETS EACH COLUMN TO CUSTOMER INFO
        try {
            customerTableView.setItems(getCustomers());

            divisions = DivisionQuery.getDivisions();
            countries = CountryQuery.getCountries();
            countryComboBox.setItems(countries);
            divisionComboBox.setItems(divisions);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        // SETS COUNTRIES AND DIVISIONS TO THEIR RESPECTIVE COMBO BOX
        //setCountryComboBox();
        //setDivisionComboBox();
    }

    /**
     * PARSES INPUT INTO NEW CUSTOMER ENTRY IN DATABASE
     * @param event ADD CUSTOMER CLICKED
     */
    public void onAddCustomerB(ActionEvent event) {

        addCustomer();

    }

    public void addCustomer() {
        Customer customer = new Customer();

        customer.setCustomerName(nameTextField.getText());
        customer.setAddress(addressTextField.getText());
        customer.setPostalCode(zipCodeTextField.getText());
        customer.setPhoneNumber(phoneTextField.getText());
        customer.setDivisionId(divisionComboBox.getSelectionModel().getSelectedItem().getDivisionId());
        customer.setCountry(countryComboBox.getSelectionModel().getSelectedItem().getCountry());

        try {
            int insert = CustomerQuery.insertNewCustomer(customer);
            customerTableView.getItems().clear();
            customerTableView.setItems(CustomerQuery.getCustomers());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void onEditCustomerB(ActionEvent event) {
        Customer SC = customerTableView.getSelectionModel().getSelectedItem();

        idTextField.setText(String.valueOf(SC.getCustomerId()));
        nameTextField.setText(SC.getCustomerName());
        addressTextField.setText(SC.getAddress());
        zipCodeTextField.setText(SC.getPostalCode());
        phoneTextField.setText(SC.getPhoneNumber());

        for (Country country: countryComboBox.getItems()) {
            if (customerTableView.getSelectionModel().getSelectedItem().getCountry().equals(country.getCountry())) {
                countryComboBox.setValue(country);
                break;
            }
        }
        for (Division division: divisionComboBox.getItems()) {
            if (customerTableView.getSelectionModel().getSelectedItem().getDivision().equals(division.getDivision())) {
                divisionComboBox.setValue(division);
                break;
            }
        }
    }

    /**
     * DELETES DATABASE ENTRY OF SELECTED CUSTOMER
     * @param event DELETE CUSTOMER CLICKED
     */
    public void onDeleteCustomerB(ActionEvent event) {

        Customer SC = customerTableView.getSelectionModel().getSelectedItem();
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
                    CustomerQuery.deleteCustomer(SC);
                    customerTableView.getItems().clear();
                    customerTableView.setItems(CustomerQuery.getCustomers());
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
        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        if (!countryComboBox.getSelectionModel().isEmpty()) {
                for (Division division : divisions) {
                    if (division.getCountryId() == countryComboBox.getSelectionModel().getSelectedItem().getCountryId()) {
                        divisionList.add(division);
                }
            }
                divisionComboBox.setItems(divisionList);
        }
    }

    /**
     * SETS COMBO BOX TO CONTAIN COUNTRIES
     */
    private void setCountryComboBox() {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            countries = CountryQuery.getCountries();
            if (countries != null) {
                for (Country country : countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        countryComboBox.setItems(countries);
    }

    /**
     * SETS THE COMBO BOX TO CONTAIN FIRST LEVEL DIVISIONS
     */
    public void setDivisionComboBox() {
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            divisions = DivisionQuery.getDivisions();
            if (divisions != null) {
                for (Division division : divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        divisionComboBox.setItems(divisions);
    }

    public void clearTextFields() {
        idTextField.setText("User ID - Auto Generated");
        nameTextField.clear();
        addressTextField.clear();
        zipCodeTextField.clear();
        phoneTextField.clear();
        countryComboBox.getSelectionModel().clearSelection();
        divisionComboBox.getSelectionModel().clearSelection();
    }
}
