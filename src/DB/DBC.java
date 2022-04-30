package DB;

import java.sql.Connection;
import java.sql.DriverManager;


/**
 * This class is used to establish database connections
 * @author carolyn.sher
 */
public class DBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static String password = "Passw0rd!";
    public static Connection dbc;

    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            dbc = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            dbc.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error Closing Data Connection DBC:" + e.getMessage());
        }
    }

    public static Connection getDBC(){
        return dbc;
    }


}
