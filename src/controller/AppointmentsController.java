package controller;

import DB.DBAppointment;
import DB.DBContact;
import DB.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;

import java.io.IOException;
import java.time.*;
import java.util.Optional;

/**
 * FXML Controller class
 * @author Aaron Wolf
 */
public class AppointmentsController {
    public TableView <Appointment> appointments_table;
    public TableColumn tbl_id;
    public TableColumn tbl_title;
    public TableColumn tbl_desc;
    public TableColumn tbl_location;
    public TableColumn tbl_type;
    public TableColumn tbl_start;
    public TableColumn tbl_end;
    public TableColumn tbl_customer_id;
    public TableColumn tbl_user_id;
    public TableColumn tbl_contact_id;
    
    public TextField txt_title;
    public TextField txt_description;
    public TextField txt_location;
    public TextField txt_type;
    public ComboBox<Contact> cb_contact;
    public ComboBox<Customer> cb_customer;
    public DatePicker dp_start_date;
    public DatePicker dp_end_date;
    public ComboBox<String> cb_start_hour;
    public ComboBox<String> cb_start_min;
    public ComboBox<String> cb_end_hour;
    public ComboBox<String> cb_end_min;
    public TextField apt_id;
    ObservableList<String> hours = FXCollections.observableArrayList();
    ObservableList<String> minutes = FXCollections.observableArrayList();


