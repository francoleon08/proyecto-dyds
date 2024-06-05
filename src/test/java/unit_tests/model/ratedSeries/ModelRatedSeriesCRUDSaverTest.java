package unit_tests.model.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDSaver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModelRatedSeriesCRUDSaverTest {
    private static RatedModelSeriesCRUDSaver modelRatedSeriesCRUDSaver;

    @BeforeClass
    public static void setUp() throws Exception {
        modelRatedSeriesCRUDSaver = RatedModelSeriesCRUDSaver.getInstance();
        mockStatic(SQLInsert.class);
        doNothing().when(SQLInsert.class);
        SQLInsert.savePuntuaction(anyString(), anyInt());
    }

    @Test
    public void testNotifiedListenerSaveSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelRatedSeriesCRUDSaver.addListener(OperationType.SAVE_RATED, () -> isNotified.set(true));
        modelRatedSeriesCRUDSaver.saveRatedSeries("title", 5);
        assertTrue(isNotified.get());
    }
}