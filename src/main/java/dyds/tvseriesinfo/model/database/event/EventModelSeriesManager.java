package dyds.tvseriesinfo.model.database.event;

import dyds.tvseriesinfo.model.database.crud.OperationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventModelSeriesManager {
    private static EventModelSeriesManager instance;
    private HashMap<OperationType, List<ListenerModelSeries>> listeners;

    private EventModelSeriesManager() {
        initListeners();
    }

    public static EventModelSeriesManager getInstance() {
        if (instance == null) {
            instance = new EventModelSeriesManager();
        }
        return instance;
    }

    private void initListeners() {
        listeners = new HashMap<>();
        for (OperationType operationType : OperationType.values()) {
            listeners.put(operationType, new ArrayList<>());
        }
    }

    public void subscribeListener(OperationType operationType, ListenerModelSeries listener) {
        listeners.get(operationType).add(listener);
    }

    public void unsubscribeListener(OperationType operationType, ListenerModelSeries listener) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        listeners.remove(listener);
    }

    public void notifyListenersSuccess(OperationType operationType) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        for (ListenerModelSeries listener : listeners) {
            listener.hasFinishedOperationSucces();
        }
    }
}
