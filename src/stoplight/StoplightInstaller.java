package stoplight;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StoplightInstaller {

    private static final Font regularFont = new Font("Tahoma", Font.PLAIN, 16);
    private static Timer textUpdate;

    public static void install(JFrame frame) {
        
        var settings = getSettings();
        var model = new StoplightModel(settings);
        var presenter = new StoplightPresenter(model);
        
        var consts = new GridBagConstraints();
        consts.fill = GridBagConstraints.BOTH;
        consts.insets = new Insets(5, 5, 5, 5);
        consts.weightx = 0.8;
        consts.weighty = 1;
        consts.anchor  = GridBagConstraints.CENTER;
        frame.getContentPane().add(presenter, consts);

        var infoPanel = setupInfoPanel(model);
        consts.gridx = 1;
        consts.weightx = 1;
        frame.getContentPane().add(infoPanel, consts);
        
        frame.revalidate();
        presenter.initialize();
    }

    private static JPanel setupInfoPanel(StoplightModel model) {
        var infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        var consts = new GridBagConstraints();
        consts.fill = GridBagConstraints.VERTICAL;
        consts.insets = new Insets(5, 5, 5, 5);
        consts.weightx = 1;
        consts.gridy = 0;
        consts.gridx = 0;

        var stateInfo = new JLabel("State info:");
        stateInfo.setFont(regularFont);
        stateInfo.setPreferredSize(new Dimension(300, 30));
        infoPanel.add(stateInfo, consts);

        consts.gridy += 1;
        var remainingInfo = new JLabel("Remains:");
        remainingInfo.setFont(regularFont);
        remainingInfo.setPreferredSize(new Dimension(300, 30));
        infoPanel.add(remainingInfo, consts);
        
        consts.gridy += 1;
        var toggle = new JToggleButton("Enable stoplight");
        toggle.addActionListener(evt -> {
            if (toggle.isSelected())
                model.enable();
            else
                model.disable(); 
            
            toggle.setText(toggle.isSelected() ? "Disable stoplight" : "Enable stoplight");
        });
        infoPanel.add(toggle, consts);
        
        model.subscribe(evt -> {
            var state = (StoplightState) evt.getNewValue();
            if (textUpdate != null)
                textUpdate.stop();
            textUpdate = new Timer(1, ignored -> {
                stateInfo.setText("State: " + state.getData().toString());
                remainingInfo.setText("Remaining: " + state.getRemaining());
            });
            textUpdate.start();
        });
        
        return infoPanel;
    }

    private static StoplightFSMSettings getSettings() {
        return new StoplightFSMSettings(
                new StoplightStateData(0, Color.yellow, 2, StoplightVisualType.Blink),
                new StoplightStateData[] {
                        new StoplightStateData(500, Color.red, 1, StoplightVisualType.Simple),
                        new StoplightStateData(500, Color.yellow, 2, StoplightVisualType.Simple),
                        new StoplightStateData(200, Color.green, 3, StoplightVisualType.Simple),
                        new StoplightStateData(200, Color.green, 3, StoplightVisualType.Blink),
                        new StoplightStateData(500, Color.yellow, 2, StoplightVisualType.Simple)
                }
        );
    }
}