package stoplight;

import lombok.Getter;

import java.awt.*;
import java.text.MessageFormat;

public class StoplightStateData {
    
    @Getter private final int duration;
    @Getter private final int key;
    @Getter private final Color color;
    @Getter private final StoplightVisualType type;

    public StoplightStateData(int duration, Color color, 
                              int key, StoplightVisualType type) {
        this.duration = duration;
        this.color = color;
        this.key = key;
        this.type = type;
    }
    
    @Override
    public String toString() {
        return MessageFormat.format("Place: {0}\n" +
                "State: {1}", key, getVisual());
    }
    
    private String getVisual() {
        if (color == Color.red)
            return "Wait";

        if (color == Color.green)
            return type == StoplightVisualType.Blink
                    ? "Go faster"
                    : "Go";
        if (color == Color.yellow)
            return type == StoplightVisualType.Blink
                    ? "Disabled"
                    : "Prepare";
        return "Error!";
    }
}
