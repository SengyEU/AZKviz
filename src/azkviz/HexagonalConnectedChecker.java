package azkviz;

import java.util.*;

public class HexagonalConnectedChecker {

    static class Coordinate {
        int x, y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static boolean isConnected(int[][] field, int target) {
        if (field == null || field.length == 0 || field[0].length == 0) {
            return false;
        }

        int rows = field.length;
        int cols = field[0].length;
        Map<Coordinate, Boolean> visited = new HashMap<>();

        // Perform depth-first search to check connectivity
        dfs(field, new Coordinate(0, 0), target, visited);

        // Check if all occurrences of the target value are visited
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == target && !visited.getOrDefault(new Coordinate(i, j), false)) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void dfs(int[][] field, Coordinate current, int target, Map<Coordinate, Boolean> visited) {
        if (!isValidCell(field, current) || visited.getOrDefault(current, false) || field[current.x][current.y] != target) {
            return;
        }

        visited.put(current, true);

        // Define the six possible directions in a hexagonal grid
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}};

        // Traverse in all six directions
        for (int[] dir : directions) {
            Coordinate next = new Coordinate(current.x + dir[0], current.y + dir[1]);
            dfs(field, next, target, visited);
        }
    }

    private static boolean isValidCell(int[][] field, Coordinate cell) {
        int rows = field.length;
        int cols = field[0].length;
        return cell.x >= 0 && cell.x < rows && cell.y >= 0 && cell.y < cols;
    }

    public static void main(String[] args) {
        int[][] field = {
                {1, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0},
                {1, 2, 1, 0, 0, 0, 0},
                {1, 1, 2, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0, 0},
                {1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1}
        };

        int target = 2; // Change this to 3 if you want to check for threes

        boolean connected = isConnected(field, target);
        System.out.println("Are all " + target + "s connected? " + connected);
    }
}
