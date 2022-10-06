package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Naasir
 */

public class DbConnection {
    private static final String DBNAME = "client_schedule";
    private static final String URL = "jdbc:mysql://localhost/" + DBNAME;
    private static final String USER = "sqlUser";
    private static final String PASS = "Passw0rd!";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    public static Connection conn;

    //public DbConnection() {}

    public static void openConnection() throws Exception
    {
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL, USER, PASS);
        System.out.println("Connection Opened Successfully");
    }
    public static void closeConnection() throws Exception
    {
        conn.close();
        System.out.println("Connection Closed Successfully");
    }
}
