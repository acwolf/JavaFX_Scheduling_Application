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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;

import java.io.IOException;
/**
 * FXML Controller class
 * @author Aaron Wolf
 */
public class ReportController {
    public ComboBox<String> cb_month;
    public ComboBox<String> cb_type;
    public TextField txt_total;
    public TableColumn tbl_aid;
    public TableColumn tbl_title;
    public TableColumn tbl_desc;
    public TableColumn tbl_type;
    public TableColumn tbl_start;
    public TableColumn tbl_end;
    public TableColumn tbl_customer_id;
    public ComboBox<Contact> cb_contact;
    public TableView appointments_table;
    public TableView customers_table;
    public TableColumn tbl_customer_name;
    public TableColumn tbl_customer_address;
    public TableColumn tbl_customer_postal_code;
    public TableColumn tbl_customer_phone;
    public TableColumn tbl_customer_division_id;

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

    public void init_customer_total() throws Exception {
        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January","February", "March","April","May","June","July","August","September","October","November","December");
        cb_month.setItems(months);
        cb_type.setItems(DBAppointment.getAllTypes());

    }

    public void load_customer_total(ActionEvent actionEvent) {
        int month = cb_month.getSelectionModel().getSelectedIndex() + 1;
        String type = cb_type.getValue();
        txt_total.setText(String.valueOf(DBAppointment.get_type_month_total(month, type)));
    }

    public void init_contact_schedule() throws Exception {
        ObservableList<Contact> all_contacts = DBContact.getAllContacts();
        cb_contact.setItems(all_contacts);
    }

    public void show_contact_schedule(ActionEvent actionEvent) {
        int contact_id = cb_contact.getValue().getId();

        tbl_aid.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        tbl_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        tbl_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        tbl_start.setCellValueFactory(new PropertyValueFactory<>("readableStart"));
        tbl_end.setCellValueFactory(new PropertyValueFactory<>("readableEnd"));
        tbl_customer_id.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        try {
            appointments_table.setItems(DBAppointment.getAllByContact(contact_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init_unscheduled() {
        tbl_customer_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_customer_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_customer_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbl_customer_postal_code.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        tbl_customer_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbl_customer_division_id.setCellValueFactory(new PropertyValueFactory<>("division_id"));
        try {
            customers_table.setItems(DBCustomer.getUnscheduled());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
