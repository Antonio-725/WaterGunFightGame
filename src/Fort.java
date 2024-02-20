import java.util.List;

public class Fort {
    private List<Cell> cells;
    private char id;
    private boolean destroyed;
    private boolean hasHitPlayer;

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

    public boolean isHit(Cell shot) {
        for (Cell cell : cells) {
            if (cell.getX() == shot.getX() && cell.getY() == shot.getY()) {
                return true;
            }
        }
        return false;
    }


    public void hit(Cell shot) {
        if (cells.contains(shot)) {
            shot.setHit(true);
            boolean allFortCellsHit = cells.stream().allMatch(Cell::isHit);
            if (allFortCellsHit) {
                destroyed = true;
            }
        }
    }


    public boolean hasHitPlayer() {
        return hasHitPlayer;
    }

    public void setHitPlayer(boolean hasHitPlayer) {
        this.hasHitPlayer = hasHitPlayer;
    }

    @Override
    public String toString() {
        return "Fort " + id + " (" + (destroyed ? "destroyed" : "intact") + ")";
    }

}
