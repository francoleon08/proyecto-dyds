package unit_tests.model.series;

import dyds.tvseriesinfo.model.database.SQLmanager.SQLSelectManager;
import dyds.tvseriesinfo.model.database.crud.OperationType;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.model.exceptions.SeriesSearchException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class SeriesCRUDGetterTest {
    private static ModelSeriesCRUDGetter modelSeriesCRUDGetter;
    private static ArrayList<String> lastTitlesSeriesTest;

    @BeforeClass
    public static void setUp() throws Exception {
        modelSeriesCRUDGetter = ModelSeriesCRUDGetter.getInstance();
        mockStatic(SQLSelectManager.class);
        loadArrayListTitles();
        when(SQLSelectManager.getTitlesSeries()).thenReturn(lastTitlesSeriesTest);
        when(SQLSelectManager.getExtractSeriesByTitle(anyString())).thenReturn("extract");
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
        modelSeriesCRUDGetter.getTitlesSeries();
        ArrayList<String> lastTitlesSeries = modelSeriesCRUDGetter.getLastTitlesSeries();
        assertEquals(lastTitlesSeriesTest, lastTitlesSeries);
    }

    @Test
    public void testGetSeriesByTitle() throws SeriesSearchException {
        modelSeriesCRUDGetter.getExtractSeriesByTitle("24 (TV series)");
        String actual = modelSeriesCRUDGetter.getLastSeriesExtactByTitle();
        String expected = "extract";
        assertEquals(expected, actual);
    }

    @Test
    public void testNotifiedGetLastSeries() throws SeriesSearchException {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDGetter.addListener(OperationType.LOAD_LOCAL_SERIES, () -> isNotified.set(true));
        modelSeriesCRUDGetter.getTitlesSeries();
        assertTrue(isNotified.get());
    }

    @Test
    public void testNotifiedGetSeriesByTitle() throws SeriesSearchException {
        AtomicBoolean isNotified = new AtomicBoolean(false);
        modelSeriesCRUDGetter.addListener(OperationType.GET_SERIES, () -> isNotified.set(true));
        modelSeriesCRUDGetter.getExtractSeriesByTitle("title");
        assertTrue(isNotified.get());
    }
}