package DB;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.DBC.dbc;

/**
 * This class is used to interface between user objects and the databse.
 */
public class DBUser {
    /**
     * Authenticates a user.
     * @param userName A users username.
     * @param password A users password.
     * @return Returns a user object upon successful login.
     * @throws SQLException
     * @throws Exception
     */
    public static User login(String userName, String password) throws SQLException, Exception{
        try {
            String sql="SELECT * FROM users WHERE User_Name  = '"+userName+"' AND Password = '"+password+"'";
            //Statement stmt = dbc.createStatement();
            //ResultSet rs = stmt.executeQuery(sql);
            PreparedStatement ps = dbc.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userid = rs.getInt("User_ID");
                String name = rs.getString("User_Name");
                String pword = rs.getString("Password");
                User u = new User(userid, name, pword);
                return u;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return null;
    }

    /**
     * Gets a user object associated with a given username
     * @param userName A username to find in the database.
     * @return A user object.
     * @throws SQLException
     * @throws Exception
     */
    public static User getUser(String userName) throws SQLException, Exception{
        try {
            String sql="SELECT * FROM user WHERE userName  = '"+userName+ "'";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int userid = rs.getInt("userid");
                String name = rs.getString("userName");
                String password = rs.getString("password");
                //String createDate=rs.getString("createDate");
                //String createdBy=rs.getString("createBy");
                //String lastUpdate=rs.getString("lastUpdate");
                //String lastUpdateby=rs.getString("lastUpdatedBy");
                //Calendar createDateCalendar=stringToCalendar(createDate);
                //Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                User u = new User(userid, name, password);
                return u;
            }
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return null;
    }

    /**
     * Gets all users from the database.
     * @return An ObservableList of user objects.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<User> getAllUsers() throws SQLException, Exception{
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM user";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int userid = rs.getInt("userid");
                String name = rs.getString("userName");
                String password = rs.getString("password");
                String createDate = rs.getString("createDate");
                String createdBy = rs.getString("createdBy");
                String lastUpdate = rs.getString("lastUpdate");
                String lastUpdateby = rs.getString("lastUpdateBy");
                //Calendar createDateCalendar=stringToCalendar(createDate);
                //Calendar lastUpdateCalendar=stringToCalendar(lastUpdate);
                User u = new User(userid, name, password);
                allUsers.add(u);
            }
        }
        catch(Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return allUsers;
    }

}
