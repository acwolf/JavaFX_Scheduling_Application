package controller;

import DB.DBAppointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.Main;
import model.Appointment;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * FXML Controller class
 * @author Aaron Wolf
 */
public class DashController {
    public Button btn_customers;
    public TextArea txt_notice;

    /**
     * Initializes the dashboard with notification of upcoming appointments.
     * Uses a lambda expression to loop through an ObservableList of upcoming appointments and notify the user on their dshboard.
     */
    public void init_dash(){
        ObservableList<Appointment> apts = DBAppointment.getUpcoming(Main.gUser.getUserID());
        StringBuilder notice = new StringBuilder("You have up coming appointments\n");
        if (apts != null) {
            apts.forEach(apt -> {
                notice.append("Appointment ID: " + apt.getId() + " Starts:" + apt.getReadableStart() + "\n");
            });
            txt_notice.setText(notice.toString());
        }
    }

    /**
     * Responds to "Appointments" button by initializing "View All Appointments" TableView
     * @param actionEvent
     * @throws IOException
     */
    public void view_appointments(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointments.fxml"));
        Parent root = loader.load();

        AppointmentsController ac = loader.getController();
        ac.init_appointment_table();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Responds to "Customer" button by initializing "View All Customers" TableView
     * @param actionEvent
     * @throws IOException
     */
    public void view_customers(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customers.fxml"));
        Parent root = loader.load();
        CustomerController controller = loader.getController();
        controller.init_customer_table();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Responds to "Customer Appointments" button by initializing "Customer Appointments" report screen.
     * @param actionEvent
     * @throws IOException
     */
    public void rp_customer_appointments(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report_customer_appointments.fxml"));
        Parent root = loader.load();

        ReportController c = loader.getController();
        c.init_customer_total();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Responds to "Contact Schedule" button by initializing "Contact Schedule" report screen.
     * @param actionEvent
     * @throws IOException
     */
    public void rp_contact_schedule(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report_contact_schedule.fxml"));
        Parent root = loader.load();

        ReportController c = loader.getController();
        c.init_contact_schedule();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Responds to "Unscheduled Customers" button by initializing "Unscheduled" report screen.
     * @param actionEvent
     * @throws IOException
     */
    public void rp_unscheduled(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/report_unscheduled.fxml"));
        Parent root = loader.load();

        ReportController c = loader.getController();
        c.init_unscheduled();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
