package controller;

import DB.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import model.User;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/**
 * FXML Controller class to handel user logins
 * @author Aaron Wolf
 */
public class LoginController implements Initializable {
    public TextField txt_username;
    public PasswordField txt_password;
    public Label lbl_zoneid;
    public Button btn_submit;
    public String error_hd;
    public String error_msg;

    /**
     * Method to initialize login screen to show current user timezone and define strings used to internationalize alert boxes.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoneId zone = ZoneId.systemDefault();
        lbl_zoneid.setText("location: "+String.valueOf(zone));
        error_hd = resourceBundle.getString("login_error");
        error_msg = resourceBundle.getString("user_not_found");

    }

    /**
     * Method to handel login attempts, logs successful and failed logins to login_activity.txt
     * @param actionEvent
     * @throws Exception
     */
    public void submit_login(ActionEvent actionEvent) throws Exception {
        FileWriter fw = new FileWriter("login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fw);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("h:mm a  LLLL d, yyyy");
        String login_time = df.format(LocalDateTime.now());

        String username = txt_username.getText();
        String password = txt_password.getText();
        User mainUser = DBUser.login(username, password);

        if (mainUser == null) {
            pw.println("Failed login username: " + username + " at " + login_time);
            pw.close();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(error_hd);
            alert.setHeaderText(error_hd);
            //alert.setContentText("Login Error");
            alert.setContentText(error_msg);
            alert.showAndWait();
        }else {
            pw.println(username+" logged in at " + login_time);
            pw.close();

            Main.gUser = mainUser;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dash.fxml"));
            Parent root = loader.load();

            DashController c = loader.getController();
            c.init_dash();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
