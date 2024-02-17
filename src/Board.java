import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private Cell[][] cells;
    private List<Fort> forts;

    public Board(int size) {
        cells = new Cell[size][size];
        forts = new ArrayList<>();
        initializeCells();
    }

    private void initializeCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public void addFort(Fort fort) {
        forts.add(fort);
    }

    public boolean allFortsDestroyed() {
        return forts.stream().allMatch(Fort::isDestroyed);
    }

    public boolean isGameOver(int opponentScore) {
        return allFortsDestroyed() || opponentScore >= 2500;
    }

    public void display(boolean cheatMode, int playerScore, int opponentScore) {
        System.out.println("   1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < cells.length; i++) {
            System.out.print((char) ('A' + i) + "  ");
            for (int j = 0; j < cells[i].length; j++) {
                if (cheatMode) {
                    boolean fortCell = false;
                    for (Fort fort : forts) {
                        for (Cell cell : fort.getCells()) {
                            if (cell.getX() == i && cell.getY() == j) {
                                System.out.print(fort.getId() + " ");
                                fortCell = true;
                                break;
                            }
                        }
                        if (fortCell) {
                            break;
                        }
                    }
                    if (!fortCell) {
                        System.out.print(". ");
                    }
                } else {
                    Cell cell = cells[i][j];
                    if (cell.isHit()) {
                        System.out.print("x ");
                    } else {
                        System.out.print("~ ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println("Player score: " + playerScore+"/2500");
        System.out.println("Opponent score: " + opponentScore+"/2500");
    }

}
