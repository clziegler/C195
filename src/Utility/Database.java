package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author charlesziegler
 */


public class Database {
    private static Connection con;
    private static final String DBNAME = "U04FDf";
    private static final String USERNAME = "U04FDf";
    private static final String PASSWORD = "53688222741";
    private static final String HOST = "3.227.166.251";
    private static final String URL = "jdbc:mysql://" + HOST + "/" + DBNAME;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

    public Database() {
    }

    /**
     * Connects to the database using the defined values.
     */
    public static void databaseConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found: " + e.getMessage());
        } finally {
            System.out.println("Connected to database.");
        }
    }

    public static Connection getDatabaseConnection(){
        return con;
    }

    public static void disconnect() {
        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Disconnected from database.");
        }
    }
}

