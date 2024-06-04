package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.presenter.rated.PresenterLoadLocalRatedSeries;
import dyds.tvseriesinfo.presenter.rated.PresenterSearchRatingSeries;
import dyds.tvseriesinfo.presenter.search.PresenterOpenHyperLink;
import dyds.tvseriesinfo.presenter.search.PresenterSavePuntuaction;
import dyds.tvseriesinfo.presenter.search.PresenterSaveSeries;
import dyds.tvseriesinfo.presenter.search.PresenterSearchSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterDeleteSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterGetterSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterLoadLocalSeries;
import dyds.tvseriesinfo.presenter.storage.PresenterSaveChangesSeries;
import dyds.tvseriesinfo.view.ViewFactory;

public class PresenterFactory {
    private final ViewFactory viewFactory;
    private final ModelFactory modelFactory;

    public PresenterFactory(ViewFactory viewFactory, ModelFactory modelFactory) {
        this.viewFactory = viewFactory;
        this.modelFactory = modelFactory;
    }

    public void initPresenters() {
        createPresenterSavePuntuaction();
        createPresenterGetterSeries();
        createPresenterLoadLocalSeries();
        createPresenterDeleteSeries();
        createPresenterSaveChangesSeries();
        createPresenterSaveSeries();
        createPresenterSearchSeries();
        createPresenterGetterRatedSeries();
        createPresenterLoadLocalRatedSeries();
        createPresenterOpenHyperLink();
    }

    private void createPresenterSavePuntuaction() {
        new PresenterSavePuntuaction(viewFactory.getViewPanelSearch(), viewFactory.getViewPanelPuntuaction(), modelFactory.getRatedSeriesCRUDSaver());
    }

    private void createPresenterGetterSeries() {
        new PresenterGetterSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDGetter());
    }

    private void createPresenterLoadLocalSeries() {
        new PresenterLoadLocalSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDGetter());
    }

    private void createPresenterDeleteSeries() {
        new PresenterDeleteSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDDeleter());
    }

    private void createPresenterSaveChangesSeries() {
        new PresenterSaveChangesSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDSaver());
    }

    private void createPresenterSaveSeries() {
        new PresenterSaveSeries(viewFactory.getViewPanelSearch(), modelFactory.getSeriesCRUDSaver());
    }

    private void createPresenterSearchSeries() {
        new PresenterSearchSeries(viewFactory.getViewPanelSearch(), modelFactory.getModelWikipediaAPI());
    }

    private void createPresenterGetterRatedSeries() {
        new PresenterSearchRatingSeries(viewFactory.getGeneralView(), modelFactory.getModelWikipediaAPI());
    }

    private void createPresenterLoadLocalRatedSeries() {
        new PresenterLoadLocalRatedSeries(viewFactory.getViewPanelPuntuaction(), modelFactory.getRatedSeriesCRUDGetter());
    }

    private void createPresenterOpenHyperLink() {
        new PresenterOpenHyperLink(viewFactory.getViewPanelSearch());
    }
}
