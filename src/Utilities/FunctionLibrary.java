package Utilities;

import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import javax.swing.text.html.Option;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class FunctionLibrary {

    static Stage stage;
    static Parent scene;

    /**
     * THE RESOURCE BUNDLE FOR THE TWO LANGAUGES SUPPORTED ON THE LOGIN PAGE
     */
    public static final ResourceBundle setLanguage = ResourceBundle.getBundle("Languages/language", Locale.getDefault());

    /**
     * SEVERAL CASES OF ALERTS THROUGHOUT PROGRAM
     * CASES 1 THROUGH 5 ARE FOR LOGIN POPUPS
     * CASES 6 THROUGH 9 ARE FOR CUSTOMER AND APPOINTMENT CREATION
     */
    public static void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInform = new Alert(Alert.AlertType.INFORMATION);
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);

        switch (alertType) {
            case 1 -> {
                alertError.setTitle(setLanguage.getString("error"));
                alertError.setHeaderText(setLanguage.getString("idAndPasswordError"));
                alertError.showAndWait();
            }
            case 2 -> {
                alertError.setTitle(setLanguage.getString("error"));
                alertError.setHeaderText(setLanguage.getString("idError"));
                alertError.showAndWait();
            }
            case 3 -> {
                alertError.setTitle(setLanguage.getString("error"));
                alertError.setHeaderText(setLanguage.getString("passwordError"));
                alertError.showAndWait();
            }
            case 4 -> {
            }
            case 5 -> {
                alertInform.setTitle("noUpcoming");
                alertInform.showAndWait();
            }
            case 6 -> {
                alertError.setTitle("INPUT BOX EMPTY");
                alertError.setHeaderText("CHECK FOR EMPTY TEXT FIELDS");
                alertError.showAndWait();
            }
            case 7 -> {
                alertError.setTitle("DATE ERROR");
                alertError.setHeaderText("APPOINTMENT MUST START AND END ON SAME DAY");
                alertError.showAndWait();
            }
            case 8 -> {
                alertError.setTitle("TIME ERROR");
                alertError.setHeaderText("START TIME MUST BE BEFORE END TIME");
                alertError.showAndWait();
            }
            case 9 -> {
                alertError.setTitle("APPOINTMENT CONFLICT");
                alertError.setHeaderText("APPOINTMENT ALREADY EXISTS FOR CUSTOMER DURING TIME SELECTED");
                alertError.showAndWait();
            }
        }
    }
}
