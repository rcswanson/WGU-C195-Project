package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage login) throws Exception {
        Parent root = FXMLLoader.load((getClass().getResource("/View/Login.fxml")));
        login.setTitle("QAM2 Project");
        login.setScene(new Scene(root, 500, 300));
        login.centerOnScreen();
        login.setResizable(false);
        login.show();
    }

    public static void main(String[] args) {

        JDBC.openConnection();

        launch(args);

        JDBC.closeConnection();

    }
}
