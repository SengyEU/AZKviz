package azkviz.gui;

import azkviz.logic.AzKviz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Třída reprezentující herní plochu kvízu Az Kvíz.
 */
public class HerniPlocha extends JFrame {

    private final JPanel mainPanel;
    private final static String[] letters = {"A", "B", "C", "Č", "D", "E", "F", "G", "H", "Ch", "I", "J", "K", "L", "M", "N", "O", "P", "R", "Ř", "S", "Š", "T", "U", "V", "W", "Z", "Ž"};

    public JButton player1;
    public HexagonButton player1icon;
    public JButton player2;
    public HexagonButton player2icon;

    /**
     * Konstruktor pro vytvoření instance herní plochy.
     *
     * @param azKviz Reference na instanci hry AzKviz.
     * @param finale True, pokud je hra v finále, jinak false.
     */
    public HerniPlocha(AzKviz azKviz, boolean finale) {

        setTitle("Az Kvíz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));

        mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(AzKviz.backgroundColor);

        JPanel pyramidPanel = new JPanel();

        pyramidPanel.setLayout(new BoxLayout(pyramidPanel, BoxLayout.Y_AXIS));
        pyramidPanel.setBackground(AzKviz.backgroundColor);

        int[][] buttonStates = azKviz.getButtonStates();

        int index = 1;

        for (int i = 0; i < buttonStates.length; i++) {
            int[] row = buttonStates[i];
            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
            rowPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            for (int j = 0; j < row.length; j++) {
                int col = row[j];
                if (col == 0) continue;

                String text = finale ? letters[index - 1] : String.valueOf(index);

                HerniTlacitko button = new HerniTlacitko(azKviz, this, text, text, i, j);
                button.setBackground(AzKviz.backgroundColor);
                button.setFont(button.getFont().deriveFont(Font.BOLD));
                rowPanel.add(button);
                index++;
            }

            pyramidPanel.add(rowPanel);
        }

        pyramidPanel.setBorder(new EmptyBorder(100, 200, 100, 200));

        mainPanel.add(pyramidPanel);

        JPanel playersPanel = new JPanel();

        playersPanel.setBorder(new EmptyBorder(0, 50, 200, 50));
        playersPanel.setBackground(AzKviz.backgroundColor);

        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.X_AXIS));
        player1 = new JButton("Hráč 1");
        player1.setAlignmentX(Component.LEFT_ALIGNMENT);
        player1.setBackground(AzKviz.player1Color);
        player1icon = new HexagonButton("", AzKviz.player1Color);
        player1icon.setBackground(AzKviz.backgroundColor);

        player2 = new JButton("Hráč 2");
        player2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        player2.setBackground(AzKviz.player2Color);
        player2icon = new HexagonButton("", AzKviz.grayColor);
        player2icon.setBackground(AzKviz.backgroundColor);

        playersPanel.add(player1);
        playersPanel.add(player1icon);
        playersPanel.add(Box.createHorizontalGlue());
        playersPanel.add(player2icon);
        playersPanel.add(player2);

        mainPanel.add(playersPanel);

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

    /**
     * Metoda pro přizpůsobení velikosti tlačítek v závislosti na velikosti okna.
     */
    private void adjustButtonSize() {
        int buttonSize = (mainPanel.getHeight() - 200) / 9;
        int buttonWidth = (mainPanel.getWidth() - 400) / 10;
        float mainPanelSize = getWidth();

        for (Component row : mainPanel.getComponents()) {
            if (!(row instanceof JPanel panel)) continue;

            for (Component comp : panel.getComponents()) {
                if (comp instanceof JPanel childPanel) {
                    for (Component childComp : childPanel.getComponents()) {
                        if (childComp instanceof JButton button) {
                            setButtonSizeAndFont(button, buttonSize, buttonSize, mainPanelSize / 40f);
                        }
                    }
                } else if (comp instanceof JButton button)
                {
                    if (button == player1icon || button == player2icon) {
                        setButtonSizeAndFont(button, (int) (buttonSize * 1.5), (int) (buttonSize * 1.5), mainPanelSize / 40f);
                    } else if (button == player1 || button == player2) {
                        setButtonSizeAndFont(button, (int) (buttonWidth * 1.5), (int) (buttonSize * 1.5), mainPanelSize / 60f);
                    } else {
                        setButtonSizeAndFont(button, buttonWidth * 2, buttonSize * 2, mainPanelSize / 60f);
                    }
                }
            }

            panel.revalidate();
            panel.repaint();
        }
    }

    /**
     * Metoda pro nastavení velikosti tlačítka a jeho písma.
     *
     * @param button Tlačítko, u kterého se má nastavit velikost a písmo.
     * @param width Šířka tlačítka.
     * @param height ýška tlačítka.
     * @param fontSize Velikost písma tlačítka.
     */
    private void setButtonSizeAndFont(JButton button, int width, int height, float fontSize) {
        button.setPreferredSize(new Dimension(width, height));
        button.setMaximumSize(new Dimension(width, height));
        button.setMinimumSize(new Dimension(width, height));
        button.setFont(button.getFont().deriveFont(fontSize));
    }
}
