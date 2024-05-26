package dyds.tvseriesinfo.model.database.crud;

import dyds.tvseriesinfo.model.database.ListenerModelSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class SeriesCRUD {
    protected HashMap<OperationType, List<ListenerModelSeries>> listeners;

    public SeriesCRUD() {
        initListenersConfiguration();
    }

    private void initListenersConfiguration() {
        listeners = new HashMap<>();
        for (OperationType type : OperationType.values()) {
            listeners.put(type, new ArrayList<>());
        }
    }

    public void addListener(OperationType operationType, ListenerModelSeries listener) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        listeners.add(listener);
    }

    protected void notifyListenersSuccess(OperationType operationType) {
        List<ListenerModelSeries> listeners = this.listeners.get(operationType);
        for (ListenerModelSeries listener : listeners) {
            listener.hasFinishedOperation();
        }
    }
}