    public void init_appointment_table() {
        tbl_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbl_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        tbl_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tbl_start.setCellValueFactory(new PropertyValueFactory<>("readableStart"));
        tbl_end.setCellValueFactory(new PropertyValueFactory<>("readableEnd"));
        tbl_customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tbl_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
        tbl_contact_id.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        try {
            appointments_table.setItems(DBAppointment.getAllAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void view_all(ActionEvent actionEvent) {
        try {
            appointments_table.setItems(DBAppointment.getAllAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void view_month(ActionEvent actionEvent) {
        try {
            appointments_table.setItems(DBAppointment.getMonthAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void view_week(ActionEvent actionEvent) {
        try {
            appointments_table.setItems(DBAppointment.getWeekAppointments());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void new_appointment_form(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointments_add.fxml"));
        Parent root = loader.load();

        AppointmentsController ac = loader.getController();
        ac.init_new_form();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void init_new_form() throws Exception {
        cb_contact.setItems(DBContact.getAllContacts());
        cb_customer.setItems(DBCustomer.getAllCustomers());

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "05","10","15","20","25", "30","35","40", "45");
        cb_start_hour.setItems(hours);
        cb_start_min.setItems(minutes);
        cb_end_hour.setItems(hours);
        cb_end_min.setItems(minutes);

    }

    public void show_edit_form(ActionEvent actionEvent) throws Exception {
        try {
            Appointment a = appointments_table.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointment_edit.fxml"));
            Parent root = loader.load();

            AppointmentsController ac = loader.getController();
            ac.init_edit_form(a);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Warning");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("You need to select and appointment to edit.");
            alert.showAndWait();

        }

    }

    /**
     * Initializes the appointment edit form by populating form fields.
     * Uses 2 lambda expressions to loop through contacts and customers to preselect comboboxes.
     * @param a The Appointment object used to prepopulate the edit form.
     * @throws Exception
     */
    public void init_edit_form(Appointment a) throws Exception {
        apt_id.setText(String.valueOf(a.getId()));
        txt_title.setText(a.getTitle());
        txt_description.setText(a.getDescription());
        txt_type.setText(a.getType());
        txt_location.setText(a.getLocation());

        ObservableList<Contact> all_contacts = DBContact.getAllContacts();
        cb_contact.setItems(all_contacts);
        all_contacts.forEach(contact -> {
            if(contact.getId() == a.getContact_id()) cb_contact.setValue(contact);
        });
        ObservableList<Customer> all_customers = DBCustomer.getAllCustomers();
        cb_customer.setItems(all_customers);
        all_customers.forEach(customer -> {
          if(customer.getId() == a.getCustomer_id()) cb_customer.setValue(customer);
        });

        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        minutes.addAll("00", "05","10","15","20","25", "30","35","40", "45");

        dp_start_date.setValue(a.getStart().toLocalDate());
        cb_start_hour.setItems(hours);
        cb_start_hour.setValue(String.valueOf(a.getStart().getHour()));
        cb_start_min.setItems(minutes);
        cb_start_min.setValue(String.valueOf(a.getStart().getMinute()));
        cb_end_hour.setItems(hours);
        cb_end_hour.setValue(String.valueOf(a.getEnd().getHour()));
        cb_end_min.setItems(minutes);
        cb_end_min.setValue(String.valueOf(a.getEnd().getMinute()));
    }

    public void edit_appointment(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(apt_id.getText());
        String title = txt_title.getText();
        String descrip = txt_description.getText();
        String location = txt_location.getText();
        String type = txt_type.getText();
        LocalDateTime start=null;
        LocalDateTime end=null;
        int customer_id = 0;
        int contact_id = 0;

        boolean valid = true;
        String alert_text = "";
        txt_title.setStyle("-fx-border-width: 0;");
        txt_description.setStyle("-fx-border-width: 0;");
        txt_location.setStyle("-fx-border-width: 0;");
        txt_type.setStyle("-fx-border-width: 0;");
        dp_start_date.setStyle("-fx-border-width: 0;");
        cb_start_hour.setStyle("-fx-border-width: 0;");
        cb_start_min.setStyle("-fx-border-width: 0;");
        //dp_end_date.setStyle("-fx-border-width: 0;");
        cb_end_hour.setStyle("-fx-border-width: 0;");
        cb_end_min.setStyle("-fx-border-width: 0;");

        if (title.isBlank()) {
            valid = false;
            txt_title.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Title is required. \n\n";
        }
        if (descrip.isBlank()) {
            valid = false;
            txt_description.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Description is required. \n\n";
        }
        if (location.isBlank()) {
            valid = false;
            txt_location.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Location is required. \n\n";
        }
        if (type.isBlank()) {
            valid = false;
            txt_type.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Type is required. \n\n";
        }
        try {
            contact_id = cb_contact.getValue().getId();
        }catch (Exception e) {
            valid=false;
            cb_contact.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text+= "* A contact selection is required. \n\n";
        }
        try {
            customer_id = cb_customer.getValue().getId();
        }catch (Exception e) {
            valid=false;
            cb_customer.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text+= "* A customer selection is required. \n\n";
        }
        try {
            LocalDate date = dp_start_date.getValue();

            //Get start date and time
            String hour = cb_start_hour.getValue();
            String minute = cb_start_min.getValue();
            LocalDateTime start_time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
            ZonedDateTime local_start_time = start_time.atZone(ZoneId.systemDefault());
            ZonedDateTime utc_start_time = local_start_time.withZoneSameInstant(ZoneOffset.UTC);
            start = utc_start_time.toLocalDateTime();

            //Get end date and time
            String end_hour = cb_end_hour.getValue();
            String end_minute = cb_end_min.getValue();
            LocalDateTime end_time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(end_hour), Integer.parseInt(end_minute));
            ZonedDateTime local_end_time = ZonedDateTime.of(end_time, ZoneId.systemDefault());
            ZonedDateTime utc_end_time = local_end_time.withZoneSameInstant(ZoneOffset.UTC);
            end = utc_end_time.toLocalDateTime();

        }catch (Exception e) {
            valid=false;
            dp_start_date.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_start_hour.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_start_min.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_end_hour.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_end_min.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text +="* A valid date, start and end time is required. \n\n";
        }
        if (valid && DBAppointment.validate_update_appointment(customer_id, id, start, end)) {
            valid=false;
            alert_text += "This appointment conflicts with another customer appointment. \n\n";
        }


        if(valid) {
            DBAppointment.update(id, title, descrip, location, type, start, end, customer_id, contact_id);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointments.fxml"));
            Parent root = loader.load();
            AppointmentsController controller = loader.getController();
            controller.init_appointment_table();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Warning");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(alert_text);
            alert.showAndWait();
        }

    }

    public void save(ActionEvent actionEvent) throws IOException {
        String title = txt_title.getText();
        String descrip = txt_description.getText();
        String location = txt_location.getText();
        String type = txt_type.getText();
        LocalDateTime start=null;
        LocalDateTime end=null;
        int customer_id = 0;
        int contact_id = 0;

        boolean valid = true;
        String alert_text = "";
        txt_title.setStyle("-fx-border-width: 0;");
        txt_description.setStyle("-fx-border-width: 0;");
        txt_location.setStyle("-fx-border-width: 0;");
        txt_type.setStyle("-fx-border-width: 0;");
        dp_start_date.setStyle("-fx-border-width: 0;");
        cb_start_hour.setStyle("-fx-border-width: 0;");
        cb_start_min.setStyle("-fx-border-width: 0;");
        cb_end_hour.setStyle("-fx-border-width: 0;");
        cb_end_min.setStyle("-fx-border-width: 0;");
        cb_contact.setStyle("-fx-border-width: 0;");
        cb_customer.setStyle("-fx-border-width: 0;");

        if (title.isBlank()) {
            valid = false;
            txt_title.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Title is required. \n\n";
        }
        if (descrip.isBlank()) {
            valid = false;
            txt_description.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Description is required. \n\n";
        }
        if (location.isBlank()) {
            valid = false;
            txt_location.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Location is required. \n\n";
        }
        if (type.isBlank()) {
            valid = false;
            txt_type.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Type is required. \n\n";
        }
        try {
            contact_id = cb_contact.getValue().getId();
        }catch (Exception e) {
            valid=false;
            cb_contact.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text+= "* A contact selection is required. \n\n";
        }
        try {
            customer_id = cb_customer.getValue().getId();

        }catch (Exception e) {
            valid=false;
            cb_customer.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text+= "* A customer selection is required. \n\n";
        }
        try {
            LocalDate date = dp_start_date.getValue();

            //Get start date and time in UTC
            String hour = cb_start_hour.getValue();
            String minute = cb_start_min.getValue();
            LocalDateTime start_time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(hour), Integer.parseInt(minute));
            ZonedDateTime local_start_time = start_time.atZone(ZoneId.systemDefault());
            ZonedDateTime utc_start_time = local_start_time.withZoneSameInstant(ZoneOffset.UTC);
            start = utc_start_time.toLocalDateTime();

            //Get end date and time
            String end_hour = cb_end_hour.getValue();
            String end_minute = cb_end_min.getValue();
            LocalDateTime end_time = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), Integer.parseInt(end_hour), Integer.parseInt(end_minute));
            ZonedDateTime local_end_time = ZonedDateTime.of(end_time, ZoneId.systemDefault());
            ZonedDateTime utc_end_time = local_end_time.withZoneSameInstant(ZoneOffset.UTC);
            end = utc_end_time.toLocalDateTime();

            //validate against business hours 8am - 10pm EST
            if (local_start_time.withZoneSameInstant(ZoneId.of("US/Eastern")).getHour() < 8) {
                valid=false;
                alert_text += "* Start time can not be before 8am EST \n\n";
            }

            if (local_end_time.withZoneSameInstant(ZoneId.of("US/Eastern")).getHour() > 21){
                valid=false;
                alert_text += "* End time can not be after 10pm EST \n\n";
            }

        }catch (Exception e) {
            valid=false;
            dp_start_date.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_start_hour.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_start_min.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_end_hour.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            cb_end_min.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text +="* A valid date, start and end time is required. \n\n";
            System.out.println("set start end Error: " + e.getMessage());
        }
        //This is a bit confusing, skips if there's already an error, validate_new returns TRUE of there's a conflict
        if (valid && DBAppointment.validate_new_appointment(customer_id, start, end)) {
            valid=false;
            alert_text += "This appointment conflicts with another customer appointment. \n\n";
        }

        if(valid) {
            int new_appointmentID = DBAppointment.save(title, descrip, location, type, start, end, customer_id, contact_id);
            if (new_appointmentID != 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Appointment Added");
                alert.setHeaderText("New Appointment Created");
                alert.setContentText("Appointment created \n ID: "+new_appointmentID+"\nAppointment Type: "+type );
                alert.showAndWait();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointments.fxml"));
            Parent root = loader.load();
            AppointmentsController controller = loader.getController();
            controller.init_appointment_table();

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Warning");
            alert.setHeaderText("Invalid Input");
            alert.setContentText(alert_text);
            alert.showAndWait();
        }

    }

    public void delete_appointment(ActionEvent actionEvent) {
        Appointment a = appointments_table.getSelectionModel().getSelectedItem();
        if(a!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Do want to delete this appointment? \n ID: "+a.getId()+"\n Title: "+a.getTitle());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBAppointment.delete(a.getId());
                appointments_table.getItems().remove(a);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You need to make a selection");
            alert.showAndWait();
        }
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/appointments.fxml"));
        Parent root = loader.load();
        AppointmentsController ac = loader.getController();
        ac.init_appointment_table();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void goto_dashboard(ActionEvent actionEvent) throws IOException {
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
