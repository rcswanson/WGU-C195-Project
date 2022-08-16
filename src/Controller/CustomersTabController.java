package Controller;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import static Main.JDBC.connection;
import static Model.Customer.getCustomers;

public class CustomersTabController implements Initializable {
    public TableView<Customer> customerTableView;
    public Button addCustomer;
    public Button editCustomer;
    public Button deleteCustomer;
    public TextField userIdTextField;
    public TextField nameTextField;
    public TextField addressTextField;
    public TextField zipCodeTextField;
    public TextField phoneTextField;
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

    public void onDeleteCustomerB(ActionEvent event) {
    }
}
