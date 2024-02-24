package Model;

public class Cell {
    private int x;
    private int y;
    private boolean isHit;
    private boolean isTargetHit;
    private boolean isFort;
    private char fortId;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.isHit = false;
        this.isTargetHit = false;
        this.isFort = false;
        this.fortId = ' ';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    public boolean isTargetHit() {
        return isTargetHit;
    }

    public void setTargetHit(boolean targetHit) {
        isTargetHit = targetHit;
    }

    public boolean isFort() {
        return isFort;
    }

    public void setFort(boolean fort) {
        isFort = fort;
    }

    public char getFortId() {
        return fortId;
    }

    public void setFortId(char fortId) {
        this.fortId = fortId;
    }

    public boolean isFortHit() {
        return isHit && isFort;
    }
}















