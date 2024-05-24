package dyds.tvseriesinfo.model.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBase implements SeriesDAO {
    private HashMap<OperationType, List<ListenerModelSeries>> listeners;

    public DataBase() {
        initListenersConfiguration();
    }

    public void addListener(OperationType operationType, ListenerModelSeries listener) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        listeners.add(listener);
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rs = statement.executeQuery("select * from catalog");
            while (rs.next()) titles.add(rs.getString("title"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
            return titles;
        }

    }

    public void saveInfo(String title, String extract, OperationType operationType) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");

            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("replace into catalog values(null, '" + title + "', '" + extract + "', 1)");
            notifyListenersSuccess(operationType);
        } catch (SQLException e) {
            System.err.println("Error saving " + e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }

    }

    public void deleteEntry(String title) {

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("DELETE FROM catalog WHERE title = '" + title + "'");

        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println("Get title error " + e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }

        notifyListenersSuccess(OperationType.DELETE);
    }

    private void initListenersConfiguration() {
        listeners = new HashMap<>();
        for (OperationType type : OperationType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    private void notifyListenersSuccess(OperationType operationType) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        for (ListenerModelSeries listener : listeners) {
            listener.hasFinishedOperation();
        }
    }


    // ????
    public static String getExtract(String title) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:./dictionary.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'");
            rs.next();
            return rs.getString("extract");
        } catch (SQLException e) {
            System.err.println("Get title error " + e.getMessage());
        }finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e);
            }
        }
        return null;
    }
}
