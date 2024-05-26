package stoplight;

import java.beans.PropertyChangeListener;
import java.util.Timer;
import java.util.TimerTask;

public class StoplightModel {
    
    private final Timer timer = new Timer();
    private final StoplightFSM stateMachine;
    
    public StoplightModel(StoplightFSMSettings settings) {
        this.stateMachine = new StoplightFSM(settings);
    }
    
    public void enable() {
        var task = new TimerTask() {
            @Override
            public void run() {
                stateMachine.update();
            }
        };
        timer.schedule(task, 0, 10);
        stateMachine.setEnabled();
    }
    
    public void disable() {
        timer.purge();
        stateMachine.setDisabled();
    }
    
    public void subscribe(PropertyChangeListener listener) {
        stateMachine.subscribe(listener);
    }
}
