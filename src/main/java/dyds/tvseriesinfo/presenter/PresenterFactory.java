package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.ModelFactory;
import dyds.tvseriesinfo.presenter.search.*;
import dyds.tvseriesinfo.presenter.rated.*;
import dyds.tvseriesinfo.presenter.storage.*;
import dyds.tvseriesinfo.view.ViewFactory;

public class PresenterFactory {
    private ViewFactory viewFactory;
    private ModelFactory modelFactory;

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
