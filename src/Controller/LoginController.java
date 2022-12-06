package Controller;

import Model.Appointment;
import Model.User;
import Utilities.AppointmentSql;
import Utilities.FunctionLibrary;
import Utilities.UserSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TimeZone;

import static Utilities.FunctionLibrary.setLanguage;

public class LoginController implements Initializable {

    static Stage stage;
    static Parent scene;

    public TextField loginName;
    public TextField loginPass;
    public Button loginButton;
    public Text loginTimeZone;
    public Text clientSchedules;
    public Text timeZoneText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // DISPLAYS TIME ZONE
        loginTimeZone.setText(String.valueOf(ZoneId.of(TimeZone.getDefault().getID())));

        // CHANGES TEXT TO ENGLISH OR FRENCH
        clientSchedules.setText(setLanguage.getString("clientSchedule"));
        loginName.setPromptText(setLanguage.getString("userNamePrompt"));
        loginPass.setPromptText(setLanguage.getString("passwordPrompt"));
        loginButton.setText(setLanguage.getString("loginButton"));
        timeZoneText.setText(setLanguage.getString("timeZoneText"));
    }

    /**
     * MATCHES INPUT WITH DATABASE INFO AND GRANTS ACCESS IF CORRECT
     *
     * @param event LOGIN BUTTON CLICKED
     */
    public void onLoginB(ActionEvent event) throws SQLException, IOException {
        String username = loginName.getText();
        String password = loginPass.getText();
        int userId = UserSql.checkLogin(username, password);

        // ALERTS FOR INCORRECT INPUT
        if (loginName.getText().isEmpty() && loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(1);
        } else if (loginName.getText().isEmpty()) {
            FunctionLibrary.displayAlert(2);
        } else if (loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(3);
        } else if (userId > 0) {
            successfulLogin();
            // SWITCHES TO MAIN TABS CONTROLLER
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainTabs.fxml"));
            stage.setScene(new Scene(scene));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();

        } else {
            failedLogin();
        }
    }

    public void apptAlert() {
        // APPOINTMENT ALERT VARIABLES
        LocalDateTime local = LocalDateTime.now();
        LocalDateTime localPlus = local.plusMinutes(15);
        ObservableList<Appointment> upcomingAppt = FXCollections.observableArrayList();

        // UPCOMING APPOINTMENT ALERT
        try {
            ObservableList<Appointment> appointments = AppointmentSql.getAppointments();
            if (appointments != null) {
                for (Appointment appointment : appointments) {
                    if (appointment.getStartTime().isAfter(local) && appointment.getStartTime().isBefore(localPlus)) {
                        upcomingAppt.add(appointment);
                        Alert alertInform = new Alert(Alert.AlertType.INFORMATION);
                        alertInform.setTitle(setLanguage.getString("upcomingAppt"));
                        alertInform.setHeaderText(
                                setLanguage.getString("fifteenMinutes") + "\n" +
                                        setLanguage.getString("apptId") +
                                        appointment.getAppointmentId() + "\n" +
                                        setLanguage.getString("date") +
                                        appointment.getStartDate() + "\n" +
                                        setLanguage.getString("time") +
                                        appointment.getStartTime());
                        alertInform.showAndWait();
                    }
                }
            }
            if (upcomingAppt.size() < 1) {
                Alert alertInform = new Alert(Alert.AlertType.INFORMATION);
                alertInform.setTitle(setLanguage.getString("noUpcoming"));
                alertInform.setContentText("No upcoming appointments within the next fifteen minutes");
                alertInform.showAndWait()
                        // LAMBDA
                        .filter(response -> response == ButtonType.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void successfulLogin() throws IOException {
        apptAlert();

        // VARIABLES FOR LOGIN ACTIVITY
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm");
        FileWriter fWriter = new FileWriter("src/login_activity.txt", true);
        PrintWriter pWriter = new PrintWriter(fWriter);

        // STAMPS SUCCESSFUL LOGIN ACTIVITY TO TEXT FILE
        pWriter.println("LOGIN SUCCESSFUL\n" + "USER: " + loginName.getText() + "\n" + "TIME: " + dateTime.format(LocalDateTime.now()) + "\n");
        pWriter.close();
    }

    public void failedLogin() throws IOException {
        // VARIABLES FOR LOGIN ACTIVITY
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy H:mm");
        FileWriter fWriter = new FileWriter("src/login_activity.txt", true);
        PrintWriter pWriter = new PrintWriter(fWriter);

        FunctionLibrary.displayAlert(1);

        // STAMPS FAILED LOGIN TO TEXT FILE
        pWriter.println("LOGIN FAILED\n" + "USER: " + loginName.getText() + "\n" + "TIME: " + dateTime.format(LocalDateTime.now()) + "\n");
        pWriter.close();
    }
}