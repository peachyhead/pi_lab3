package stoplight;

import property.ReactiveProperty;
import java.beans.PropertyChangeListener;

public class StoplightFSM {
    
    private final StoplightFSMSettings settings;
    private final ReactiveProperty<StoplightState> currentStateProperty = new ReactiveProperty<>();
    
    public StoplightFSM(StoplightFSMSettings settings) {
        this.settings = settings;
    }
    
    public void setDisabled(){
        currentStateProperty.set(new StoplightState(settings.getDefaultData()));
    }
    
    public void update(long tick) {
        if (currentStateProperty.getValue() == null)
            currentStateProperty.set(new StoplightState(settings.getFirst()));
        
        var currentState = currentStateProperty.getValue();
        currentState.update(tick);
        if (!currentState.isEnd()) return;
        var newState = new StoplightState(settings.getFirst(currentState.getData()));
        currentStateProperty.set(newState);
    }
    
    public void subscribe(PropertyChangeListener listener) {
        currentStateProperty.subscribe(listener);
    }
}
