import stoplight.StoplightInstaller;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        var frame = setupFrame();
        StoplightInstaller.install(frame);
    }

    private static JFrame setupFrame() {
        var mainFrame = new JFrame();
        var parentPane = mainFrame.getContentPane();
        parentPane.removeAll();
        parentPane.setVisible(true);
        
        mainFrame.setLayout(new GridBagLayout());
        mainFrame.setTitle("It works?");
        mainFrame.setSize(1200, 800);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        var timer = new Timer(1, e -> mainFrame.repaint());
        timer.start();
        return mainFrame;
    }
}