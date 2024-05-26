package stoplight;

import property.ReactiveProperty;
import java.beans.PropertyChangeListener;

public class StoplightFSM {
    
    private boolean isDisabled;
    
    private final StoplightFSMSettings settings;
    private final ReactiveProperty<StoplightState> currentStateProperty = new ReactiveProperty<>();
    
    public StoplightFSM(StoplightFSMSettings settings) {
        this.settings = settings;
    }
    
    public void setEnabled() {
        isDisabled = false;
    }
    
    public void setDisabled(){
        currentStateProperty.set(new StoplightState(settings.getDefaultData()));
        isDisabled = true;
    }
    
    public void update(long tick) {
        if (isDisabled) return;
        if (currentStateProperty.getValue() == null)
            currentStateProperty.set(new StoplightState(settings.getFirst()));
        
        var currentState = currentStateProperty.getValue();
        currentState.update(tick);
        if (!currentState.isEnd()) return;
        var newState = new StoplightState(settings.getFirst(currentState.getData()));
        currentStateProperty.set(newState);
    }
    
    public void subscribe (PropertyChangeListener listener) {
        currentStateProperty.subscribe(listener);
    }
}
