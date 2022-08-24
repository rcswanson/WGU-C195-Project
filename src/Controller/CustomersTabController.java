package Controller;

import Model.Customer;
import Utilities.CustomerQuery;
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

    public TableView<Customer> customerTableView;
    public Button addCustomer;
    public Button editCustomer;
    public Button deleteCustomer;
    public TextField userIdTextField;
    public static TextField nameTextField;
    public static TextField addressTextField;
    public static TextField zipCodeTextField;
    public static TextField phoneTextField;
    public ComboBox countryComboBox;
    public ComboBox divisionComboBox;

    public TableColumn<Object, Object> idCol;
    public TableColumn<Object, Object> nameCol;
    public TableColumn<Object, Object> addressCol;
    public TableColumn<Object, Object> postalCodeCol;
    public TableColumn<Object, Object> phoneCol;
    public TableColumn<Object, Object> divisionIdCol;
    public TableColumn<Object, Object> divisionCol;
    public TableColumn<Object, Object> countryIdCol;
    public TableColumn<Object, Object> countryCol;

    public static int autoId = 1;
    public Customer SC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerTableView.setItems(getCustomers());
        idCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
        divisionCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        countryIdCol.setCellValueFactory(new PropertyValueFactory<>("countryId"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));

    }

    public void onAddCustomerB(ActionEvent event) {
    }

    public void onEditCustomerB(ActionEvent event) {
    }

    public void onDeleteCustomerB() {

        if(customerTableView.getSelectionModel().getSelectedItem() != null) {
            SC = customerTableView.getSelectionModel().getSelectedItem();
        } else {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Customer");
        alert.setHeaderText("Delete Customer Record");
        alert.setContentText("Would you like to delete " + SC.getCustomerName() + " from customer records?");
        alert.showAndWait().ifPresent((response -> {
            if(response == ButtonType.OK) {
                try {
                    CustomerQuery.deleteCustomer(SC.getCustomerId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                customerTableView.setItems(CustomerQuery.getCustomers());
            }
        }));

    }

}
