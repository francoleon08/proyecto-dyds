package unit_tests.model.ratedSeries;

import dyds.tvseriesinfo.model.database.SQLmanager.crud.SQLSelectManager;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.ratedSeries.RatedModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.entities.RatedSeries;
import dyds.tvseriesinfo.model.exceptions.SearchRatedSeriesException;

import org.junit.Test;
import org.junit.Before;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class ModelRatedSeriesCRUDGetterTest {
    private static RatedModelSeriesCRUDGetter modelRatedSeriesCRUDGetter;
    private static ArrayList<RatedSeries> ratedSeriesTest;

    @Before
    public void setUp() throws Exception {
        modelRatedSeriesCRUDGetter = RatedModelSeriesCRUDGetter.getInstance();
        loadArrayListRatedSeries();
    }

    private static void loadArrayListRatedSeries() {
        ratedSeriesTest = new ArrayList<>();
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("The 100 (TV series)")
                .rating(3)
                .dateModified("")
                .timeModified("")
                .build()
        );
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("Batman (TV series)")
                .rating(5)
                .dateModified("")
                .timeModified("")
                .build()
        );
        ratedSeriesTest.add(RatedSeries.builder()
                .pageID("123")
                .title("Monk (TV series)")
                .rating(2)
                .dateModified("")
                .timeModified("")
                .build()
        );
    }

    @Test
    public void testNotifiedListenerGetSeries() throws Exception {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelRatedSeriesCRUDGetter.addListener(OperationType.LOAD_RATED_SERIES, () -> isNotified.set(true));
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            when(SQLSelectManager.getRatedSeries()).thenReturn(ratedSeriesTest);
            modelRatedSeriesCRUDGetter.getRatedSeries();
        }
        assertTrue(isNotified.get());
    }

    @Test
    public void testGetterRatedSeriesOrderByRating() throws SearchRatedSeriesException {
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            when(SQLSelectManager.getRatedSeries()).thenReturn(ratedSeriesTest);
            modelRatedSeriesCRUDGetter.getRatedSeries();
        }
        ArrayList<RatedSeries> ratedSeries = modelRatedSeriesCRUDGetter.getLastRatedSeries();
        assertEquals(ratedSeries.get(0), ratedSeriesTest.get(2));
        assertEquals(ratedSeries.get(1), ratedSeriesTest.get(0));
        assertEquals(ratedSeries.get(2), ratedSeriesTest.get(1));
    }

    @Test
    public void testSizeGetterRatedSeries() throws SearchRatedSeriesException {
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            when(SQLSelectManager.getRatedSeries()).thenReturn(ratedSeriesTest);
            modelRatedSeriesCRUDGetter.getRatedSeries();
        }
        ArrayList<RatedSeries> ratedSeries = modelRatedSeriesCRUDGetter.getLastRatedSeries();
        assertEquals(ratedSeries.size(), ratedSeriesTest.size());
    }
}