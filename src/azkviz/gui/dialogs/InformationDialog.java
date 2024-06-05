package azkviz.gui.dialogs;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog pro zobrazení informace.
 */
public class InformationDialog extends JDialog {

    /**
     * Konstruktor pro vytvoření instance dialogu pro zobrazení informace.
     *
     * @param title Název dialogu.
     * @param text Text zobrazený v dialogu.
     * @param color Barva tlačítka.
     */
    public InformationDialog(String title, String text, Color color) {
        super((Frame) null, title, true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JLabel otazkaLabel = new JLabel("<html><center>" + text + "</center></html>", SwingConstants.CENTER);
        otazkaLabel.setFont(otazkaLabel.getFont().deriveFont(20f));
        otazkaLabel.setOpaque(true);
        otazkaLabel.setBackground(Color.decode("#0c0e0a"));
        otazkaLabel.setForeground(Color.WHITE);
        otazkaLabel.setPreferredSize(new Dimension(otazkaLabel.getWidth(), 100));
        this.add(otazkaLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.decode("#0c0e0a"));
        inputPanel.setForeground(Color.WHITE);

        JButton yesButton = new JButton("✔");
        yesButton.setFont(yesButton.getFont().deriveFont(20f));
        yesButton.setBackground(color);
        yesButton.setPreferredSize(new Dimension(80, 80));

        yesButton.addActionListener(e -> dispose());

        inputPanel.add(yesButton);

        this.add(inputPanel, BorderLayout.CENTER);

        this.setVisible(true);
    }
}
