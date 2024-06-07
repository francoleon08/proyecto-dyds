package unit_tests.model.series;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDDeleter;
import dyds.tvseriesinfo.model.database.repository.crudSQL.SQLDelete;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class SeriesCRUDDeleterTest {
    private static ModelSeriesCRUDDeleter modelSeriesCRUDDeleter;

    @BeforeClass
    public static void setUp() throws Exception {
        modelSeriesCRUDDeleter = ModelSeriesCRUDDeleter.getInstance();
    }

    @Test
    public void testNotifiedListenerDeleteSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDDeleter.addListener(OperationType.DELETE_SERIES, () -> isNotified.set(true));
        try (MockedStatic<SQLDelete> ignored = mockStatic(SQLDelete.class)) {
            modelSeriesCRUDDeleter.deleteSeriesByTitle("title");
        }
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedListenerLoadLocalSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDDeleter.addListener(OperationType.LOAD_LOCAL_SERIES_AFTER_CHANGE, () -> isNotified.set(true));
        try (MockedStatic<SQLDelete> ignored = mockStatic(SQLDelete.class)) {
            modelSeriesCRUDDeleter.deleteSeriesByTitle("title");
        }
        assertTrue(isNotified.get());
    }
}