package mu.lambda;

import java.util.Random;

/**
 * Created by mstritzinger on 4/16/16.
 */
public class AutomatonGridType1 implements AutomatonGrid {
    private boolean[][] grid;
    private int n;
    private int m;
    private Random rand;

    public AutomatonGridType1(int newM, int newN) {
        // An mxn array of booleans. True indicates the spot is occupied
        grid = new boolean[newM][newN];
        rand  = new Random();
        m = newM;
        n = newN;

    }
    // Fills approximately fillFactor percentage of spots.
    public void gridFill(double fillFactor) {
        for(int i = 0; i < this.m; i++) {
            for (int j = 0; j < n; j++) {
                if (this.rand.nextFloat() < fillFactor) {
                    this.grid[i][j] = true;
                }
            }
        }
    }
    // Helper function that tells us if a given index is in our grid
    private boolean inGrid(int checkM, int checkN) {
        return (checkM >= 0 && checkM < m && checkN >= 0 && checkN < n);
    }
    public String toString() {
        String repr = "";
        for (int i = 0; i < this.m; i++) {
            for(int j = 0; j < this.n; j++) {
                if (this.grid[i][j]) {
                    repr += "+ ";
                }
                else {
                    repr += "- ";
                }
            }
            repr += "\n";
       }
        return repr;
    }
    // Returns the number of neighbors for a given index
    private int numOccupiedNeighbors(int x, int y) {
        int numOccupiedNeighbors = 0;
        for (int i = Math.max((x - 1), 0); i <= Math.min((x + 1), this.m - 1); i++) {
            for (int j = Math.max((y - 1), 0); j <= Math.min((y + 1), this.m - 1); j++) {
               if (grid[i][j] && !(i == x && j == y)) {
                   numOccupiedNeighbors += 1;
               }

            }
        }
        return numOccupiedNeighbors;
    }
    private boolean edgeHandler (int i, int j) {
        int numOccupiedNeighbs = numOccupiedNeighbors(i, j);
        boolean defaultReturn = false;
        // Current cell occupied
        if (this.grid[i][j]) {
            if (numOccupiedNeighbs <= 3) {
                    defaultReturn = true;
                }
        }
        // Current cell unoccupied
        else {
            if (numOccupiedNeighbs > 2) {
                if (this.rand.nextDouble() <= .3) {
                    defaultReturn = true;
                }
            }
        }
        return defaultReturn;
    }

    public void runIteration() {
        boolean[][] newGrid = new boolean[this.m][this.n];
        // First handle interior points (no corners or edges)
        for (int i = 1; i < this.m - 1; i++) {
            for (int j = 1; j < this.n - 1; j++) {
                int numOccupiedNeighbs = numOccupiedNeighbors(i, j);
                // We are handling a currently occupied cell
                if (this.grid[i][j]) {
                    if (numOccupiedNeighbs <= 5) {
                        newGrid[i][j] = true;
                    }
                }
                // Current cell unoccupied
                else {
                    if (numOccupiedNeighbs > 2) {
                        if (this.rand.nextDouble() <= .3) {
                            newGrid[i][j] = true;
                        }
                    }

                }
            }
        }
        // Now we handle edges (still no corners)

        // This represents the top and bottom edges
        for (int i = 1; i < this.m - 1; i++) {
            for (int j : new int[]{0, this.n - 1}) {
                newGrid[i][j] = edgeHandler(i, j);
            }
        }
        // This represents the left and right edges
        for (int i : new int[]{0, this.m - 1}) {
            for (int j = 1; j < this.n - 1; j++) {
                newGrid[i][j] = edgeHandler(i, j);
            }
        }
        // Now we handle corners
        for (int i : new int[]{0, this.m - 1}) {
            for (int j : new int[]{0, this.n - 1}) {
                newGrid[i][j] = edgeHandler(i, j);
            }
        }
       this.grid = newGrid;
    }
}
