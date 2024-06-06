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

    public Presenter createPresenterSavePuntuaction() {
        return new PresenterSavePuntuaction(viewFactory.getViewPanelSearch(), viewFactory.getViewPanelPuntuaction(), modelFactory.getRatedSeriesCRUDSaver());
    }

    public Presenter createPresenterGetterSeries() {
        return new PresenterGetterSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDGetter());
    }

    public Presenter createPresenterLoadLocalSeries() {
        return new PresenterLoadLocalSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDGetter());
    }

    public Presenter createPresenterDeleteSeries() {
        return new PresenterDeleteSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDDeleter());
    }

    public Presenter createPresenterSaveChangesSeries() {
        return new PresenterSaveChangesSeries(viewFactory.getViewPanelStorage(), modelFactory.getSeriesCRUDSaver());
    }

    public Presenter createPresenterSaveSeries() {
        return new PresenterSaveSeries(viewFactory.getViewPanelSearch(), modelFactory.getSeriesCRUDSaver());
    }

    public Presenter createPresenterSearchSeries() {
        return new PresenterSearchSeries(viewFactory.getViewPanelSearch(), modelFactory.getModelWikipediaAPI());
    }

    public Presenter createPresenterGetterRatedSeries() {
        return new PresenterSearchRatingSeries(viewFactory.getGeneralView(), modelFactory.getModelWikipediaAPI());
    }

    public Presenter createPresenterLoadLocalRatedSeries() {
        return new PresenterLoadLocalRatedSeries(viewFactory.getViewPanelPuntuaction(), modelFactory.getRatedSeriesCRUDGetter());
    }

    public Presenter createPresenterOpenHyperLink() {
        return new PresenterOpenHyperLink(viewFactory.getViewPanelSearch());
    }
}
