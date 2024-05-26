package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.DatabaseConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SeriesCrudGetter extends SeriesCrudDatabase {

    public SeriesCrudGetter() {
        super();
    }

    public ArrayList<String> getTitlesSeries() {
        ArrayList<String> titles = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.createConnection();
            Statement statement = DatabaseConnectionManager.createStatement(connection);

            ResultSet rs = statement.executeQuery("select * from catalog");

            while (rs.next())
                titles.add(rs.getString("title"));
        } catch (SQLException e) {
            System.err.println("Get titles error " + e.getMessage());
        } finally {
            DatabaseConnectionManager.closeConnection(connection);
            return titles;
        }

    }

    public static String getDetailsSeriesByTitle(String title) {

        Connection connection = null;
        try {
            connection = DatabaseConnectionManager.createConnection();
            Statement statement = DatabaseConnectionManager.createStatement(connection);

            ResultSet rs = statement.executeQuery("select * from catalog WHERE title = '" + title + "'");
            rs.next();
            System.out.println(rs.getString("extract"));
            return rs.getString("extract");
        } catch (SQLException e) {
            System.err.println("Get title error " + e.getMessage());
        }finally {
            DatabaseConnectionManager.closeConnection(connection);
        }
        return null;
    }
}
