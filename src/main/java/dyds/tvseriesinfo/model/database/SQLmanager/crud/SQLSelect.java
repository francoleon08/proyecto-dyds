package dyds.tvseriesinfo.model.database.SQLmanager.crud;


import dyds.tvseriesinfo.model.entities.RatedSeries;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLSelect {
    protected static final String SELECT_FROM_CATALOG = "select * from catalog";
    protected static final String SELECT_FROM_CATALOG_BY_TITLE = "select * from catalog WHERE title = ?";
    protected static final String SELECT_FROM_RATED_SERIES = "select * from rated_series";
    protected static final String SELECT_FROM_RATED_SERIES_BY_TITLE = "select * from rated_series WHERE  title = ?";
    protected static final String COLUMN_LABEL_EXTRACT = "extract";
    protected static final String ERROR_DATABASE_CONNECTION = "Database connection error.";

    protected static RatedSeries createRatedSeriesOfResultSet(ResultSet resultSet) throws SQLException {
        return RatedSeries.builder()
                .title(resultSet.getString("title"))
                .rating(resultSet.getInt("puntaction"))
                .dateModified(resultSet.getString("rating_date"))
                .timeModified(resultSet.getString("rating_time"))
                .build();
    }
}