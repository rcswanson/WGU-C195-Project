package Model;

import javafx.scene.control.Alert;

public class FunctionLibrary {

    public static void displayAlert(int alertType) {

        Alert alertError = new Alert(Alert.AlertType.ERROR);

        switch (alertType) {

            case 1:
                alertError.setTitle("ERROR");
                alertError.setHeaderText("User ID and Password Not Found!");
                alertError.showAndWait();
                break;
            case 2:
                alertError.setTitle("ERROR");
                alertError.setHeaderText("User ID Not Found!");
                alertError.showAndWait();
                break;
            case 3:
                alertError.setTitle("ERROR");
                alertError.setHeaderText("Password Not Found!");
                alertError.showAndWait();
                break;
        }
    }

}
