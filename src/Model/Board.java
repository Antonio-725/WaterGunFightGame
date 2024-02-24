package Model;

import java.util.List;

public class Board {
    private Cell[][] cells;
    private boolean gameOver;

    public Board(int size) {
        cells = new Cell[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                cells[row][col] = new Cell(row, col);
            }
        }
        gameOver = false;
    }
    /**
     * Adds a fort to the board.
     *
     * @param fort the fort to add
     */

    public void addFort(Fort fort) {
        List<Cell> fortCells = fort.getCells();
        char fortId = fort.getId();
        for (Cell cell : fortCells) {
            int x = cell.getX();
            int y = cell.getY();
            if (x >= 0 && x < cells.length && y >= 0 && y < cells[x].length) {
                cells[x][y].setFort(true);
                cells[x][y].setFortId(fortId);
            }
        }
    }
    /**
     * Displays the board.
     *
     * @param cheatMode    true if cheat mode is enabled, false otherwise
     *
     * @param opponentScore the opponent's score
     */

    public String display(boolean cheatMode, int opponentScore) {
        StringBuilder builder = new StringBuilder();
        builder.append("  1 2 3 4 5 6 7 8 9 10\n");
        char rowLabel = 'A';
        for (int row = 0; row < cells.length; row++) {
            builder.append(rowLabel).append(" ");
            for (int col = 0; col < cells[row].length; col++) {
                Cell cell = cells[row][col];
                char displayChar = getDisplayChar(cell, cheatMode);
                builder.append(displayChar).append(" ");
            }
            builder.append("\n");
            rowLabel++;
        }

        builder.append("Opponent score: ").append(opponentScore).append("/2500\n");
        return builder.toString();
    }


    private char getDisplayChar(Cell cell, boolean cheatMode) {
        if (cell.isHit()) {
            if (gameOver) {
                return cell.isFortHit() ? Character.toLowerCase(cell.getFortId()) : ' ';
            } else {
                return cell.isFort() ? 'X' : ' ';
            }
        } else {

            return (cheatMode && cell.isFort()) ? cell.getFortId() : (cell.isTargetHit() ? ' ' : '~');
        }
    }


    public boolean isGameOver(int opponentScore, List<Fort> opponents) {
        boolean allOpponentFortsDestroyed = opponents.stream().allMatch(Fort::isDestroyed);

        gameOver = allOpponentFortsDestroyed || opponentScore >= 2500;
        return gameOver;
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

    public void markTargetHit(int row, int col) {
        cells[row][col].setHit(true);
        cells[row][col].setTargetHit(true);
    }
}
