package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.event.EventModelSeriesManager;
import dyds.tvseriesinfo.model.database.event.ListenerModelSeries;

public abstract class SeriesCRUD {
    protected EventModelSeriesManager eventModelSeriesManager;

    public SeriesCRUD() {
        eventModelSeriesManager = EventModelSeriesManager.getInstance();
    }

    public void addListener(OperationType operationType, ListenerModelSeries listener) {
        eventModelSeriesManager.subscribeListener(operationType, listener);
    }

    protected void notifyListenersSuccess(OperationType operationType) {
        eventModelSeriesManager.notifyListenersSuccess(operationType);
    }
}
