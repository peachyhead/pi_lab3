package property;

import lombok.Getter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

public class ReactiveProperty<T> {
    @Getter
    private T value;
    
    private final Action onChange = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            
        }
    };
    
    public void set (T value) {
        this.value = value;
        onChange.putValue("value", value);
    }
    
    public void subscribe(PropertyChangeListener listener) {
        onChange.addPropertyChangeListener(listener);
    }
}
