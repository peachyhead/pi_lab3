package stoplight;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class StoplightSingleView extends JPanel {
    
    private final static Color disabledColor = Color.darkGray;
    
    @Getter private final int key;
    private Timer blinkTimer;
    private Color currentColor;
    
    private final Dimension size;
    private final int blinkingPeriod;

    public StoplightSingleView(int key, int blinkingPeriod, Dimension size) {
        this.key = key;
        this.size = size;
        this.blinkingPeriod = blinkingPeriod;
        
        setPreferredSize(size);
        currentColor = disabledColor;
    }
    
    public void applyState(StoplightState state) {
        if (state.getData().getKey() != key) {
            currentColor = disabledColor;
            if (blinkTimer != null) blinkTimer.stop();
            return;
        }
        
        currentColor = state.getData().getColor();
        setVisual(state.getData());
    }
    
    public void setVisual (StoplightStateData data) {
        if (data.getType() != StoplightVisualType.Blink) return;
        if (blinkTimer != null) blinkTimer.stop();
        
        blinkTimer = new Timer(blinkingPeriod, value -> {
            currentColor = currentColor == disabledColor 
                    ? data.getColor() 
                    : disabledColor; 
        });
        blinkTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        var g2d = (Graphics2D) g.create();
        if (currentColor != null)
            g2d.setColor(currentColor);
        g2d.fillOval(0, 0, size.width, size.height);
        g2d.dispose();
    }
}
