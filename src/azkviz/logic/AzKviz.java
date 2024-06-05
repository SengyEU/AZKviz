package azkviz.logic;

import azkviz.gui.dialogs.InformationDialog;
import azkviz.gui.MainMenu;
import azkviz.gui.HerniPlocha;
import azkviz.otazky.Otazky;

import java.awt.*;
import java.io.IOException;
import java.util.*;

/**
 * Třída pro logiku hry AzKviz.
 */
public class AzKviz {

    /** Barva pozadí herní plochy. */
    public static final Color backgroundColor = Color.decode("#9cd2f1");
    /** Barva pro chybové hlášky. */
    public static final Color errorExitColor = Color.decode("#f40c0c");
    /** Barva pro hráče 1. */
    public static final Color player1Color = Color.decode("#70e3e5");
    /** Barva pro hráče 2. */
    public static final Color player2Color = Color.decode("#f3a004");
    /** Světle šedá barva. */
    public static final Color lightGrayColor = Color.decode("#f8f4f4");
    /** Šedá barva. */
    public static final Color grayColor = Color.decode("#808080");

    /** Určuje, zda se jedná o finálovou fázi hry. */
    public final boolean finale;
    /** Určuje, zda je na tahu hráč 1. */
    private boolean player1 = true;
    /** Stav tlačítek na herní ploše. */
    private int[][] buttonStates = new int[7][7];
    /** Pole herních polí. */
    private HerniPole[][] herniPole;
    /** Instance třídy pro práci s otázkami. */
    public Otazky otazky;
    /** Instance herní plochy. */
    public HerniPlocha herniPlocha;

    /**
     * Konstruktor pro vytvoření instance hry AzKviz.
     *
     * @param finale True, pokud se jedná o finálovou fázi hry, jinak false.
     * @throws IOException Pokud dojde k chybě při čtení otázek.
     */
    public AzKviz(boolean finale) throws IOException {
        this.finale = finale;
        initializeButtonStates();
        initializeGameFields();
        herniPlocha = new HerniPlocha(this, finale);
        otazky = new Otazky();
    }

    /**
     * Metoda pro získání stavu tlačítek na herní ploše.
     *
     * @return Stav tlačítek.
     */
    public int[][] getButtonStates() {
        return buttonStates;
    }

    /**
     * Metoda pro zjištění, zda je na tahu hráč 1.
     *
     * @return True, pokud je na tahu hráč 1, jinak false.
     */
    public boolean isPlayer1Playing() {
        return player1;
    }

    /**
     * Metoda pro nastavení, zda je na tahu hráč 1.
     *
     * @param player1 True, pokud je na tahu hráč 1, jinak false.
     */
    public void setPlayer1Playing(boolean player1) {
        this.player1 = player1;
    }

    /**
     * Metoda pro spuštění hry.
     */
    public void play() {
        if (checkWin(2)) {
            new InformationDialog("Konec hry!", "Hráč 1 vyhrál!", AzKviz.player1Color);
            restart();
        } else if (checkWin(3)) {
            new InformationDialog("Konec hry!", "Hráč 2 vyhrál!", AzKviz.player2Color);
            restart();
        }
    }

    /**
     * Metoda pro restartování hry.
     */
    private void restart() {
        herniPlocha.dispose();
        new MainMenu();
        buttonStates = new int[7][7];
        initializeButtonStates();
        player1 = true;
    }

    /**
     * Metoda pro kontrolu výhry.
     *
     * @param state Stav pro kontrolu výhry.
     * @return True, pokud hráč vyhrál, jinak false.
     */
    public boolean checkWin(int state) {
        boolean[][] visited = new boolean[7][7];

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                if (!visited[j][i] && buttonStates[i][j] == state) {
                    if (bfs(i, j, state, visited)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Metoda pro prohledávání do šířky pro kontrolu výhry.
     *
     * @param i X-ová souřadnice herního pole.
     * @param j Y-ová souřadnice herního pole.
     * @param state Stav pro kontrolu výhry.
     * @param visited Pole pro zaznamenání již navštívených herních polí.
     * @return True, pokud hráč vyhrál, jinak false.
     */
    public boolean bfs(int i, int j, int state, boolean[][] visited) {

        Queue<HerniPole> queue = new LinkedList<>();
        queue.add(herniPole[i][j]);
        visited[j][i] = true;

        boolean touchesBottom = false, touchesLeft = false, touchesRight = false;


        while (!queue.isEmpty()) {

            HerniPole current = queue.poll();

            if (current.isLeft()) touchesLeft = true;
            if (current.isRight()) touchesRight = true;
            if (current.isBottom()) touchesBottom = true;

            for (HerniPole neighbour : current.getNeighbours()) {
                int nx = neighbour.getX();
                int ny = neighbour.getY();
                if (!visited[nx][ny] && buttonStates[ny][nx] == state) {
                    visited[nx][ny] = true;
                    queue.add(neighbour);
                }
            }
        }

        return touchesBottom && touchesLeft && touchesRight;
    }

    /**
     * Metoda pro inicializaci herních polí.
     */
    private void initializeGameFields() {
        herniPole = new HerniPole[7][];
        for (int y = 0; y < 7; y++) {
            herniPole[y] = new HerniPole[y + 1];
            for (int x = 0; x <= y; x++) {
                herniPole[y][x] = new HerniPole(x, y, herniPole);
            }
        }
    }

    /**
     * Metoda pro inicializaci stavu tlačítek na herní ploše.
     */
    public void initializeButtonStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                buttonStates[i][j] = 1;
            }
        }
    }

    /**
     * Metoda pro nastavení stavu tlačítka na herní ploše.
     *
     * @param state Stav tlačítka.
     * @param row   Řádek tlačítka.
     * @param col   Sloupec tlačítka.
     */
    public void setButtonState(int state, int row, int col) {
        buttonStates[row][col] = state;
    }

    /**
     * Metoda pro získání stavu tlačítka na herní ploše.
     *
     * @param row Řádek tlačítka.
     * @param col Sloupec tlačítka.
     * @return Stav tlačítka.
     */
    public int getButtonState(int row, int col) {
        return buttonStates[row][col];
    }
}
