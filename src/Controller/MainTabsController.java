package Controller;

import Utilities.UserSql;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainTabsController {

    static Stage stage;
    static Parent scene;

    public AnchorPane appointmentsTab;
    public AnchorPane customersTab;
    public Button logOut;
    public Text loggedUser;


    // LOGS OUT OF SESSION AND RETURNS TO LOGIN WINDOW
    public void onLogOut(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Login.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

//    public void loggedUser() {
//        if (UserSql.checkLogin("test", "test")) {
//            loggedUser.setText("Test");
//        } else if (UserSql.checkLogin("admin", "admin")) {
//            loggedUser.setText("Admin");
//        }
//
//    }
}
