package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.DBC.dbc;

/**
 * This class is used to interface between Contact objects and the databse.
 * @author Aaron Wolf
 */
public class DBContact {
    /**
     * Gets all contacts stored in the database
     * @return An ObservableList of Contact objects
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM contacts";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Contact_ID");
                String name = rs.getString("Contact_Name");
                String email = rs.getString("Email");
                Contact c = new Contact(id, name, email);
                allContacts.add(c);
            }
            //return allCustomers;
        }
        catch(Exception e){
            System.out.println("getAllContacts Error: "+e.getMessage());
        }
        return allContacts;
    }
}
