package azkviz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.EmptyStackException;

public class HraciPole extends JFrame {

    private final JPanel mainPanel;
    private final JPanel pyramidPanel;

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
        JButton player2 = new JButton("Hráč 2");
        player2.setAlignmentX(Component.RIGHT_ALIGNMENT);

        playersPanel.add(player1);
        playersPanel.add(Box.createHorizontalGlue());
        playersPanel.add(player2);

        mainPanel.add(playersPanel);

        pyramidPanel = new JPanel();

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
            JButton button = new JButton(String.valueOf(index));
            button.setFont(button.getFont().deriveFont(Font.BOLD));
            rowPanel.add(button);
            index++;
        }

        return rowPanel;
    }

    private void adjustButtonSize() {
        int buttonHeight = (mainPanel.getHeight() - 200 ) / 7;
        int buttonWidth = (mainPanel.getWidth() - 400 ) / 8;
        float buttonTextSize = Math.max(pyramidPanel.getWidth(), pyramidPanel.getHeight()) / 21.6f;
        for (Component row : pyramidPanel.getComponents()) {
            if (!(row instanceof JPanel rowPanel)) continue;
            for (Component comp : rowPanel.getComponents()) {
                if (!(comp instanceof JButton button)) continue;
                if(button.getText().contains("Hráč")) continue;

                button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
                button.setFont(button.getFont().deriveFont(buttonTextSize));
                rowPanel.revalidate();
                rowPanel.repaint();

            }
        }
    }


}
