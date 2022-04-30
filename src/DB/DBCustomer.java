package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;

import static DB.DBC.dbc;

/**
 * This class is used to interface between customer objects and the datbase.
 * @author Aaron Wolf
 */
public class DBCustomer {
    /**
     * Gets all customers from the databse.
     * @return An ObservableList of customer objects.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division_id = rs.getInt("Division_ID");
                //Calendar createDateCalendar=stringToCalendar(createDate);
                //Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                Customer c = new Customer(id, name, address, zip, phone, division_id);
                allCustomers.add(c);
            }
            //return allCustomers;
        }
        catch(Exception e){
            System.out.println("getAllCustomers Error: "+e.getMessage());
        }
        return allCustomers;
    }

    /**
     * Saves a customer to the databse.
     * @param name The name of a customer.
     * @param address The address of a customer.
     * @param postal_code The postal code of a customer.
     * @param phone The phone number of a customer.
     * @param did The division ID associated with a customer.
     */
    public static void save(String name, String address, String postal_code, String phone, int did) {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?, ?, ?, ?, now(), ?, now(), ?,?)";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal_code);
            ps.setString(4, phone);
            ps.setString(5, Main.gUser.getUserName());
            ps.setString(6, Main.gUser.getUserName());
            ps.setInt(7, did);

            ps.execute();
        } catch (SQLException e) {
            System.out.println("Save Customer Error: "+e.getMessage());
        }
    }

    /**
     * Updates a customer record in the database.
     * @param cid The ID of the customer record to be updated.
     * @param name The name of a customer.
     * @param address The address of a customer.
     * @param postal_code The postal code of a customer.
     * @param phone The phone number of a customer.
     * @param did The division ID associated with a customer.
     */
    public static void update(int cid, String name, String address, String postal_code, String phone, int did) {
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone =?, " +
                "Last_Update = now(), Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postal_code);
            ps.setString(4, phone);
            ps.setString(5, Main.gUser.getUserName());
            ps.setInt(6, did);
            ps.setInt(7, cid);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Save Customer Error: "+e.getMessage());
        }
    }

    /**
     * Deletes a Customer from the database.
     * @param cid The ID associated with a customer.
     */
    public static void delete(int cid){
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("Save Customer Error: "+e.getMessage());
        }

    }

    /**
     * Gets a list of customers who do not have any appointments scheduled.
     * @return An ObservableList of customer objects.
     */
    public static ObservableList getUnscheduled() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers " +
                    "WHERE Customer_ID NOT IN (SELECT Customer_ID FROM Appointments)";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String zip = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int division_id = rs.getInt("Division_ID");
                Customer c = new Customer(id, name, address, zip, phone, division_id);
                allCustomers.add(c);
            }
        }
        catch(Exception e){
            System.out.println("getAllCustomers Error: "+e.getMessage());
        }
        return allCustomers;

    }
}
