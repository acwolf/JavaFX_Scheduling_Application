package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.DBC.dbc;

/**
 * This object is used to interface between Country objects and the database.
 * @author Aaron Wolf
 */
public class DBCountry {
    /**
     * Gets all countries stored in the database.
     * @return An ObservableList of Country objects.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");
                Country c = new Country(id, name);
                allCountries.add(c);
            }
        }
        catch(Exception e){
            System.out.println("getAllCustomers Error: "+e.getMessage());
        }
        return allCountries;
    }

    /**
     * Gets all country's associated with a given division from the database.
     * @param did An integer representing a division ID.
     * @return
     */
    public static Country getCountryByDivisionId(int did){
        try {
            String sql = "Select c.Country_ID, c.Country FROM countries AS c " +
                    "INNER JOIN first_level_divisions AS fld ON fld.Country_ID=c.Country_ID " +
                    "WHERE fld.Division_ID=?";
            PreparedStatement ps = dbc.prepareStatement(sql);
            ps.setInt(1, did);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Country c = new Country(rs.getInt(1), rs.getString(2));
                return c;
            }
        }catch (Exception e){
            System.out.println("getCountryByDivisionId Error: "+e.getMessage());
        }
        return null;
    }
}
