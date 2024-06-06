package unit_tests.model.series;

import dyds.tvseriesinfo.model.database.SQLmanager.crud.SQLSelectManager;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SeriesCRUDGetterTest {
    private static ModelSeriesCRUDGetter modelSeriesCRUDGetter;
    private static ArrayList<String> lastTitlesSeriesTest;

    @Before
    public void setUp() throws Exception {
        modelSeriesCRUDGetter = ModelSeriesCRUDGetter.getInstance();
        loadArrayListTitles();
    }

    private static void loadArrayListTitles() {
        lastTitlesSeriesTest = new ArrayList<>();
        lastTitlesSeriesTest.add("The 100 (TV series)");
        lastTitlesSeriesTest.add("Rubí (2020 TV series)");
        lastTitlesSeriesTest.add("24 (TV series)");
        lastTitlesSeriesTest.add("Monk (TV series)");
    }

    @Test
    public void testGetLastSeries() throws SeriesSearchException {
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            mock.when(SQLSelectManager::getTitlesSeries).thenReturn(lastTitlesSeriesTest);
            mock.when(() -> SQLSelectManager.getExtractSeriesByTitle(anyString())).thenReturn("extract");
            modelSeriesCRUDGetter.getTitlesSeries();
        }
        ArrayList<String> lastTitlesSeries = modelSeriesCRUDGetter.getLastTitlesSeries();
        assertEquals(lastTitlesSeriesTest, lastTitlesSeries);
    }

    @Test
    public void testGetSeriesByTitle() throws SeriesSearchException {
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            mock.when(SQLSelectManager::getTitlesSeries).thenReturn(lastTitlesSeriesTest);
            mock.when(() -> SQLSelectManager.getExtractSeriesByTitle(anyString())).thenReturn("extract");
            modelSeriesCRUDGetter.getExtractSeriesByTitle("24 (TV series)");
        }
        String actual = modelSeriesCRUDGetter.getLastSeriesExtactByTitle();
        String expected = "extract";
        assertEquals(expected, actual);
    }

    @Test
    public void testNotifiedGetLastSeries() throws SeriesSearchException {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDGetter.addListener(OperationType.LOAD_LOCAL_SERIES, () -> isNotified.set(true));
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            mock.when(SQLSelectManager::getTitlesSeries).thenReturn(lastTitlesSeriesTest);
            mock.when(() -> SQLSelectManager.getExtractSeriesByTitle(anyString())).thenReturn("extract");
            modelSeriesCRUDGetter.getTitlesSeries();
        }
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedGetSeriesByTitle() throws SeriesSearchException {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDGetter.addListener(OperationType.GET_SERIES, () -> isNotified.set(true));
        try (MockedStatic<SQLSelectManager> mock = mockStatic(SQLSelectManager.class)) {
            mock.when(SQLSelectManager::getTitlesSeries).thenReturn(lastTitlesSeriesTest);
            mock.when(() -> SQLSelectManager.getExtractSeriesByTitle(anyString())).thenReturn("extract");
            modelSeriesCRUDGetter.getExtractSeriesByTitle("title");
        }
        assertTrue(isNotified.get());
    }
}