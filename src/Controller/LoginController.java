package Controller;

import Main.JDBC;
import Utilities.FunctionLibrary;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.Timer;


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

        loginTimeZone.setText(String.valueOf(ZoneId.systemDefault()));

        Locale locale = Locale.getDefault();
        Locale.setDefault(locale);

        clientSchedules.setText(FunctionLibrary.setLanguage.getString("clientSchedule"));
        loginName.setPromptText(FunctionLibrary.setLanguage.getString("userNamePrompt"));
        loginPass.setPromptText(FunctionLibrary.setLanguage.getString("passwordPrompt"));
        loginButton.setText(FunctionLibrary.setLanguage.getString("loginButton"));
        timeZoneText.setText(FunctionLibrary.setLanguage.getString("timeZoneText"));

    }

    public void onLoginB(ActionEvent event) {

        try {
            String username = loginName.getText();
            String password = loginPass.getText();
            int userId = checkCreds(username, password);
            if (loginName.getText().isEmpty() && loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(1);
            } else if (loginName.getText().isEmpty()) {
            FunctionLibrary.displayAlert(2);
            } else if (loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(3);
            } else if (userId > 0) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/MainTabs.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else if (userId < 0) {
                FunctionLibrary.displayAlert(1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int checkCreds(String username, String password) {

        try {
            String query = "SELECT * FROM users";
            Statement stmt = JDBC.connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("User_Name").equals(username)) {
                    if (rs.getString("Password").equals(password)) {
                        return rs.getInt("User_ID");
                    }
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return -1;
    }
}