package stoplight;

import java.beans.PropertyChangeListener;

public class StoplightModel {
    
    private Thread updateThread;
    private final StoplightFSM stateMachine;
    private long cachedTime;
    
    public StoplightModel(StoplightFSMSettings settings) {
        this.stateMachine = new StoplightFSM(settings);
    }
    
    public void enable() {
        updateThread = new Thread(() -> {
            while (true) {
                var currentTime = System.currentTimeMillis();
                var delta = currentTime - cachedTime;
                cachedTime = currentTime;
                stateMachine.update(delta % 1000);
            }
        });
        updateThread.start();
        stateMachine.setEnabled();
    }
    
    public void disable() {
        if (updateThread != null)
            updateThread.interrupt();
        stateMachine.setDisabled();
    }
    
    public void subscribe(PropertyChangeListener listener) {
        stateMachine.subscribe(listener);
    }
}
