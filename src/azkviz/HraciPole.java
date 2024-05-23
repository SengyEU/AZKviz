package azkviz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HraciPole extends JFrame {

    private JPanel mainPanel;

    private int index = 1;

    public HraciPole() {
        setTitle("Az Kvíz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1600, 900);
        setExtendedState(MAXIMIZED_BOTH);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#9cd2f1"));

        mainPanel.add(createRowPanel(1));
        mainPanel.add(createRowPanel(2));
        mainPanel.add(createRowPanel(3));
        mainPanel.add(createRowPanel(4));
        mainPanel.add(createRowPanel(5));
        mainPanel.add(createRowPanel(6));
        mainPanel.add(createRowPanel(7));

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustButtonSize();
            }
        });

        adjustButtonSize();
    }

    private JPanel createRowPanel(int numComponents) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
        rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        for (int i = 0; i < numComponents; i++) {
            JButton button = new JButton("Button " + index);
            rowPanel.add(button);
            index++;
        }

        return rowPanel;
    }

    private void adjustButtonSize() {
        int buttonHeight = getHeight() / 7;
        int buttonwidth = getWidth() / 8;
        for (Component row : mainPanel.getComponents()) {
            if (!(row instanceof JPanel rowPanel)) return;
            for (Component comp : rowPanel.getComponents()) {
                if (!(comp instanceof JButton button)) return;

                button.setPreferredSize(new Dimension(buttonwidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonwidth, buttonHeight));
                rowPanel.revalidate();
                rowPanel.repaint();

            }
        }
    }


}
