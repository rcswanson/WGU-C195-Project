package Controller;

import Model.Appointment;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static Model.Appointment.getAppointments;

public class AppointmentsTabController implements Initializable {

    public TableView<Appointment> appointmentTableView;
    public TableColumn<Object, Object> idCol;
    public TableColumn<Object, Object> titleCol;
    public TableColumn<Object, Object> desCol;
    public TableColumn<Object, Object> locCol;
    public TableColumn<Object, Object> contactCol;
    public TableColumn<Object, Object> typeCol;
    public TableColumn<Object, Object> startDateTimeCol;
    public TableColumn<Object, Object> endDateTimeCol;
    public TableColumn<Object, Object> customerIdCol;
    public TableColumn<Object, Object> userIdCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        appointmentTableView.setItems(getAppointments());
        idCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        desCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
}
