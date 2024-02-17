import java.util.Scanner;

public class Player {
    private Scanner scanner;

    public Player() {
        this.scanner = new Scanner(System.in);
    }

    public Cell shoot() {
        System.out.print("Enter target (e.g., B5): ");
        String target = scanner.nextLine().toUpperCase();

        if (target.equals("--CHEAT")) {
            return null; // Handle cheat input differently
        }

        // Validate input format
        if (!target.matches("[A-J][1-9]|10")) {
            System.out.println("Invalid input. Please enter a valid target (e.g., B5).");
            return null;
        }

        int x = target.charAt(0) - 'A';
        int y = Integer.parseInt(target.substring(1)) - 1;

        return new Cell(x, y);
    }



}
