package azkviz.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class HraciPole extends JFrame {

    private final JPanel mainPanel;

    private int index = 1;

    public HraciPole() {
        setTitle("Az Kvíz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1920, 1080);
        setExtendedState(MAXIMIZED_BOTH);

        mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.decode("#9cd2f1"));

        JPanel playersPanel = new JPanel();

        playersPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        playersPanel.setBackground(Color.decode("#9cd2f1"));

        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
        JButton player1 = new JButton("Hráč 1");
        player1.setAlignmentX(Component.LEFT_ALIGNMENT);
        player1.setBackground(Color.decode("#70e3e5"));

        JButton player2 = new JButton("Hráč 2");
        player2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        player2.setBackground(Color.decode("#f3a004"));

        playersPanel.add(player1);
        playersPanel.add(Box.createHorizontalGlue());
        playersPanel.add(player2);

        mainPanel.add(playersPanel);

        JPanel pyramidPanel = new JPanel();

        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        pyramidPanel.setBackground(Color.decode("#9cd2f1"));

        pyramidPanel.add(createRowPanel(1));
        pyramidPanel.add(createRowPanel(2));
        pyramidPanel.add(createRowPanel(3));
        pyramidPanel.add(createRowPanel(4));
        pyramidPanel.add(createRowPanel(5));
        pyramidPanel.add(createRowPanel(6));
        pyramidPanel.add(createRowPanel(7));

        pyramidPanel.setBorder(new EmptyBorder(0, 200, 200, 200));

        mainPanel.add(pyramidPanel);

        this.add(mainPanel);
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
            HexagonButton button = new HexagonButton(String.valueOf(index));
            button.setBackground(Color.decode("#9cd2f1"));
            button.setFont(button.getFont().deriveFont(Font.BOLD));
            rowPanel.add(button);
            index++;
        }

        return rowPanel;
    }

    private void adjustButtonSize() {
        int buttonSize = (mainPanel.getHeight() - 200) / 9;
        int buttonWidth = (mainPanel.getWidth() - 400) / 10;
        float mainPanelSize = Math.max(mainPanel.getWidth(), mainPanel.getHeight());

        for (Component row : mainPanel.getComponents()) {
            if (!(row instanceof JPanel rowPanel)) continue;

            for (Component comp : rowPanel.getComponents()) {
                if (comp instanceof JPanel childPanel) {
                    for (Component childComp : childPanel.getComponents()) {
                        if (childComp instanceof HexagonButton button) {
                            setButtonSizeAndFont(button, buttonSize, buttonSize, mainPanelSize / 40f);
                        }
                    }
                } else if (comp instanceof JButton button) {
                    setButtonSizeAndFont(button, buttonWidth, buttonSize, mainPanelSize / 60f);
                }
            }

            rowPanel.revalidate();
            rowPanel.repaint();
        }
    }

    private void setButtonSizeAndFont(JButton button, int width, int height, float fontSize) {
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        button.setFont(button.getFont().deriveFont(fontSize));
    }

}
