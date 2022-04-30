package controller;

import DB.DBCountry;
import DB.DBCustomer;
import DB.DBDivision;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 * @author Aaron Wolf
 */
public class AddCustomerController implements Initializable {
    public TextField cName;
    public TextField cAddress;
    public TextField cPostalCode;
    public TextField cPhone;
    public Button btn_save;
    public Button btn_cancel;
    public ComboBox<Country> cb_country;
    public ComboBox<Division> cb_division;
    private int country_id;
    private int division_id;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            cb_country.setItems(DBCountry.getAllCountries());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cb_country_selected(ActionEvent actionEvent) {
        this.country_id = cb_country.getValue().getId();
        try {
            cb_division.setItems(DBDivision.getDivisionsByCountryId(country_id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cb_select_division(ActionEvent actionEvent) {
        this.division_id = cb_division.getValue().getId();
    }

    public void save_customer(ActionEvent actionEvent) throws IOException {
        String name = cName.getText();
        String address = cAddress.getText();
        String postal_code = cPostalCode.getText();
        String phone = cPhone.getText();
        int did;
        boolean valid = true;
        String alert_text = "";
        cName.setStyle("-fx-border-width: 0;");
        cAddress.setStyle("-fx-border-width: 0;");
        cPostalCode.setStyle("-fx-border-width: 0;");
        cPhone.setStyle("-fx-border-width: 0;");
        cb_division.setStyle("-fx-border-width: 0;");
        if (name.isBlank() || name.length() > 80) {
            valid = false;
            cName.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Name is required and must be less then 80 characters. \n\n";
        }
        if (address.isBlank()) {
            valid = false;
            cAddress.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Address is required. \n\n";
        }
        if (postal_code.isBlank()) {
            valid = false;
            cPostalCode.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
            alert_text += "* Postal Code is required. \n\n";
        }
        if (phone.isBlank()) {
            valid = false;
            cPhone.setStyle("-fx-border-color: RED; -fx-border-width: 1; -fx-border-radius: 5;");
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
            DBCustomer.save(name, address, postal_code, phone, this.division_id);

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

    public void cancel(ActionEvent actionEvent) {
    }

}
