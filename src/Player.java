import java.util.Scanner;

public class Player {
    private Scanner scanner;

    public Player() {
        this.scanner = new Scanner(System.in);
    }

    public Cell shoot() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter target (e.g., D10): ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.matches("[A-J]10|[A-J][1-9]")) {
            System.out.println("Invalid input. Please enter a valid target (e.g., B5).");
            return null;
        }
        int x = input.charAt(0) - 'A';
        int y = Integer.parseInt(input.substring(1)) - 1;
        return new Cell(x, y);
    }

}









