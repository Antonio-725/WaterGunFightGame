public class Cell {
    private int x;
    private int y;
    private boolean hit;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}
