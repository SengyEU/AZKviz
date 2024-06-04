package azkviz;

import azkviz.gui.HlavniMenu;
import azkviz.gui.HraciPole;
import azkviz.otazky.Otazky;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class AzKviz {


    private final boolean finale;

    private boolean player1 = true;
    private int[][] buttonStates = new int[7][7];
    private GameField[][] gameFields;
    public Otazky otazky;

    HraciPole hraciPole;

    public AzKviz(boolean finale) {
        this.finale = finale;
    }

    public void start() throws IOException {
        initializeButtonStates();
        initializeGameFields();
        hraciPole = new HraciPole(this, finale);
        otazky = new Otazky();
    }

    public int[][] getButtonStates() {
        return buttonStates;
    }

    public boolean isPlayer1Playing() {
        return player1;
    }

    public void setPlayer1Playing(boolean player1) {
        this.player1 = player1;
    }

    public void play(){
        if(checkWin(2)){
            JOptionPane.showMessageDialog(null, "Hráč 1 vyhrál", "1", JOptionPane.INFORMATION_MESSAGE);
            restart();
        }
        else if(checkWin(3)){
            JOptionPane.showMessageDialog(null, "Hráč 2 vyhrál", "2", JOptionPane.INFORMATION_MESSAGE);
            restart();
        }
    }

    private void restart(){
        hraciPole.dispose();
        HlavniMenu hlavniMenu = new HlavniMenu();
        buttonStates = new int[7][7];
        initializeButtonStates();
        player1 = true;
    }

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


    public boolean bfs(int i, int j, int state, boolean[][] visited) {

        Queue<GameField> queue = new LinkedList<>();
        queue.add(gameFields[i][j]);
        visited[j][i] = true;

        boolean touchesBottom = false, touchesLeft = false, touchesRight = false;


        while (!queue.isEmpty()) {

            GameField current = queue.poll();

            if (current.isLeft()) touchesLeft = true;
            if (current.isRight()) touchesRight = true;
            if (current.isBottom()) touchesBottom = true;

            for (GameField neighbour : current.getNeighbours()) {
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








    private void initializeGameFields() {
        gameFields = new GameField[7][];
        for (int y = 0; y < 7; y++) {
            gameFields[y] = new GameField[y + 1];
            for (int x = 0; x <= y; x++) {
                gameFields[y][x] = new GameField(x, y, gameFields);
            }
        }
    }

    public void initializeButtonStates() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j <= i; j++) {
                buttonStates[i][j] = 1;
            }
        }
    }

    public void setButtonState(int state, int row, int col) {
        buttonStates[row][col] = state;
    }

    public int getButtonState(int row, int col) {
        return buttonStates[row][col];
    }
}

class GameField {

    @Override
    public String toString() {
        return x + " " + y;
    }

    private int x;
    private int y;
    private GameField[][] fields;

    public GameField(int x, int y, GameField[][] fields) {
        this.x = x;
        this.y = y;
        this.fields = fields;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isLeft(){
        return x == 0 || y == 0;
    }

    public boolean isRight(){
        return (x == 0 && y == 0) || x == y;
    }

    public boolean isBottom(){
        return y == 6;
    }

    public List<GameField> getNeighbours() {
        List<GameField> neighbours = new ArrayList<>();

        int[][] directions = {
                {0, -1}, {0, 1}, {-1, 0}, {-1, -1}, {1, 0}, {1, 1}
        };

        for (int[] direction : directions) {
            int dx = direction[0];
            int dy = direction[1];
            int newX = x + dx;
            int newY = y + dy;

            if (newY >= 0 && newY < fields.length && newX >= 0 && newX < fields[newY].length) {
                neighbours.add(fields[newY][newX]);
            }
        }

        return neighbours;
    }


}
