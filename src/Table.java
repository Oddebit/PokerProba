import java.util.Random;

public class Table {

    private String[][] gameBoard;

    Random random = new Random();
    WinProbTable winProbTable = new WinProbTable();

    Player player;
    Player opponent;
    Player dealer;

    public Table() {

        this.player = new Player("Player");
        this.opponent = new Player("Opponent");
        this.dealer = new Player("On the table");

        for (int i = 0; i < 1_000_000_000; i++) {

            newGame();

            dealHand(player, 2);
            dealHand(opponent, 2);
            dealHand(dealer, 5);

            compareHands();
        }
        winProbTable.setProba();
    }

    public void newGame() {

        this.player.clearHand();
        this.opponent.clearHand();
        this.dealer.clearHand();

        gameBoard = new String[4][13];

    }

    public void dealHand(Player player, int cards) {

        for (int i = 0; i < cards; i++) {

            boolean occupied = true;
            while (occupied) {

                int suit = random.nextInt(4);
                int value = random.nextInt(13);

                if (gameBoard[suit][value] == null) {

                    gameBoard[suit][value] = player.getName();
                    player.receivesCard(new Card(value, suit));
                    occupied = false;
                }
            }

        }
    }

    public void compareHands() {

        new HandCalculator(this, player);
        new HandCalculator(this, opponent);

        int x;
        int y;

        if(player.isHandSuited()) {
            x = Math.max(player.getCardValue(0), player.getCardValue(1));
            y = Math.min(player.getCardValue(0), player.getCardValue(1));
        } else {
            x = Math.min(player.getCardValue(0), player.getCardValue(1));
            y = Math.max(player.getCardValue(0), player.getCardValue(1));
        }

        winProbTable.counts[y][x] += 1;

        for (int i = 0; i < 6; i++) {
            if (player.getHandCode(i) > opponent.getHandCode(i)) {
                winProbTable.occurences[y][x] += 1;
                return;
            } else if (player.getHandCode(i) < opponent.getHandCode(i)) {
                return;
            }
        }

        //count equality as win (not lose)
        winProbTable.occurences[y][x] += 1;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }
}
