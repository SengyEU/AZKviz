package azkviz.gui;

import azkviz.gui.dialogs.InformationDialog;
import azkviz.logic.AzKviz;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

/**
 * Hlavní menu aplikace Az Kvíz.
 */
public class MainMenu extends JFrame {

    /**
     * Konstruktor pro vytvoření instance hlavního menu.
     */
    public MainMenu() {

        this.setTitle("Az Kvíz");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(1280, 720));
        this.setLocationRelativeTo(null);

        // Hlavní panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(AzKviz.backgroundColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(300, 300, 300, 300));

        // Název hry
        JLabel title = createLabel("AZ Kvíz", 150f);
        mainPanel.add(title);

        // Text pro výběr typu otázek
        JLabel selectionText = createLabel("Zvol typ otázek:", 50f);
        mainPanel.add(selectionText);

        // Tlačítka pro výběr typu hry
        JButton semiFinale = createGameButton("Semifinále (1-28)", false);
        mainPanel.add(semiFinale);
        JButton finale = createGameButton("Finále (A-Z)", true);
        mainPanel.add(finale);

        // Tlačítko pro ukončení aplikace
        JButton exit = createExitButton();
        mainPanel.add(exit);

        this.add(mainPanel);
        setVisible(true);
    }

    /**
     * Metoda pro vytvoření popisku s textem a velikostí písma.
     *
     * @param text Text popisku.
     * @param fontSize Velikost písma.
     * @return Popisek s nastaveným textem a velikostí písma.
     */
    private JLabel createLabel(String text, float fontSize) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(label.getFont().deriveFont(fontSize));
        return label;
    }

    /**
     * Metoda pro vytvoření tlačítka pro spuštění hry s daným typem.
     *
     * @param text Text na tlačítku.
     * @param finale True, pokud se jedná o finále, jinak false.
     * @return Tlačítko pro spuštění hry s daným typem.
     */
    private JButton createGameButton(String text, boolean finale) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(500, 300));
        button.setFont(button.getFont().deriveFont(30f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(AzKviz.lightGrayColor);
        button.addActionListener(e -> {
            try {
                new AzKviz(finale);
            } catch (IOException ex) {
                dispose();
                new InformationDialog("Nastala chyba!", "Nepodařilo se načíst otázky. Ujisti se že máš potřebné soubory. Hra nebude fungovat!", AzKviz.errorExitColor);
            }
        });
        return button;
    }

    /**
     * Metoda pro vytvoření tlačítka pro ukončení aplikace.
     *
     * @return Tlačítko pro ukončení aplikace.
     */
    private JButton createExitButton() {
        JButton button = new JButton("Odejít");
        button.setPreferredSize(new Dimension(500, 300));
        button.setFont(button.getFont().deriveFont(30f));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(AzKviz.errorExitColor);
        button.addActionListener(e -> dispose());
        return button;
    }
}
