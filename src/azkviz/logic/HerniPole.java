package azkviz.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída reprezentující herní pole.
 */
public class HerniPole {

    @Override
    public String toString() {
        return x + " " + y;
    }

    private final int x;
    private final int y;
    private final HerniPole[][] fields;

    /**
     * Konstruktor pro vytvoření instance herního pole.
     *
     * @param x      Souřadnice X herního pole.
     * @param y      Souřadnice Y herního pole.
     * @param fields Pole herních polí, ve kterém se nachází.
     */
    public HerniPole(int x, int y, HerniPole[][] fields) {
        this.x = x;
        this.y = y;
        this.fields = fields;
    }

    /**
     * Metoda pro získání souřadnice X herního pole.
     *
     * @return Souřadnice X herního pole.
     */
    public int getX() {
        return x;
    }

    /**
     * Metoda pro získání souřadnice Y herního pole.
     *
     * @return Souřadnice Y herního pole.
     */
    public int getY() {
        return y;
    }

    /**
     * Metoda pro zjištění, zda se herní pole nachází na levém okraji.
     *
     * @return True, pokud se pole nachází na levém okraji, jinak false.
     */
    public boolean isLeft(){
        return x == 0 || y == 0;
    }

    /**
     * Metoda pro zjištění, zda se herní pole nachází na pravém okraji.
     *
     * @return True, pokud se pole nachází na pravém okraji, jinak false.
     */
    public boolean isRight(){
        return (x == 0 && y == 0) || x == y;
    }

    /**
     * Metoda pro zjištění, zda se herní pole nachází na spodním okraji.
     *
     * @return True, pokud se pole nachází na spodním okraji, jinak false.
     */
    public boolean isBottom(){
        return y == 6;
    }

    /**
     * Metoda pro získání seznamu sousedních herních polí.
     *
     * @return Seznam sousedních herních polí.
     */
    public List<HerniPole> getNeighbours() {
        List<HerniPole> neighbours = new ArrayList<>();

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
