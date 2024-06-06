package integration_test.storage_view;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.model.database.SQLmanager.SQLCRUD;
import dyds.tvseriesinfo.model.database.crud.series.ModelSeriesCRUDGetter;
import dyds.tvseriesinfo.presenter.Presenter;
import dyds.tvseriesinfo.presenter.PresenterFactory;
import dyds.tvseriesinfo.view.ViewFactory;
import dyds.tvseriesinfo.view.tabbedPane.ViewPanelStorage;
import org.junit.Before;
import org.junit.Test;
import stubs.StubSQLCRUD;

import static org.junit.Assert.assertEquals;

public class PresenterGetterSeriesTest {
    private static final ViewFactory viewFactory = new ViewFactory();
    private static final ModelFactory modelFactory = new ModelFactory();
    private static final PresenterFactory presenterFactory = new PresenterFactory(viewFactory, modelFactory);

    private Presenter presenterGetterSeries;
    private ViewPanelStorage viewPanelStorage;
    private ModelSeriesCRUDGetter modelSeriesCRUDGetter;
    private SQLCRUD sqlCRUDStub;

    @Before
    public void setUp() {
        sqlCRUDStub = new StubSQLCRUD();
        modelSeriesCRUDGetter = modelFactory.getSeriesCRUDGetter();
        modelSeriesCRUDGetter.setSqlCRUD(sqlCRUDStub);
        viewPanelStorage = viewFactory.getViewPanelStorage();
        viewPanelStorage.setActiveMessageDialog(false);
        presenterGetterSeries = presenterFactory.createPresenterGetterSeries();
        presenterFactory.createPresenterLoadLocalSeries();
    }

    @Test
    public void testLoadSeriesSelectItemComboBox() {
        viewPanelStorage.getSeriesComboBox().setSelectedIndex(0);
        presenterGetterSeries.onEvent();
        String expected = "<html> <head> </head> <body> <h1> Monk (TV series) </h1> <p> <i><b>Monk</b></i> is an American comedy-drama detective television series that originally ran on the USA Network from July 12, 2002, to December 4, 2009, with 125 episodes broadcast over eight seasons. The series follows Adrian Monk (Tony Shalhoub), a private detective with obsessive&#8211;compulsive disorder and multiple phobias, and his assistants Sharona Fleming (Bitty Schram) and Natalie Teeger (Traylor Howard). Monk works with the San Francisco Police Department in solving unconventional cases while investigating his wife`s unsolved murder. The show also explores the personal lives and struggles of the main characters. </p> <p> First envisioned by ABC as an Inspector Clouseau-type police show, the series` premise of a detective with obsessive&#8211;compulsive disorder originated with David Hoberman in 1998, while Andy Breckman, who is credited as creator, wrote the pilot episode by taking inspiration from Sherlock Holmes. <i>Monk</i> went through two years of development hell due to difficulties finding an actor for the main role. After being handed off to the USA Network and casting Shalhoub, the series` pilot was shot in Vancouver, British Columbia, in 2001. Subsequent episodes of the first season were filmed in Toronto, Ontario, while the remainder of the series was primarily shot in Los Angeles, California. </p> <p> <i>Monk</i> received critical acclaim and awards throughout its run, including eight Emmy Awards, one Golden Globe Award, and two Screen Actors Guild Awards. The two-part series finale aired on November 27 and December 4, 2009. The final episode held the record for the most-watched scripted cable television drama from 2009 to 2012 (broken by <i>The Walking Dead</i>) with 9.4 million viewers. </p> <p> A follow-up film, <i>Mr. Monk`s Last Case: A Monk Movie</i>, premiered on Peacock on December 8, 2023, with a script written by Breckman and the main cast reprising their roles from the series. </p> <a href=\"https://en.wikipedia.org/wiki/Monk_(TV_series)\">OPEN IN BROWSER!</a> </body> </html> ";
        String actual = viewPanelStorage.getDetailsSeries().replaceAll("\\s+", " ");
        assertEquals(expected, actual);
    }
}
