package DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static DB.DBC.dbc;

/**
 * This class is an interface between division objects and the database.
 */
public class DBDivision {
    /**
     * Gets a list of all divisions associated with a given country.
     * @param id The ID of a country.
     * @return An ObservableList of division objects.
     * @throws SQLException
     * @throws Exception
     */
    public static ObservableList<Division> getDivisionsByCountryId(int id) throws SQLException, Exception{
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            String sql="SELECT Division_ID, Division, Country_ID FROM first_level_divisions WHERE Country_ID  = '"+id+ "'";
            Statement stmt = dbc.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int did = rs.getInt("Division_ID");
                String dname = rs.getString("Division");
                int cid = rs.getInt("Country_ID");
                Division d = new Division(did, dname, cid);
                divisions.add(d);
            }
        }
        catch(Exception e){
            System.out.println("DBDivsions Error: "+e.getMessage());
        }
        return divisions;
    }

}
