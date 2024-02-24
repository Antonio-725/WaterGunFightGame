package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a fort in the game.
 */
public class Fort {
    private List<Cell> cells;
    private char id;
    private boolean destroyed;
    private boolean hasHitPlayer;

    /**
     * Constructs a new fort with the given ID and cells.
     *
     * @param id    the ID of the fort
     * @param cells the cells occupied by the fort
     */
    public Fort(char id, List<Cell> cells) {
        this.id = id;
        this.cells = cells;
        this.destroyed = false;
        this.hasHitPlayer = false;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public char getId() {
        return id;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isCompletelyDestroyed() {
        return cells.stream().allMatch(Cell::isHit);
    }

    /**
     * Checks if the fort is hit by a shot at the given cell.
     *
     * @param shot the cell representing the shot
     * @return true if the fort is hit, false otherwise
     */
    public boolean isHit(Cell shot) {
        for (Cell cell : cells) {
            if (cell.getX() == shot.getX() && cell.getY() == shot.getY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Marks the fort as hit by a shot at the given cell.
     *
     * @param shot the cell representing the shot
     */
    public void hit(Cell shot) {
        if (cells.contains(shot)) {
            shot.setHit(true);
            if (allCellsHit()) {
                destroyed = true;
            }
        }
    }

    private boolean allCellsHit() {
        return cells.stream().allMatch(Cell::isHit);
    }

    public boolean hasHitPlayer() {
        return hasHitPlayer;
    }

    public void setHitPlayer(boolean hasHitPlayer) {
        this.hasHitPlayer = hasHitPlayer;
    }

    @Override
    public String toString() {
        return "Model.Fort " + id + " (" + (destroyed ? "destroyed" : "intact") + ")";

    }

    private static final int[][] T_SHAPE = {{0, 0}, {0, 1}, {-1, 0}, {1, 0}, {0, -1}};
    private static final int[][] L_SHAPE = {{0, 0}, {0, 1}, {0, 2}, {-1, 2}, {-1, 1}};
    private static final int[][] I_SHAPE = {{0, 0}, {0, 1}, {0, 2}, {0, 3}, {0, 4}};

    /**
     * Creates a new fort with a random tetris shape.
     *
     * @param id           the ID of the fort
     * @param existingForts the list of existing forts
     * @return a new fort with a random tetris shape
     */
    public static Fort createRandomTetrisFort(char id, List<Fort> existingForts) {
        Random random = new Random();
        int[][][] shapes = {T_SHAPE, L_SHAPE, I_SHAPE};
        int[][] shape = shapes[random.nextInt(shapes.length)];
        Cell baseCell = new Cell(random.nextInt(10), random.nextInt(10));
        return createTetrisFort(id, shape, existingForts);
    }


private static Fort createTetrisFort(char id, int[][] shape, List<Fort> existingForts) {
    List<Cell> cells = new ArrayList<>();
    Random random = new Random();
    for (int attempt = 0; attempt < 100; attempt++) {
        int baseX = random.nextInt(10);
        int baseY = random.nextInt(10);
        boolean validPlacement = true;
        cells.clear();
        for (int i = 0; i < 5; i++) {
            int x = baseX + shape[i][0];
            int y = baseY + shape[i][1];
            if (x < 0 || x >= 10 || y < 0 || y >= 10 || isCellOccupied(new Cell(x, y), existingForts)) {
                validPlacement = false;
                break;
            }
            cells.add(new Cell(x, y));
        }
        if (validPlacement) {
            return new Fort(id, cells);
        }
    }
    return null; // Return null if fort creation fails after the maximum number of attempts
}

    private static boolean isCellOccupied(Cell cell, List<Fort> forts) {
        for (Fort fort : forts) {
            if (fort.getCells().contains(cell)) {
                return true;
            }
        }
        return false;
    }
}
