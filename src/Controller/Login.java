package Controller;

import Model.FunctionLibrary;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Login implements Initializable {

    static Stage stage;
    static Parent scene;

    public TextField loginID;
    public TextField loginPass;
    public Button loginButton;
    public Text loginTimeZone;

    public void onLoginB(ActionEvent event) throws IOException {

        if (loginID.getText().isEmpty() && loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(1);
        }
        else if (loginID.getText().isEmpty()) {
            FunctionLibrary.displayAlert(2);
        }
        else if (loginPass.getText().isEmpty()) {
            FunctionLibrary.displayAlert(3);
        }
        else {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/ApptsAndCustomers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    public void setLanguage() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loginTimeZone.setText(String.valueOf(ZoneId.systemDefault()));


    }
}