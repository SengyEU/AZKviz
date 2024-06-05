package azkviz.gui.dialogs;

import azkviz.gui.HexagonButton;
import azkviz.logic.AzKviz;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog pro zadání odpovědi.
 */
public class AnswerInputDialog extends JDialog {
    private String userInput;
    private boolean isSubmitted;

    /**
     * Konstruktor pro vytvoření instance dialogu pro zadání odpovědi.
     *
     * @param otazka Text otázky.
     * @param color Barva tlačítek.
     * @param buttonText Text tlačítka.
     * @param anoNe True, pokud jsou odpovědi "Ano" a "Ne", jinak false pro textové pole.
     */
    public AnswerInputDialog(String otazka, Color color, String buttonText, boolean anoNe) {
        super((Frame) null, "Zadej svojí odpověď", true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(800, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        JLabel otazkaLabel = new JLabel("<html><center>" + otazka + "</center></html>", SwingConstants.CENTER);
        otazkaLabel.setFont(otazkaLabel.getFont().deriveFont(20f));
        otazkaLabel.setOpaque(true);
        otazkaLabel.setBackground(Color.decode("#0c0e0a"));
        otazkaLabel.setForeground(Color.WHITE);
        otazkaLabel.setPreferredSize(new Dimension(otazkaLabel.getWidth(), 100));
        this.add(otazkaLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBackground(Color.decode("#0c0e0a"));
        inputPanel.setForeground(Color.WHITE);

        HexagonButton hexagonButton = new HexagonButton(buttonText, AzKviz.lightGrayColor);
        hexagonButton.setFont(otazkaLabel.getFont().deriveFont(30f));
        hexagonButton.setBackground(Color.decode("#0c0e0a"));
        hexagonButton.setPreferredSize(new Dimension(80, 80));
        inputPanel.add(hexagonButton);

        if (anoNe) {
            JButton yesButton = new JButton("Ano");
            yesButton.setFont(yesButton.getFont().deriveFont(20f));
            yesButton.setBackground(color);
            yesButton.setPreferredSize(new Dimension(80, 80));
            JButton noButton = new JButton("Ne");
            noButton.setFont(noButton.getFont().deriveFont(20f));
            noButton.setBackground(color);
            noButton.setPreferredSize(new Dimension(160, 80));

            inputPanel.add(yesButton);
            inputPanel.add(noButton);

            yesButton.addActionListener(e -> {
                userInput = "ano";
                isSubmitted = true;
                dispose();
            });

            noButton.addActionListener(e -> {
                userInput = "ne";
                isSubmitted = false;
                dispose();
            });
        } else {
            JTextField answerField = new JTextField(25);
            answerField.setFont(answerField.getFont().deriveFont(20f));
            answerField.setPreferredSize(new Dimension(80, 80));
            inputPanel.add(answerField);

            JButton submitButton = new JButton("✔");
            submitButton.setFont(submitButton.getFont().deriveFont(20f));
            submitButton.setBackground(color);
            submitButton.setPreferredSize(new Dimension(80, 80));
            JButton cancelButton = new JButton("✘ Nevím");
            cancelButton.setFont(cancelButton.getFont().deriveFont(20f));
            cancelButton.setBackground(color);
            cancelButton.setPreferredSize(new Dimension(160, 80));

            inputPanel.add(submitButton);
            inputPanel.add(cancelButton);

            submitButton.addActionListener(e -> {
                userInput = answerField.getText();
                isSubmitted = true;
                dispose();
            });

            cancelButton.addActionListener(e -> {
                userInput = null;
                isSubmitted = false;
                dispose();
            });
        }

        this.add(inputPanel, BorderLayout.CENTER);
    }

    /**
     * Metoda pro získání uživatelského vstupu.
     *
     * @return Uživatelský vstup.
     */
    public String getUserInput() {
        return userInput;
    }

    /**
     * Metoda pro zjištění, zda byla odpověď odeslána.
     *
     * @return True, pokud byla odpověď odeslána, jinak false.
     */
    public boolean isSubmitted() {
        return isSubmitted;
    }
}
