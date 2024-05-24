package dyds.tvseriesinfo.model.database;

import java.sql.*;

public class DatabaseConnectionManager {
    private static final String URL_DATABASE = "jdbc:sqlite:./dictionary.db";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL_DATABASE);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadDatabase() {
        try  {
            connection = getConnection();
            DatabaseMetaData meta = connection.getMetaData();
            System.out.println("The driver name is " + meta.getDriverName());

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            statement.executeUpdate("create table catalog (id INTEGER, title string PRIMARY KEY, extract string, source integer)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
