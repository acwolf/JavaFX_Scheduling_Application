package main;
import DB.DBC;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

import java.util.ResourceBundle;

/**
 * This class is used to fire up a customer scheduling application.
 *
 *  @author Aaron Wolf
 * */
public class Main extends Application {

    public static User gUser;

    /**
     * The Main method starts this JavaFX application.
     * NOTE: To test internationalization functionality uncomment Local.setDefault
     * @param args
     */
    public static void main(String[] args) {
        //Locale.setDefault(new Locale("fr"));
        DBC.openConnection();
        launch(args);
        DBC.closeConnection();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle rb = ResourceBundle.getBundle("util/lang");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/login.fxml"));
        loader.setResources(rb);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Schedule App by Aaron Wolf");
        stage.show();
    }
}

