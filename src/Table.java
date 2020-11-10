import java.util.Arrays;
import java.util.Random;

public class Table {

    private String[][] gameBoard;

    Random random = new Random();
    WinProbTable winProbTable = new WinProbTable();

    Player player;
    Player[] opponents;
    Player dealer;

    public Table(int opponents, int games) {

        this.player = new Player("Player");

        this.opponents = new Player[opponents];

        for (int i = 0; i < opponents; i++) {

            this.opponents[i] = new Player("Opponent" + i);
        }

        this.dealer = new Player("On the table");

        for (int i = 0; i < games; i++) {

            newGame();

            dealHand(player, 2);
            dealHand(dealer, 5);

            for (int opp = 0; opp < this.opponents.length; opp++) {

                dealHand(this.opponents[opp], 2);
            }

            compareHands();
        }
        winProbTable.setProba();
    }

    public void newGame() {

        gameBoard = new String[4][13];
        this.dealer.clearHand();

        this.player.clearHand();

        for (int opp = 0; opp < this.opponents.length; opp++) {
            this.opponents[opp].clearHand();
        }
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

        for (int opp = 0; opp < this.opponents.length; opp++) {
            new HandCalculator(this, this.opponents[opp]);
        }

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

            int max = 0;

            for (Player opponent : this.opponents) {

                if (player.getHandCode(i) < opponent.getHandCode(i)) {

                    return;
                }

                max = Math.max(max, opponent.getHandCode(i));
            }

            if (player.getHandCode(i) > max) {

                winProbTable.wins[y][x] += 1;
                return;
            }
        }

        //count equality as win (not lose)
        winProbTable.wins[y][x] += 1;
    }

    public String[][] getGameBoard() {
        return gameBoard;
    }
}
