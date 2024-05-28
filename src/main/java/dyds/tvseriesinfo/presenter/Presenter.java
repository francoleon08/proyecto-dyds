package dyds.tvseriesinfo.presenter;

import dyds.tvseriesinfo.model.database.event.ListenerModelSeries;

public interface Presenter extends ListenerModelSeries {

    void onEvent();
}
