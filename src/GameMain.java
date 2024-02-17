import java.util.*;

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

    public void play() {
        boolean cheatMode = false;

        if (System.getProperty("cheat") != null && System.getProperty("cheat").equals("true")) {
            cheatMode = true;
            board.display(cheatMode, playerScore, opponentScore);
        }

        while (!board.isGameOver(opponentScore)) {
            // Player's turn
            Cell playerShot = player.shoot();
            if (playerShot == null) {
                board.display(true, playerScore, opponentScore);
                continue;
            }

            boolean playerHit = false;
            for (Fort opponent : opponents) {
                if (opponent.isHit(playerShot)) {
                    opponent.hit(playerShot);
                    System.out.println("Hit opponent fort " + opponent.getId() + "!");
                    playerHit = true;
                    break;
                }
            }

            if (!playerHit) {
                System.out.println("Missed! No opponent fort hit.");
            }

            // Opponents' turn
            for (Fort opponent : opponents) {
                if (!opponent.isDestroyed()) {
                    int points = calculatePoints(opponent);
                    opponentScore += points;
                    System.out.println("Opponent fort " + opponent.getId() + " scored " + points + " points.");
                }
            }

            board.display(cheatMode, playerScore, opponentScore);
        }

        if (board.allFortsDestroyed()) {
            System.out.println("Congratulations! You have destroyed all opponent forts. You win!");
        } else {
            System.out.println("Game over! The opponent team has scored 2500 points. You lose!");
        }
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
        if (args.length > 0) {
            numOpponents = Integer.parseInt(args[0]);
        }

        GameMain game = new GameMain(numOpponents);
        game.play();
    }

}
