package com.company;

import org.javatuples.Pair;

import java.util.*;

/**
 * Created by mstritzinger on 4/17/16.
 */
public class AutomatonGridType2 implements AutomatonGrid {
    private enum cellType {
        X, Y, Unocc
    }
    private cellType[][] grid;
    private int n;
    private int m;
    private Random rand;

    public AutomatonGridType2(int newM, int newN) {
        // An mxn array of booleans. True indicates the spot is occupied
        grid = new cellType[newM][newN];
        rand  = new Random();
        m = newM;
        n = newN;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = cellType.Unocc;
            }
        }

    }
    // Fills approximately fillFactor percentage of spots.
    public void gridFill(double fillFactor) {
        cellType lastFilled = cellType.X;
        for(int i = 0; i < this.m; i++) {
            for (int j = 0; j < n; j++) {
                // If we are going to fill, we alternate between Xs and Ys starting with Y
                if (this.rand.nextFloat() < fillFactor) {
                    if (lastFilled == cellType.X) {
                        grid[i][j] = cellType.Y;
                        lastFilled = cellType.Y;
                    }
                    else {
                        grid[i][j] = cellType.X;
                        lastFilled = cellType.X;
                    }
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
                if (this.grid[i][j] == cellType.X) {
                    repr += "x ";
                }
                else if (this.grid[i][j] == cellType.Y){
                    repr += "y ";
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
    private Map<cellType, Integer> numOccupiedNeighbors(int x, int y) {
        Map<cellType, Integer> numOccupiedNeighbs = new HashMap<>();
        numOccupiedNeighbs.put(cellType.Unocc, 0);
        numOccupiedNeighbs.put(cellType.X, 0);
        numOccupiedNeighbs.put(cellType.Y, 0);

        for (int i = Math.max((x - 1), 0); i <= Math.min((x + 1), this.m - 1); i++) {
            for (int j = Math.max((y - 1), 0); j <= Math.min((y + 1), this.m - 1); j++) {
                if (!(i == x && j == y)) {
                    numOccupiedNeighbs.put(grid[i][j], numOccupiedNeighbs.get(grid[i][j]) + 1);
                }
            }
        }
        return numOccupiedNeighbs;
    }
    private cellType edgeHandler (int i, int j) {
        Map<cellType, Integer> numOccupiedNeighbs = numOccupiedNeighbors(i, j);
        cellType defaultReturn = cellType.X;
        return defaultReturn;
    }

    public void runIteration() {
        cellType[][] newGrid = this.grid.clone();
        Set<Pair<Integer, Integer> > unoccupiedNew = new HashSet<>();
        Set<Pair<Integer, Integer> > occupiedOld = new HashSet<>();
        Map<Pair<Integer, Integer>, Map<cellType, Integer>> neighborSet = new HashMap<>();
        // Generate a set of all unoccupied spaces
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                if (this.grid[i][j] == cellType.Unocc) {
                    unoccupiedNew.add(new Pair<>(i, j));
                }
                else {
                    occupiedOld.add(new Pair<>(i, j));
                }
                neighborSet.put(new Pair<>(i,j), numOccupiedNeighbors(i,j));
            }
        }
        int numOccupiedOld = occupiedOld.size();

        // Run through all currently occupied cells to see where they will end up
        for (Pair<Integer, Integer> cell : occupiedOld) {
            Map<cellType, Integer> numOccupiedNeighbs = neighborSet.get(cell);
            cellType cellVal = this.grid[cell.getValue0()][cell.getValue1()];
            // Handle Normally
            if (this.rand.nextDouble() >= .1) {

                // Handle X Cells
                if (cellVal == cellType.X) {

                    // We need to move
                    if (numOccupiedNeighbs.get(cellType.X) < 2) {
                        System.out.println(cell.toString() + " has fewer than two neighbors, gonna move");

                        // We need a set of all possible spaces. We are going to randomly remove one in each iteration
                        Set<Pair<Integer, Integer>> searchSet = new HashSet<>(unoccupiedNew);
                        for (int i = 0; i < unoccupiedNew.size(); i++) {
                            //Convert to list, randomly shuffle, and take the first element.
                            List<Pair<Integer, Integer>> selectFrom = new ArrayList<>(searchSet);
                            Collections.shuffle(selectFrom);
                            Pair<Integer, Integer> space = selectFrom.get(0);
                            // We selected this element, don't want to use it again when we find a new random one.
                            searchSet.remove(space);

                            if (neighborSet.get(space).get(cellType.X) >= 2) {
                                newGrid[space.getValue0()][space.getValue1()] = cellType.X;
                                newGrid[cell.getValue0()][cell.getValue1()] = cellType.Unocc;
                                unoccupiedNew.remove(space);
                                unoccupiedNew.add(cell);
                                System.out.println(space.toString() + " was the final position");
                                break;
                            }
                        }
                    }
                }
                // Handle Ys
                else {
                    if (numOccupiedNeighbs.get(cellType.Y) == 0) {

                        // We need a set of all possible spaces. We are going to randomly remove one in each iteration
                        Set<Pair<Integer, Integer>> searchSet = new HashSet<>(unoccupiedNew);
                        for (int i = 0; i < unoccupiedNew.size(); i++) {
                            //Convert to list, randomly shuffle, and take the first element.
                            List<Pair<Integer, Integer>> selectFrom = new ArrayList<>(searchSet);
                            Collections.shuffle(selectFrom);
                            Pair<Integer, Integer> space = selectFrom.get(0);
                            searchSet.remove(space);


                            if (neighborSet.get(space).get(cellType.Y) >= 2) {
                                newGrid[space.getValue0()][space.getValue1()] = cellType.Y;
                                newGrid[cell.getValue0()][cell.getValue1()] = cellType.Unocc;
                                unoccupiedNew.remove(space);
                                unoccupiedNew.add(cell);
                                break;
                            }
                        }
                    }
                }

            }
            // Random chance to do something else
            else {
                // We need a set of all possible spaces. We are going to randomly remove one in each iteration
                Set<Pair<Integer, Integer>> searchSet = new HashSet<>(unoccupiedNew);
                for (int i = 0; i < unoccupiedNew.size(); i++) {
                    //Convert to list, randomly shuffle, and take the first element.
                    List<Pair<Integer, Integer>> selectFrom = new ArrayList<>(searchSet);
                    Collections.shuffle(selectFrom);
                    Pair<Integer, Integer> space = selectFrom.get(0);
                    // We selected this element, don't want to use it again when we find a new random one.
                    searchSet.remove(space);

                    int numNeighbors = neighborSet.get(space).get(cellType.X) + neighborSet.get(space).get(cellType.Y);

                    // If the number of neighbors is less than 1, we're gonna move there!
                    if (numNeighbors <= 1) {
                        newGrid[space.getValue0()][space.getValue1()] = cellVal;
                        newGrid[cell.getValue0()][cell.getValue1()] = cellType.Unocc;
                        unoccupiedNew.remove(space);
                        unoccupiedNew.add(cell);
                        break;
                    }
                }
            }

        }
        assert(numOccupiedOld + unoccupiedNew.size() == this.n * this.m);
    }
}
