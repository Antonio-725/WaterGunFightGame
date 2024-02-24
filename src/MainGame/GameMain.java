package MainGame;

import Model.Board;
import Model.Cell;
import Model.Fort;
import Model.Player;

import java.util.*;


/**
 * Represents the main game logic.
 */
public class GameMain {
    private Board board;
    private Player player;
    private List<Fort> opponents;
    private int opponentScore;
    private int playerScore;

    /**
     * Constructs a new MainGame.GameMain object with the specified number of opponents.
     *
     * @param numOpponents the number of opponents
     */
    public GameMain(int numOpponents) {
        this.board = new Board(10);
        this.player = new Player();
        this.opponents = new ArrayList<>();
        this.opponentScore = 0;
        this.playerScore = 0;

        initializeOpponents(numOpponents);
    }

    /**
     * Initializes the opponents with random shapes.
     *
     * @param numOpponents the number of opponents to initialize
     */

    private void initializeOpponents(int numOpponents) {
        List<Fort> existingForts = new ArrayList<>(); // Create an empty list to hold existing forts
        for (char id = 'A'; id < 'A' + numOpponents; id++) {
            Fort fort = Fort.createRandomTetrisFort(id, existingForts); // Pass the existing forts list
            if (fort != null) { // Check if fort creation was successful
                board.addFort(fort);
                opponents.add(fort);
                existingForts.add(fort); // Add the new fort to the existing forts list
            }
        }
    }



    /**
     * Starts the game.
     *
     * @param cheatMode {@code true} if cheat mode is enabled, {@code false} otherwise
     */
    /**
     * Starts the game.
     *
     * @param cheatMode {@code true} if cheat mode is enabled, {@code false} otherwise
     */
    public void play(boolean cheatMode) {
        String boardDisplay;
        if (cheatMode) {
            boardDisplay = board.display(true, opponentScore);
        } else {
            boardDisplay = board.display(false, opponentScore);
        }
        System.out.println(boardDisplay);

        while (!board.isGameOver(opponentScore,opponents)) {
            System.out.print("Enter target (e.g., D10): ");
            Cell playerShot = player.shoot();
            if (playerShot == null) {
                System.out.println("Invalid move. Please enter a valid target.");
                continue;
            }

            boolean playerHit = checkPlayerHit(playerShot);
            if (playerHit) {
                board.markTargetHit(playerShot.getX(), playerShot.getY());
            } else {
                board.markHit(playerShot.getX(), playerShot.getY());
            }

            boardDisplay = board.display(false, opponentScore); // Display the board after each turn
            System.out.println(boardDisplay);

            playOpponentsTurn();
        }

        displayGameResult();
    }

    private boolean checkPlayerHit(Cell playerShot) {
        for (Fort opponent : opponents) {
            if (opponent.isHit(playerShot)) {
                opponent.hit(playerShot);
                playerScore += calculatePoints(opponent);
                System.out.println("Hit opponent fort " + opponent.getId() + "!");
                if (opponent.isCompletelyDestroyed()) {
                    System.out.println("Opponent fort " + opponent.getId() + " has been destroyed!");
                }
                return true;
            }
        }
        System.out.println("Missed! No opponent fort hit.");
        return false;
    }

    private void playOpponentsTurn() {
        Iterator<Fort> iterator = opponents.iterator();
        while (iterator.hasNext()) {
            Fort opponent = iterator.next();
            if (!opponent.isDestroyed()) {
                int points = calculatePoints(opponent);
                opponentScore += points;
                System.out.println("Opponent fort " + opponent.getId() + " scored " + points + " points.");

                if (opponent.isDestroyed()) {
                    System.out.println("Opponent fort " + opponent.getId() + " is destroyed. They are out of the game.");
                    iterator.remove();
                }
            }
        }
    }

    private void displayGameResult() {
        String boardDisplay = board.display(true, opponentScore); // Display the board without cheat mode
        System.out.println(boardDisplay);


        System.out.println("Opponent score: " + opponentScore + "/2500");

        if (board.isGameOver(opponentScore,opponents)) {
            System.out.println("Congratulations! You win!");
        } else {
            System.out.println("Game over! The opponent team has scored " + opponentScore + " points. You lose!");
        }
    }


    private int calculatePoints(Fort opponent) {
        int totalCells = opponent.getCells().size();
        int undamagedCells = (int) opponent.getCells().stream().filter(cell -> !cell.isHit()).count();
        double percentageRemaining = (double) undamagedCells / totalCells;

        if (percentageRemaining >= 0.8) {
            return 20;
        } else if (percentageRemaining >= 0.6) {
            return 15;
        } else if (percentageRemaining >= 0.4) {
            return 10;
        } else if (percentageRemaining >= 0.2) {
            return 5;
        } else {
            return 2;
        }
    }

    /**
     * The main method to start the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int numOpponents = 5;
        boolean cheatMode = Arrays.asList(args).contains("--cheat");

        GameMain game = new GameMain(numOpponents);
        game.play(cheatMode);
    }
}
