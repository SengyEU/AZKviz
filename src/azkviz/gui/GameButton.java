package azkviz.gui;

import azkviz.AzKviz;
import azkviz.otazky.Otazka;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class GameButton extends JButton {

    private static final int NO_ANSWER = 0;
    private static final int PLAYER_1_CORRECT = 1;
    private static final int PLAYER_2_CORRECT = 2;
    private static final int PLAYER_DECLINED = 3;
    private static final int PLAYER_2_WRONG = 4;
    private final AzKviz azKviz;
    private final int row;
    private final int col;
    private HexagonPath hexagonPath;

    public GameButton(AzKviz azKviz, HraciPole hraciPole, String text, String number, int row, int col) {
        super(text);
        applyDefaults();

        this.azKviz = azKviz;
        this.row = row;
        this.col = col;

        this.addActionListener(e -> {
            int state = azKviz.getButtonState(row, col);

            if (state != 1 && state != 4) return;

            int result = otazka(state == 4, hraciPole);
            handleResult(result, hraciPole);
            setText(number);
            hraciPole.player1icon.setText("");
            hraciPole.player2icon.setText("");
        });
    }

    private void handleResult(int result, HraciPole hraciPole) {
        if (result == PLAYER_1_CORRECT) {
            setState(2);
            azKviz.setPlayer1Playing(false);
        } else if (result == PLAYER_2_CORRECT) {
            setState(3);
            azKviz.setPlayer1Playing(true);
        } else if (result == PLAYER_DECLINED) {
            setState(4);
            azKviz.setPlayer1Playing(!azKviz.isPlayer1Playing());
        } else if (result == PLAYER_2_WRONG) {
            setState(4);
        } else {
            setState(azKviz.isPlayer1Playing() ? 3 : 2);
        }

        updatePlayerColors(hraciPole);
    }

    private void updatePlayerColors(HraciPole hraciPole) {
        if (azKviz.isPlayer1Playing()) {
            hraciPole.player1icon.setColor("#70e3e5");
            hraciPole.player2icon.setColor("#808080");
        } else {
            hraciPole.player2icon.setColor("#f3a004");
            hraciPole.player1icon.setColor("#808080");
        }
    }

    private int otazka(boolean anoNe, HraciPole hraciPole) {
        Otazka otazka = azKviz.otazky.vygenerujOtazku(azKviz.finale, anoNe, getText());

        if (!anoNe) {
            showFirstLetters(otazka, hraciPole);
        }

        if (anoNe) {
            return handleYesNoQuestion(otazka);
        } else {
            return handleTextQuestion(otazka);
        }
    }

    private void showFirstLetters(Otazka otazka, HraciPole hraciPole) {
        String[] words = otazka.getOdpoved().split(" ");
        StringBuilder firstLetters = new StringBuilder();
        for (String word : words) {
            firstLetters.append(word.charAt(0));
        }

        if (azKviz.isPlayer1Playing()) {
            hraciPole.player1icon.setText(firstLetters.toString().toUpperCase());
        } else {
            hraciPole.player2icon.setText(firstLetters.toString().toUpperCase());
        }

        if(!azKviz.finale) setText(firstLetters.toString());
    }

    private int handleYesNoQuestion(Otazka otazka) {
        int answer = JOptionPane.showConfirmDialog(null, otazka.getOtazka());
        int correctAnswer = otazka.getOdpoved().equalsIgnoreCase("ano") ? 0 : 1;
        if (answer == correctAnswer) {
            showCorrectAnswerDialog(otazka);
            return azKviz.isPlayer1Playing() ? PLAYER_1_CORRECT : PLAYER_2_CORRECT;
        } else {
            return NO_ANSWER;
        }
    }

    private int handleTextQuestion(Otazka otazka) {
        String answer = JOptionPane.showInputDialog(null, otazka.getOtazka());
        if (otazka.jeOdpovedSpravne(answer)) {
            showCorrectAnswerDialog(otazka);
            return azKviz.isPlayer1Playing() ? PLAYER_1_CORRECT : PLAYER_2_CORRECT;
        } else {
            return handleWrongTextAnswer(otazka);
        }
    }

    private int handleWrongTextAnswer(Otazka otazka) {
        int secondPlayer = JOptionPane.showConfirmDialog(null, "Chce odpovídat druhý hráč?");
        if (secondPlayer == 0) {
            String answer2 = JOptionPane.showInputDialog(null, otazka.getOtazka());
            if (otazka.jeOdpovedSpravne(answer2)) {
                showCorrectAnswerDialog(otazka);
                return azKviz.isPlayer1Playing() ? PLAYER_2_CORRECT : PLAYER_1_CORRECT;
            } else {
                showCorrectAnswerDialog(otazka);
                return PLAYER_2_WRONG;
            }
        } else {
            showCorrectAnswerDialog(otazka);
            return PLAYER_DECLINED;
        }
    }

    private void showCorrectAnswerDialog(Otazka otazka) {
        JOptionPane.showMessageDialog(null, "Správná odpověď byla: " + otazka.getOdpoved(), otazka.getOdpoved(), JOptionPane.INFORMATION_MESSAGE);
    }

    private Color getStateColor() {
        Color fillColor;
        switch (azKviz.getButtonState(row, col)) {
            case 2 -> fillColor = Color.decode("#70e3e5");
            case 3 -> fillColor = Color.decode("#f3a004");
            case 4 -> fillColor = Color.BLACK;
            default -> fillColor = Color.decode("#f8f4f4");
        }

        return fillColor;
    }

    public void setState(int state) {

        azKviz.setButtonState(state, row, col);


        repaint();
        azKviz.play();
    }

    @Override
    public void invalidate() {
        hexagonPath = null;
        super.invalidate();
    }

    protected int getMaxDimension() {
        Dimension size = super.getPreferredSize();
        return Math.max(size.width, size.height);
    }

    @Override
    public Dimension getPreferredSize() {
        int maxDimension = getMaxDimension();
        return new Dimension(maxDimension, maxDimension);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    protected void applyDefaults() {
        setBorderPainted(false);
        setFocusPainted(false);
    }

    protected HexagonPath getHexagonPath() {
        if (hexagonPath == null) {
            hexagonPath = new HexagonPath(getMaxDimension() - 1);
        }
        return hexagonPath;
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonPath path = getHexagonPath();
        g2d.setColor(getForeground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.draw(path);
        g2d.setColor(getStateColor());
        g2d.fill(path);

        g2d.dispose();

        String text = getText();
        if (text != null && !text.isEmpty()) {
            Graphics2D textG2d = (Graphics2D) g.create();
            textG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm = textG2d.getFontMetrics();
            Rectangle textBounds = fm.getStringBounds(text, textG2d).getBounds();
            int x = (getWidth() - textBounds.width) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            textG2d.setColor(getForeground());
            textG2d.drawString(text, x, y);
            textG2d.dispose();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D hexagonG2d = (Graphics2D) g.create();
        hexagonG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hexagonG2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hexagonG2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        hexagonG2d.setColor(getBackground());
        hexagonG2d.fill(getHexagonPath());
        hexagonG2d.dispose();
    }


    @Override
    public Color getBackground() {
        if (getModel().isArmed()) {
            return Color.BLUE;
        }
        return super.getBackground();
    }

    @Override
    public Color getForeground() {
        if (getModel().isArmed()) {
            return Color.WHITE;
        }
        return super.getForeground();
    }

    protected static class HexagonPath extends Path2D.Double {

        public HexagonPath(double size) {
            double centerX = size / 2d;
            double centerY = size / 2d;
            for (double i = 0; i < 6; i++) {
                double angleDegrees = (60d * i) - 30d;
                double angleRad = ((float) Math.PI / 180.0f) * angleDegrees;

                double x = centerX + ((size / 2f) * Math.cos(angleRad));
                double y = centerY + ((size / 2f) * Math.sin(angleRad));

                if (i == 0) {
                    moveTo(x, y);
                } else {
                    lineTo(x, y);
                }
            }
            closePath();
        }

    }
}
