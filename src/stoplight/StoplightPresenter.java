package stoplight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StoplightPresenter extends JPanel {
    
    private static final Dimension size = new Dimension(300, 700);
    private static final Dimension lightSize = new Dimension(200, 200);
    private static final int blinkPeriod = 100;
    
    private final StoplightModel model;
    private final List<StoplightSingleView> lightViews = new ArrayList<>();

    public StoplightPresenter(StoplightModel model) {
        
        this.model = model;
        setBackground(Color.BLACK);
        setPreferredSize(size);
        setLayout(new GridBagLayout());
        
        var consts = new GridBagConstraints();
        consts.fill = GridBagConstraints.VERTICAL;
        consts.insets = new Insets(10, 0, 10, 10);
        
        for (int i = 1; i <= 3; i++) {
            var view = new StoplightSingleView(i, blinkPeriod, lightSize);
            lightViews.add(view);
            consts.gridy = i;
            add(view, consts);
            view.setVisible(true);
            revalidate();
        }
    }
    
    public void initialize() {
        model.subscribe(evt ->
        {
            var state = (StoplightState) evt.getNewValue();
            lightViews.forEach(view -> view.applyState(state));
        });
    }
}
