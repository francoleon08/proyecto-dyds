package unit_tests.model.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class ModelSeriesCRUDSaverTest {
    private static ModelSeriesCRUDSaver seriesCRUDSaver;

    @BeforeClass
    public static void setUp() throws Exception {
        seriesCRUDSaver = ModelSeriesCRUDSaver.getInstance();
        mockStatic(SQLInsert.class);
        doNothing().when(SQLInsert.class);
        SQLInsert.saveSeries(anyString(), anyString());
    }

    @Test
    public void testNotifiedListenerSaveSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.SAVE_SERIES, () -> isNotified.set(true));
        seriesCRUDSaver.saveSeries("title", "extract");
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedListenerLoadLocalSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE, () -> isNotified.set(true));
        seriesCRUDSaver.saveSeries("title", "extract");
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedListenerSaveChangeSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.SAVE_CHANGES, () -> isNotified.set(true));
        seriesCRUDSaver.saveChangesSeries("title", "extract");
        assertTrue(isNotified.get());
    }
}