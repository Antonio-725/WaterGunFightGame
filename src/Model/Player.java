package Model;

import Model.Cell;

import java.util.Scanner;

public class Player {
    private Scanner scanner;

    public Player() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Allows the player to shoot at a target.

     */

    public Cell shoot() {
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.matches("[A-J]10|[A-J][1-9]")) {
            return null;
        }
        int x = input.charAt(0) - 'A';
        int y = Integer.parseInt(input.substring(1)) - 1;
        return new Cell(x, y);

    }

}



