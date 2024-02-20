public class Cell {
    private int x;
    private int y;
    private boolean hit;
    private boolean fort;
    private char fortId;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.hit = false;
        this.fort = false;
        this.fortId = ' ';
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

    public boolean isFort() {
        return fort;
    }

    public void setFort(boolean fort) {
        this.fort = fort;
    }

    public char getFortId() {
        return fortId;
    }

    public void setFortId(char fortId) {
        this.fortId = fortId;
    }
}
