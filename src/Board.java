import java.util.List;

public class Board {
    private Cell[][] cells;

    public Board(int size) {
        cells = new Cell[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public void addFort(Fort fort) {
        List<Cell> fortCells = fort.getCells();
        char fortId = fort.getId();
        for (Cell cell : fortCells) {
            cells[cell.getX()][cell.getY()].setFort(true);
            cells[cell.getX()][cell.getY()].setFortId(fortId);
        }
    }

    public void display(boolean cheatMode, int playerScore, int opponentScore) {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        char rowLabel = 'A';
        for (int row = 0; row < cells.length; row++) {
            System.out.print(rowLabel + " ");
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                char displayChar = cell.isHit() ? 'X' : (cheatMode && cell.isFort() ? cell.getFortId() : '~');
                System.out.print(displayChar + " ");
            }
            System.out.println();
            rowLabel++;
        }
        System.out.println("Player score: " + playerScore + "/2500");
        System.out.println("Opponent score: " + opponentScore + "/2500");
    }

    public boolean isGameOver(int opponentScore) {
        return opponentScore >= 2500;
    }

    public boolean allFortsDestroyed() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.isFort()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void markHit(int row, int col) {
        cells[row][col].setHit(true);
    }
}
