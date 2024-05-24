package dyds.tvseriesinfo.model.database;

import java.util.ArrayList;

public interface SeriesDAO {

    public ArrayList<String> getTitles();
    public void saveInfo(String title, String extract, OperationType operationType);
    public void deleteEntry(String title);
}
