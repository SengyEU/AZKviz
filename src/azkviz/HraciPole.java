package azkviz;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HraciPole extends JFrame {

    public HraciPole(){
        setTitle("Az Kvíz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 450);
        getContentPane().setBackground(Color.decode("#9cd2f1"));
        setExtendedState(MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        /*

        int buttonCount = 1;
        int slot = 0;

        List<Integer> slots = Arrays.asList(4, 11, 12, 17, 18, 19, 24, 25, 26, 27, 30, 31, 32, 33, 34, 35, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49);

        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 7; j++) {
                slot++;
                JButton button = new JButton("" + buttonCount);
                if(slots.contains(slot)){
                    buttonCount++;
                    panel.add(button);
                }
                else {
                    panel.add(new JLabel());
                }
            }
        }

        */

        panel.add(createRowPanel(1));
        panel.add(createRowPanel(2));
        panel.add(createRowPanel(3));
        panel.add(createRowPanel(4));
        panel.add(createRowPanel(5));
        panel.add(createRowPanel(6));

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createRowPanel(int numComponents) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 0)); // 1 row, 0 columns

        // Add components to the row panel
        for (int i = 0; i < numComponents; i++) {
            JButton button = new JButton("Button " + (i + 1));
            rowPanel.add(button);
        }

        rowPanel.setPreferredSize(new Dimension(100, rowPanel.getPreferredSize().height));

        return rowPanel;
    }


}
