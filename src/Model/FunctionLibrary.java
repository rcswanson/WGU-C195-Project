package Model;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

public class FunctionLibrary {

    public static final ResourceBundle setLanguage = ResourceBundle.getBundle("Languages/language", Locale.getDefault());

    public static void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);

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
