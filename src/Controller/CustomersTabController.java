package Controller;

import Main.JDBC;
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

import static Utilities.CustomerQuery.customers;
import static Utilities.CustomerQuery.getCustomers;


public class CustomersTabController implements Initializable {

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

    public ComboBox<String> countryComboBox;
    public ComboBox<String> divisionComboBox;

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

    int autoId = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // SETS EACH COLUMN TO CUSTOMER INFO
        customerTableView.setItems(getCustomers());

        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

        // SETS COUNTRIES AND DIVISIONS TO THEIR RESPECTIVE COMBO BOX
        setCountryComboBox();
        setDivisionComboBox();

    }

    /**
     * POPULATES DIVISION COMBO BOX CONCURRENT WITH SELECTED COUNTRY
     * @param event COMBO BOX CLICKED
     */
    public void onSelectCountry(ActionEvent event) {
        ObservableList<String> divisionList = FXCollections.observableArrayList();
        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisionsByCountry(countryComboBox.getSelectionModel().getSelectedItem());
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
            divisionComboBox.setItems(divisionList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * PARSES INPUT INTO NEW CUSTOMER ENTRY IN DATABASE
     * @param event ADD CUSTOMER CLICKED
     */
    public void onAddCustomerB(ActionEvent event) {
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
                    CustomerQuery.deleteCustomer(SC.getCustomerId());
                    customerTableView.getItems().clear();
                    customerTableView.setItems(CustomerQuery.getCustomers());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * SETS COMBO BOX TO CONTAIN COUNTRIES
     */
    private void setCountryComboBox() {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        try {
            ObservableList<Country> countries = CountryQuery.getCountries();
            if (countries != null) {
                for (Country country: countries) {
                    countryList.add(country.getCountry());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        countryComboBox.setItems(countryList);
    }

    /**
     * SETS THE COMBO BOX TO CONTAIN FIRST LEVEL DIVISIONS
     */
    public void setDivisionComboBox(){
        ObservableList<String> divisionList = FXCollections.observableArrayList();

        try {
            ObservableList<Division> divisions = DivisionQuery.getDivisions();
            if (divisions != null) {
                for (Division division: divisions) {
                    divisionList.add(division.getDivision());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        divisionComboBox.setItems(divisionList);
    }
}
