package stoplight;

import lombok.Getter;

public class StoplightState {
    
    @Getter private final StoplightStateData data;
    @Getter private int accumulated;

    public StoplightState(StoplightStateData data) {
        this.data = data;
    }

    public void update () {
        accumulated += 1;
    }
    
    public boolean isEnd() {
        return accumulated >= data.getDuration();
    }
    
    public int getRemaining() {
        return data.getDuration() - accumulated;
    }
}
