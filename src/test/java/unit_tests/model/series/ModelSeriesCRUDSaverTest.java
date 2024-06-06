package unit_tests.model.series;

import dyds.tvseriesinfo.model.database.SQLmanager.crud.SQLInsert;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDSaver;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ModelSeriesCRUDSaverTest {
    private static ModelSeriesCRUDSaver seriesCRUDSaver;

    @Before
    public void setUp() throws Exception {
        seriesCRUDSaver = ModelSeriesCRUDSaver.getInstance();
    }

    @Test
    public void testNotifiedListenerSaveSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.SAVE_SERIES, () -> isNotified.set(true));
        try (MockedStatic<SQLInsert> ingnored = mockStatic(SQLInsert.class)) {
            seriesCRUDSaver.saveSeries("title", "extract");
        }
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedListenerLoadLocalSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE, () -> isNotified.set(true));
        try (MockedStatic<SQLInsert> ingnored = mockStatic(SQLInsert.class)) {
            seriesCRUDSaver.saveSeries("title", "extract");
        }
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedListenerSaveChangeSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        seriesCRUDSaver.addListener(OperationType.SAVE_CHANGES, () -> isNotified.set(true));
        try (MockedStatic<SQLInsert> ingnored = mockStatic(SQLInsert.class)) {
            seriesCRUDSaver.saveChangesSeries("title", "extract");
        }
        assertTrue(isNotified.get());
    }
}