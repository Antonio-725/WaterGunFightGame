import java.util.ArrayList;
import java.util.List;

public class Fort {
    private List<Cell> cells;
    private char id;
    private boolean destroyed;

    public Fort(char id, List<Cell> cells) {
        this.id = id;
        this.cells = cells;
        this.destroyed = false;
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

    public boolean isHit(Cell shot) {
        return cells.contains(shot);
    }

    public void hit(Cell shot) {
        if (cells.contains(shot)) {
            shot.setHit(true);
            boolean allCellsHit = cells.stream().allMatch(Cell::isHit);
            if (allCellsHit) {
                destroyed = true;
            }
        }
    }

    @Override
    public String toString() {
        return "Fort " + id + " (" + (destroyed ? "destroyed" : "intact") + ")";
    }

}
