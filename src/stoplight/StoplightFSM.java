package stoplight;

import property.ReactiveProperty;
import java.beans.PropertyChangeListener;

public class StoplightFSM {
    
    private boolean isDisabled;
    
    private final StoplightFSMSettings settings;
    private final ReactiveProperty<StoplightState> currentStateProperty = new ReactiveProperty<>();
    
    public StoplightFSM(StoplightFSMSettings settings) {
        this.settings = settings;
        currentStateProperty.set(new StoplightState(settings.getNext()));
    }
    
    public void setEnabled() {
        isDisabled = false;
    }
    
    public void setDisabled(){
        currentStateProperty.set(new StoplightState(settings.getDefaultData()));
        isDisabled = true;
    }
    
    public void update () {
        if (isDisabled) return;
        
        var currentState = currentStateProperty.getValue();
        currentState.update();
        if (!currentState.isEnd()) return;
        var newState = new StoplightState(settings.getNext(currentState.getData()));
        currentStateProperty.set(newState);
    }
    
    public void subscribe (PropertyChangeListener listener) {
        currentStateProperty.subscribe(listener);
    }
}
