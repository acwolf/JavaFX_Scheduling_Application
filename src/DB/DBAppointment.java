package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.Main;
import model.Appointment;
import model.Customer;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static DB.DBC.dbc;

/**
 * This class is used as an interface between Appointment objects and the database.
 * @author Aaron Wolf
 */
public class DBAppointment {
    /**
     * Gets all appointment from the database.
     * @return An ObservableList of all appointments in the database.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.*, Contacts.Contact_Name FROM appointments " +
            "LEFT JOIN Contacts on appointments.Contact_ID = contacts.Contact_ID";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("location");
                String type = rs.getString("Type");
                Timestamp start_ts = rs.getTimestamp("Start");
                Timestamp end_ts = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                LocalDateTime start = start_ts.toLocalDateTime();
                LocalDateTime end = end_ts.toLocalDateTime();

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id, contactName);
                allAppointments.add(a);
            }
        } catch (Exception e) {
            System.out.println("getAllAppointments Error: " + e.getMessage());
        }
        return allAppointments;
    }

    /**
     * Gets all appointments for the current month
     * @return An ObservableList of Appointments
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getMonthAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        int month = LocalDateTime.now().getMonthValue();
        int year = LocalDateTime.now().getYear();
        try {
            String sql = "SELECT appointments.*, Contacts.Contact_Name FROM appointments " +
                    "LEFT JOIN Contacts on appointments.Contact_ID = contacts.Contact_ID " +
                    "WHERE month(Start) = ? AND year(Start)=?";
            PreparedStatement ps = dbc.prepareStatement(sql);
            ps.setInt(1, month);
            ps.setInt(2, year);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("location");
                String type = rs.getString("Type");
                Timestamp start_ts = rs.getTimestamp("Start");
                Timestamp end_ts = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                LocalDateTime start = start_ts.toLocalDateTime();
                LocalDateTime end = end_ts.toLocalDateTime();

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id, contactName);
                allAppointments.add(a);
            }
        } catch (Exception e) {
            System.out.println("getMonthAppointments Error: " + e.getMessage());
        }

        return allAppointments;
    }

    /**
     * Gets all appointments for this week in the database.
     * @return An ObservableList of appointments.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Appointment> getWeekAppointments() throws SQLException, Exception {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT appointments.*, Contacts.Contact_Name FROM appointments " +
                    "LEFT JOIN Contacts on appointments.Contact_ID = contacts.Contact_ID " +
                    "WHERE YEARWEEK(Start) = YEARWEEK(now())";

            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("location");
                String type = rs.getString("Type");
                Timestamp start_ts = rs.getTimestamp("Start");
                Timestamp end_ts = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");
                String contactName = rs.getString("Contact_Name");

                LocalDateTime start = start_ts.toLocalDateTime();
                LocalDateTime end = end_ts.toLocalDateTime();

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id, contactName);
                allAppointments.add(a);
            }
        } catch (Exception e) {
            System.out.println("getWeekAppointments Error: " + e.getMessage());
        }
        return allAppointments;
    }
    /**
     * Gets all the apointment types stored in the databse
     * @return An ObservableList of unique appointment types stored in the databse
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<String> getAllTypes() throws SQLException, Exception {
        ObservableList<String> allTypes = FXCollections.observableArrayList();
        try {
            String sql = "SELECT distinct Type FROM appointments ";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                allTypes.add(rs.getString("Type"));
            }

        } catch (Exception e) {
            System.out.println("getAllTypes Error: " + e.getMessage());
        }
        return allTypes;
    }

    /**
     * Gets the total number of appointments of a given type for a given month.
     * @param month An integer representing a month.
     * @param type A string representing the type of appointments to look for.
     * @return
     */
    public static int get_type_month_total(int month, String type) {
        String sql = "SELECT COUNT(Appointment_ID) FROM appointments " +
                    "WHERE type=? AND month(Start)=?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setString(1, type);
            ps.setInt(2, month);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
                System.out.println("get_type_month Error: " + e.getMessage());
        }
        return 0;
    }

    /**
     * Gets all the appointments associated with a contact.
     * @param cid An integer representing the ID associated with a contact.
     * @return
     */
    public static ObservableList<Appointment> getAllByContact(int cid) {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Contact_ID=?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("location");
                String type = rs.getString("Type");
                Timestamp start_ts = rs.getTimestamp("Start");
                Timestamp end_ts = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                LocalDateTime start = start_ts.toLocalDateTime();
                LocalDateTime end = end_ts.toLocalDateTime();

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                allAppointments.add(a);
            }
        } catch (SQLException e) {
                System.out.println("getByContact Error: " + e.getMessage());
        }
        return allAppointments;
    }

    /*
     * Checks to make sure a new appointment does not conflict with already scheduled appointments for a given customer.
     * @param cid An integer ID associated with a customer.
     * @param start A LocalDateTime associated with the start date and time of a new appointment.
     * @param end A LocalDateTime associated with the end date and time a new appointment.
     * @return Returns ture if a conflicting appointment if found.
    public static boolean validate_new_appointment(int cid, LocalDateTime start, LocalDateTime end) {

        String sql = "SELECT COUNT(Appointment_ID) FROM appointments " +
                "WHERE Customer_ID=? AND ((? BETWEEN Start AND End) OR (? BETWEEN Start AND End) OR  (? <= Start AND ? >= End))";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setInt(1, cid);
            ps.setTimestamp(2, Timestamp.valueOf(start));
            ps.setTimestamp(3, Timestamp.valueOf(end));
            ps.setTimestamp(4, Timestamp.valueOf(start));
            ps.setTimestamp(5, Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return true;
        } catch (SQLException e) {
            System.out.println("Validate New Appointment Error: "+e.getMessage());
        }

        return false;
    }
     */
    /**
     * Checks to make sure a new appointment does not conflict with already scheduled appointments for a given customer.
     * @param cid An integer ID associated with a customer.
     * @param start A LocalDateTime associated with the start date and time of a new appointment.
     * @param end A LocalDateTime associated with the end date and time a new appointment.
     * @return Returns ture if a conflicting appointment if found.
    */
    public static boolean validate_new_appointment(int cid, LocalDateTime start, LocalDateTime end) {

        String sql = "SELECT Start, End FROM appointments WHERE Customer_ID=?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime rs_start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime rs_end = rs.getTimestamp("End").toLocalDateTime();

                if ((start.isAfter(rs_start) || start.equals(rs_start)) && (start.isBefore(rs_end) || start.equals(rs_end))) return true;
                if ((end.isAfter(rs_start) || end.equals(rs_start)) && (end.isBefore(rs_end) || end.equals(rs_end))) return true;
                if ((start.isBefore(rs_start) || start.equals(rs_start)) && (end.isAfter(rs_end) || end.equals(rs_end))) return true;
            }
        } catch (SQLException e) {
            System.out.println("Validate New Appointment Error: "+e.getMessage());
        }
        return false;
    }

    /**
     * Saves a new appointment into the database.
     * @param title The title of a new appointment.
     * @param descrip The description of a new appointment.
     * @param location The location of a new appointment.
     * @param type The type of a new appointment.
     * @param start The start date and time associated with a new appointment.
     * @param end The end date and time associated with a new appointment.
     * @param customer_id The customer ID associated with a new appointment.
     * @param contact_id The contact ID associated with a new appointemnt.
     * @return Returns a integer representing the ID of a saved appointment.
     */
    public static int save(String title, String descrip, String location, String type,
                           LocalDateTime start, LocalDateTime end, int customer_id, int contact_id) {
        int aId = 0;

        String sql = "INSERT INTO Appointments (Title, Description, Location, Type, Start, End, Create_Date, " +
                "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?, ?, ?)";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, descrip);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, Main.gUser.getUserName());
            ps.setString(8, Main.gUser.getUserName());
            ps.setInt(9, customer_id);
            ps.setInt(10, Main.gUser.getUserID());
            ps.setInt(11, contact_id);

            ps.execute();

            ps = dbc.prepareStatement("SELECT LAST_INSERT_ID() FROM Appointments");
            ResultSet rs = ps.executeQuery();
            rs.next();
            aId = Integer.parseInt(rs.getString(1));

        } catch (SQLException e) {
            System.out.println("Save Appointment Error: " + e.getMessage());
        }
        return aId;
    }

    /*
     * Checks to make sure and updated appointment does not conflict with other appointment associated with a customer.
     * @param customer_id The ID of a customer assocaited with a customer.
     * @param id    The ID of the appointment to be updates.
     * @param start The start date and time of the updated appointment.
     * @param end The end date and time of the updated appointment.
     * @return Returns true if there is a conflicting appointment if found.
    public static boolean validate_update_appointment(int customer_id, int id, LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT COUNT(Appointment_ID) FROM appointments " +
                "WHERE Customer_ID=? AND Appointment_ID != ? AND ((? BETWEEN Start AND End) OR (? BETWEEN Start AND End) OR  (? <= Start AND ? >= End))";
        try {

            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setInt(1, customer_id);
            ps.setInt(2, id);
            ps.setTimestamp(3, Timestamp.valueOf(start));
            ps.setTimestamp(4, Timestamp.valueOf(end));
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) == 0) return true;
        } catch (SQLException e) {
            System.out.println("Validate New Appointment Error: "+e.getMessage());
        }

        return false;

    }
     */
    /**
     * Checks to make sure and updated appointment does not conflict with other appointment associated with a customer.
     * @param customer_id The ID of a customer assocaited with a customer.
     * @param id    The ID of the appointment to be updates.
     * @param start The start date and time of the updated appointment.
     * @param end The end date and time of the updated appointment.
     * @return Returns true if there is a conflicting appointment if found.
     */
    public static boolean validate_update_appointment(int customer_id, int id, LocalDateTime start, LocalDateTime end) {
        String sql = "SELECT Start, End FROM appointments WHERE Customer_ID=? AND Appointment_ID != ?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setInt(1, customer_id);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalDateTime rs_start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime rs_end = rs.getTimestamp("End").toLocalDateTime();

                if ((start.isAfter(rs_start) || start.equals(rs_start)) && (start.isBefore(rs_end) || start.equals(rs_end))) return true;
                if ((end.isAfter(rs_start) || end.equals(rs_start)) && (end.isBefore(rs_end) || end.equals(rs_end))) return true;
                if ((start.isBefore(rs_start) || start.equals(rs_start)) && (end.isAfter(rs_end) || end.equals(rs_end))) return true;

            }

        } catch (SQLException e) {
            System.out.println("Validate Update Appointment Error: "+e.getMessage());
        }
        return false;

    }


    /**
     * Saves and updated appointment to the database.
     * @param id The ID of an appointment to be updated.
     * @param title The title of the appointment.
     * @param descrip The description of the appointment.
     * @param location The location of the appointment.
     * @param type The type of the appointment.
     * @param start The start date and time of the appointment.
     * @param end The end date and time of the appointment.
     * @param customer_id The ID of a customer associated with an appointment.
     * @param contact_id The ID of a contact associated with an appointment.
     */
    public static void update(int id, String title, String descrip, String location, String type,
                           LocalDateTime start, LocalDateTime end, int customer_id, int contact_id) {

        String sql = "UPDATE Appointments SET Title = ?, Description = ?, Location = ?, Type = ?, " +
                "Start = ?, End = ?, Last_Update = now(), Last_Updated_By = ?, Customer_ID = ?, " +
                "Contact_ID =? WHERE Appointment_ID = ?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, descrip);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5, Timestamp.valueOf(start));
            ps.setTimestamp(6, Timestamp.valueOf(end));
            ps.setString(7, Main.gUser.getUserName());
            ps.setInt(8, customer_id);
            ps.setInt(9, contact_id);
            ps.setInt(10, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Update Appointment Error: " + e.getMessage());
        }
    }

    /**
     * Delets an appointment from the database.
     * @param id The ID of an appointment to be deleted.
     */
    public static void delete(int id) {
        String sql = "DELETE FROM appointments WHERE Appointment_ID=?";

        try{
            PreparedStatement ps = dbc.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
        } catch (SQLException e) {
        System.out.println("Delete Appointment Error: " + e.getMessage());
    }

}

    /**
     * Get appointments for a given user happening within 15 minutes
     * @param userID The ID associated with a user
     * @return ObservableList of Appointment opjects
     */
    public static ObservableList<Appointment> getUpcoming(int userID) {
        ObservableList<Appointment> apts = FXCollections.observableArrayList();
        LocalDateTime from = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime to = ZonedDateTime.now(ZoneId.of("UTC")).toLocalDateTime().plusMinutes(15);

        String sql = "SELECT * FROM appointments WHERE User_ID=? AND Start >= ? AND Start <= ?";
        try {
            PreparedStatement ps = dbc.prepareStatement(sql);

            ps.setInt(1, userID);
            ps.setTimestamp(2, Timestamp.valueOf(from));
            ps.setTimestamp(3, Timestamp.valueOf(to));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("location");
                String type = rs.getString("Type");
                Timestamp start_ts = rs.getTimestamp("Start");
                Timestamp end_ts = rs.getTimestamp("End");
                int customer_id = rs.getInt("Customer_ID");
                int user_id = rs.getInt("User_ID");
                int contact_id = rs.getInt("Contact_ID");

                LocalDateTime start = start_ts.toLocalDateTime();
                LocalDateTime end = end_ts.toLocalDateTime();

                Appointment a = new Appointment(id, title, description, location, type, start, end, customer_id, user_id, contact_id);
                apts.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Get upcoming appointments Error: "+e.getMessage());
        }
        return apts;
    }
}
