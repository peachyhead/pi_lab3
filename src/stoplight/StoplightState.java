package stoplight;

import lombok.Getter;

import java.time.Duration;

public class StoplightState {
    
    @Getter private final StoplightStateData data;
    @Getter private long accumulated;

    public StoplightState(StoplightStateData data) {
        this.data = data;
    }

    public void update(long tick) {
        accumulated += tick;
    }
    
    public boolean isEnd() {
        return getRemaining().isNegative();
    }
    
    public Duration getRemaining() {
        return data.getDuration().minus(Duration.ofMillis(accumulated));
    }
}
