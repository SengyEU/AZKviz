package azkviz.gui;

import azkviz.AzKviz;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class HlavniMenu extends JFrame {

    public HlavniMenu() {
        JButton semiFinale = createButton("Semifinále (1-28)", false);
        JButton finale = createButton("Finále (A-Z)", true);

        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        this.add(semiFinale);
        this.add(finale);

        setTitle("Az Kvíz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text, boolean isFinale) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 150)); // Increase button size
        button.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font size bigger
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Align button to the center horizontally
        button.setAlignmentY(Component.CENTER_ALIGNMENT); // Align button to the center vertically
        button.addActionListener(e -> {
            AzKviz azKviz = new AzKviz(isFinale);
            try {
                azKviz.start();
            } catch (IOException ex) {
                System.out.println("Nepodařilo se načíst otázky. Máš správně uložené soubory?");
            }
            dispose();
        });
        return button;
    }
}
