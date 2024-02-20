import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
/**
 * Constructs a new GameMain object with the specified number of opponents.

 */

public class GameMain {
    private Board board;
    private Player player;
    private List<Fort> opponents;
    private int opponentScore;
    private int playerScore;

    public GameMain(int numOpponents) {
        this.board = new Board(10);
        this.player = new Player();
        this.opponents = new ArrayList<>();
        this.opponentScore = 0;
        this.playerScore = 0;

        initializeOpponents(numOpponents);
    }

    private void initializeOpponents(int numOpponents) {
        Random random = new Random();
        for (char id = 'A'; id < 'A' + numOpponents; id++) {
            List<Cell> fortCells = new ArrayList<>();
            while (fortCells.size() < 5) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                Cell cell = new Cell(x, y);
                if (!fortCells.contains(cell)) {
                    fortCells.add(cell);
                }
            }
            Fort fort = new Fort(id, fortCells);
            board.addFort(fort);
            opponents.add(fort);
        }
    }

    public void play(boolean cheatMode) {
        // Display cheat board if cheatMode is true
        if (cheatMode) {
            board.display(true, playerScore, opponentScore);
        }

        // Continue playing until the game is over
        while (!board.isGameOver(opponentScore)) {
            // Player's turn
            Cell playerShot = player.shoot();
            if (playerShot == null) {
                continue;
            }

            boolean playerHit = checkPlayerHit(playerShot);
            displayPlayerHitResult(playerHit, playerScore, opponentScore);

            // Display the board after player's turn
            board.display(false, playerScore, opponentScore);

            // Opponents' turn
            playOpponentsTurn();

            // Display the board after opponents' turn
            board.display(false, playerScore, opponentScore);
        }

        // Display final scores and game result
        displayGameResult();
    }

    private boolean checkPlayerHit(Cell playerShot) {
        for (Fort opponent : opponents) {
            if (opponent.isHit(playerShot)) {
                opponent.hit(playerShot);
                playerScore += calculatePoints(opponent);
                System.out.println("Hit opponent fort " + opponent.getId() + "!");
                return true;
            }
        }
        System.out.println("Missed! No opponent fort hit.");
        return false;
    }

    private void displayPlayerHitResult(boolean playerHit, int playerScore, int opponentScore) {
        if (playerHit) {
            System.out.println("Player score: " + playerScore + "/2500");
            System.out.println("Opponent score: " + opponentScore + "/2500");
        }
    }

    private void playOpponentsTurn() {
        for (Fort opponent : opponents) {
            if (!opponent.isDestroyed()) {
                if (opponentMissesToHit()) {
                    System.out.println("Opponent fort " + opponent.getId() + " missed the target.");
                    continue;
                }

                int points = calculatePoints(opponent);
                opponentScore += points;
                System.out.println("Opponent fort " + opponent.getId() + " scored " + points + " points.");
            }
        }
    }

    private void displayGameResult() {
        System.out.println("Player score: " + playerScore + "/2500");
        System.out.println("Opponent score: " + opponentScore + "/2500");

        if (board.allFortsDestroyed()) {
            System.out.println("Congratulations! You have destroyed all opponent forts. You win!");
        } else {
            System.out.println("Game over! The opponent team has scored 2500 points. You lose!");
        }
    }

    private boolean opponentMissesToHit() {
        Random random = new Random();
        return random.nextInt(100) < 20; // 20% chance of missing the target
    }

    private int calculatePoints(Fort opponent) {
        int undamagedCells = (int) opponent.getCells().stream().filter(cell -> !cell.isHit()).count();
        switch (undamagedCells) {
            case 5:
                return 20;
            case 4:
                return 20;
            case 3:
                return 5;
            case 2:
                return 2;
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        int numOpponents = 5;
        boolean cheatMode = Arrays.asList(args).contains("--cheat");

        GameMain game = new GameMain(numOpponents);
        game.play(cheatMode);
    }

}
