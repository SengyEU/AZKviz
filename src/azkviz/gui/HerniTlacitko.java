package azkviz.gui;

import azkviz.gui.dialogs.AnswerInputDialog;
import azkviz.gui.dialogs.InformationDialog;
import azkviz.logic.AzKviz;
import azkviz.otazky.NahradniOtazka;
import azkviz.otazky.Otazka;

import java.awt.*;

public class HerniTlacitko extends HexagonButton {

    private static final int NO_ANSWER = 0;
    private static final int PLAYER_1_CORRECT = 1;
    private static final int PLAYER_2_CORRECT = 2;
    private static final int PLAYER_DECLINED = 3;
    private static final int PLAYER_2_WRONG = 4;
    private final AzKviz azKviz;
    private final int row;
    private final int col;
    private final String identifier;

    public HerniTlacitko(AzKviz azKviz, HerniPlocha herniPlocha, String text, String identifier, int row, int col) {
        super(text, Color.WHITE);
        applyDefaults();

        this.azKviz = azKviz;
        this.row = row;
        this.col = col;
        this.identifier = identifier;

        this.addActionListener(e -> {
            int state = azKviz.getButtonState(row, col);

            if (state != 1 && state != 4) return;

            int result = otazka(state == 4, herniPlocha);
            handleResult(result, herniPlocha);
            setText(identifier);
            herniPlocha.player1icon.setText("");
            herniPlocha.player2icon.setText("");
        });
    }

    private void handleResult(int result, HerniPlocha herniPlocha) {
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

        updatePlayerColors(herniPlocha);
    }

    private void updatePlayerColors(HerniPlocha herniPlocha) {
        if (azKviz.isPlayer1Playing()) {
            herniPlocha.player1icon.setColor(AzKviz.player1Color);
            herniPlocha.player2icon.setColor(AzKviz.grayColor);
        } else {
            herniPlocha.player2icon.setColor(AzKviz.player2Color);
            herniPlocha.player1icon.setColor(AzKviz.grayColor);
        }
    }

    private int otazka(boolean anoNe, HerniPlocha herniPlocha) {
        Otazka otazka = azKviz.otazky.vygenerujOtazku(azKviz.finale, anoNe, getText());

        if (!anoNe) {
            showFirstLetters(otazka, herniPlocha);
        }

        if (anoNe) {
            return handleYesNoQuestion(otazka);
        } else {
            return handleTextQuestion(otazka);
        }
    }

    private String getFirstLetters(Otazka otazka) {
        String[] words = otazka.getOdpovedi()[0].split(" ");
        StringBuilder firstLetters = new StringBuilder();
        for (String word : words) {
            firstLetters.append(word.charAt(0));
        }

        return firstLetters.toString();
    }

    private void showFirstLetters(Otazka otazka, HerniPlocha herniPlocha) {

        String firstLetters = getFirstLetters(otazka);

        if (azKviz.isPlayer1Playing()) {
            herniPlocha.player1icon.setText(identifier);
        } else {
            herniPlocha.player2icon.setText(identifier);
        }

        if(!azKviz.finale) setText(firstLetters);
    }

    private int handleYesNoQuestion(Otazka otazka) {

        String decision = showInputDialog(otazka.getOtazka(), azKviz.isPlayer1Playing() ? AzKviz.player1Color : AzKviz.player2Color, identifier, true);

        int answer = (decision == null || !decision.equalsIgnoreCase("ano")) ? 1 : 0;

        if (((NahradniOtazka) otazka).jeOdpovedSpravne(answer)) {
            showCorrectAnswerDialog(otazka);
            return azKviz.isPlayer1Playing() ? PLAYER_1_CORRECT : PLAYER_2_CORRECT;
        } else {
            return NO_ANSWER;
        }
    }

    private int handleTextQuestion(Otazka otazka) {
        String answer = showInputDialog(otazka.getOtazka(), azKviz.isPlayer1Playing() ? AzKviz.player1Color : AzKviz.player2Color, getFirstLetters(otazka), false);
        if (otazka.jeOdpovedSpravne(answer)) {
            showCorrectAnswerDialog(otazka);
            return azKviz.isPlayer1Playing() ? PLAYER_1_CORRECT : PLAYER_2_CORRECT;
        } else {
            return handleWrongTextAnswer(otazka);
        }
    }

    private String showInputDialog(String otazka, Color color, String buttonText, boolean anoNe) {
        AnswerInputDialog dialog = new AnswerInputDialog(otazka, color, buttonText, anoNe);
        dialog.setVisible(true);
        if (dialog.isSubmitted()) {
            return dialog.getUserInput();
        } else {
            return null;
        }
    }

    private int handleWrongTextAnswer(Otazka otazka) {
        String decision = showInputDialog("Chce odpovídat druhý hráč?", AzKviz.lightGrayColor, getFirstLetters(otazka), true);

        int secondPlayer = (decision == null || !decision.equalsIgnoreCase("ano")) ? 1 : 0;

        if (secondPlayer == 0) {
            String answer2 = showInputDialog(otazka.getOtazka(), !azKviz.isPlayer1Playing() ? AzKviz.player1Color : AzKviz.player2Color, getFirstLetters(otazka), false);
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

        String spravnaOdpoved = otazka instanceof NahradniOtazka ? ((NahradniOtazka) otazka).getOdpoved() : otazka.getOdpovedi()[0];

        new InformationDialog("Správná odpověď", "Správná odpověď byla: " + spravnaOdpoved, AzKviz.lightGrayColor);
    }


    private Color getStateColor() {
        Color fillColor;
        switch (azKviz.getButtonState(row, col)) {
            case 2 -> fillColor = AzKviz.player1Color;
            case 3 -> fillColor = AzKviz.player2Color;
            case 4 -> fillColor = Color.BLACK;
            default -> fillColor = AzKviz.lightGrayColor;
        }

        return fillColor;
    }

    public void setState(int state) {

        azKviz.setButtonState(state, row, col);


        repaint();
        azKviz.play();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonButton.HexagonPath path = getHexagonPath();
        g2d.setColor(getForeground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.draw(path);
        g2d.setColor(getStateColor());
        paintText(g, g2d, path);
    }
}
