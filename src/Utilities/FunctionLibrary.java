package Utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

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
     */
    public static void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);
        Alert alertInform = new Alert(Alert.AlertType.INFORMATION);

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
        }
    }
}
