package unit_tests.model.ratedSeries;

import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDSaver;
import dyds.tvseriesinfo.model.database.repository.crudSQL.SQLInsert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;

public class ModelRatedSeriesCRUDSaverTest {
    private static RatedModelSeriesCRUDSaver modelRatedSeriesCRUDSaver;

    @Before
    public void setUp() throws Exception {
        modelRatedSeriesCRUDSaver = RatedModelSeriesCRUDSaver.getInstance();
    }

    @Test
    public void testNotifiedListenerSaveSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelRatedSeriesCRUDSaver.addListener(OperationType.SAVE_RATED, () -> isNotified.set(true));
        try (MockedStatic<SQLInsert> ignored = mockStatic(SQLInsert.class)) {
            modelRatedSeriesCRUDSaver.saveRatedSeries("title", 5);
        }
        assertTrue(isNotified.get());
    }
}