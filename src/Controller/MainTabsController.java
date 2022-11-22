package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainTabsController {

    static Stage stage;
    static Parent scene;

    public AnchorPane appointmentsTab;
    public AnchorPane customersTab;
    public AnchorPane reportsTab;
    public Button logOut;

    // LOGS OUT OF SESSION AND RETURNS TO LOGIN WINDOW
    public void onLogOut(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LOGOFF");
        alert.setContentText("Would you like to logout and return to the login screen?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
            stage.setScene(new Scene(scene));
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        }
    }
}
