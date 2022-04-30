package controller;

import DB.DBC;
import DB.DBCountry;
import DB.DBCustomer;
import DB.DBDivision;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.util.Optional;

/**
 * FXML Controller class
 * @author Aaron Wolf
 */
public class CustomerController {
    public TableView<Customer> customers_table;
    public TableColumn<Customer, Integer> tbl_customer_id;
    public TableColumn<Customer, String> tbl_customer_name;
    public TableColumn<Customer, String> tbl_customer_address;
    public TableColumn<Customer, String> tbl_customer_postal_code;
    public TableColumn<Customer, String> tbl_customer_phone;
    public TableColumn<Customer, Integer> tbl_customer_division_id;
    public TextField txt_customer_ID;
    public TextField txt_name;
    public TextField txt_address;
    public TextField txt_postalCode;
    public TextField txt_phone;
    public Button btn_add_customer;
    public Button btn_dashboard;
    public Button btn_edit_customer;
    public ComboBox<Country> cb_country;
    public ComboBox<Division> cb_division;


    public void init_customer_table() {
        tbl_customer_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tbl_customer_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        tbl_customer_address.setCellValueFactory(new PropertyValueFactory<>("address"));
        tbl_customer_postal_code.setCellValueFactory(new PropertyValueFactory<>("postal_code"));
        tbl_customer_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tbl_customer_division_id.setCellValueFactory(new PropertyValueFactory<>("division_id"));
        try {
            customers_table.setItems(DBCustomer.getAllCustomers());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void add_customer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/customer_add.fxml"));
        Stage stage=(Stage) btn_add_customer.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void show_edit_form(ActionEvent actionEvent) throws IOException {
        Customer c = customers_table.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customer_edit.fxml"));
        Parent root = loader.load();

        CustomerController cc = loader.getController();
        cc.init_edit_form(c);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void init_edit_form(Customer c) {
        txt_customer_ID.setText(String.valueOf(c.getId()));
        txt_name.setText(c.getName());
        txt_address.setText(c.getAddress());
        txt_phone.setText(c.getPhone());
        txt_postalCode.setText(c.getPostal_code());
        try {
            Country cnt = DBCountry.getCountryByDivisionId(c.getDivision_id());
            ObservableList<Country> countries = DBCountry.getAllCountries();
            cb_country.setItems(countries);
            cb_country.setValue(cnt);

            ObservableList<Division> divs = DBDivision.getDivisionsByCountryId(cnt.getId());
            cb_division.setItems(divs);
            for(Division d : divs) {
                if (d.getId() == c.getDivision_id()) cb_division.setValue(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edit_customer(ActionEvent actionEvent) throws IOException {
        int id = Integer.parseInt(txt_customer_ID.getText());
        String name = txt_name.getText();
        String address = txt_address.getText();
        String postal_code = txt_postalCode.getText();
        String phone = txt_phone.getText();
        int did=0;
        boolean valid = true;
        String alert_text = "";
        txt_name.setStyle("-fx-border-width: 0;");
        txt_address.setStyle("-fx-border-width: 0;");
        txt_postalCode.setStyle("-fx-border-width: 0;");
        txt_phone.setStyle("-fx-border-width: 0;");
        cb_division.setStyle("-fx-border-width: 0;");
        if (name.isBlank() || name.length() > 80) {
            valid = false;
            txt_name.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Name is required and must be less then 80 characters. \n\n";
        }
        if (address.isBlank()) {
            valid = false;
            txt_address.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Address is required. \n\n";
        }
        if (postal_code.isBlank()) {
            valid = false;
            txt_postalCode.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Postal Code is required. \n\n";
        }
        if (phone.isBlank()) {
            valid = false;
            txt_phone.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Phone Number is required. \n\n";
        }
        try {
            did = cb_division.getValue().getId();
        }catch (Exception e) {
            valid = false;
            cb_division.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Division ID required (Select a country to populate divisions list).\n\n";
        }

        if(valid) {
            DBCustomer.update(id,name, address, postal_code, phone, did);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customers.fxml"));
            Parent root = loader.load();
            CustomerController controller = loader.getController();
            controller.init_customer_table();
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

    public void delete_customer(ActionEvent actionEvent) {
        Customer c = customers_table.getSelectionModel().getSelectedItem();
        int i = customers_table.getSelectionModel().getSelectedIndex();
        if(c!=null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Do want to delete customer "+c.getName()+"?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DBCustomer.delete(c.getId());
                customers_table.getItems().remove(c);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("You need to make a selection");
            alert.showAndWait();
        }
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

    public void cb_country_selected(ActionEvent actionEvent) {
        Country country = (Country) cb_country.getValue();
        try {
            ObservableList<Division> divs = DBDivision.getDivisionsByCountryId(country.getId());
            cb_division.setItems(divs);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/customers.fxml"));
        Parent root = loader.load();
        CustomerController controller = loader.getController();
        controller.init_customer_table();

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
