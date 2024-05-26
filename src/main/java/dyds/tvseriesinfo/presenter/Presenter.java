package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.ListenerModelSeries;

public interface Presenter extends ListenerModelSeries {

    void onEvent();
}
