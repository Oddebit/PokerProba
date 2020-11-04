import java.util.ArrayList;

public class HandCalculator {

    private int[][] gameBoard;

    Table table;
    Player dealer;

    Player player;


    // CONSTRUCTORS
    public HandCalculator(Table table, Player player) {

        this.gameBoard = new int[5][14];
        this.table = table;
        this.player = player;
        this.dealer = table.dealer;

        boardTranslation();

        setBestHand();
    }

    private void boardTranslation() {

        for (int s = 0; s < 4; s++) {

            for (int v = 0; v < 13; v++) {

                if(isPlayersCard(s, v)) {

                    gameBoard[s][v] += 1;
                    gameBoard[s][13] += 1;
                    gameBoard[4][v] += 1;
                }
            }
        }

    }

    private boolean isPlayersCard(int suit, int value) {

        if (this.table.getGameBoard()[suit][value] == null) return false;

        return this.table.getGameBoard()[suit][value].equals(this.player.getName())
                || this.table.getGameBoard()[suit][value].equals(this.dealer.getName());
    }

    public void setBestHand() {

        if (checkStraightFlush() != -1) {
            addStraightFlush(checkStraightFlush());
        } else if (checkFourOfAKind() != -1) {
            addFourOfAKind(checkFourOfAKind());
        } else if (checkFullHouse()[0] != -1 && checkFullHouse()[1] != -1) {
            addFullHouse(checkFullHouse());
        } else if (checkFlush() != -1) {
            addFlush(checkFlush());
        } else if (checkStraight() != -1) {
            addStraight(checkStraight());
        } else if (checkThreeOfAKind() != -1) {
            addThreeOfAKind(checkThreeOfAKind());
        } else if (checkPairs().size() > 0) {
            addPairs(checkPairs());
        } else {
            addHighCard(5);
        }
    }


    // STRAIGHT FLUSH
    public int checkStraightFlush() {

        int suit = checkFlush();

        if (suit != -1) {

            for (int v1 = 12; v1 >= 0; v1--) {

                if (gameBoard[suit][v1] > 0) {

                    int count = 0;

                    for (int v2 = v1; v2 >= 0; v2--) {

                        if (gameBoard[suit][v2] == 1) {
                            count++;
                            if (v2 == 0 && gameBoard[suit][13] > 0) {
                                count++;
                            }
                        } else {
                            break;
                        }
                    }

                    if (count >= 5) return v1;
                }
            }
        }

        return -1;
    }

    private void addStraightFlush(int value) {

        if (value != -1) {

            player.setHandCode(0, 8);
            player.setHandCode(1, value + 2);
        }
    }


    // FOUR OF A KIND
    public int checkFourOfAKind() {

        if (player.getHandCode(0) != 0) {

            for (int v = 12; v >= 0; v--) {

                if (gameBoard[4][v] == 4) {

                    return v;
                }
            }
        }

        return -1;
    }

    private void addFourOfAKind(int value) {

        if (value != -1) {

            player.setHandCode(0, 7);
            player.setHandCode(1, value + 2);

            addHighCard(1);
        }
    }


    // FULL HOUSE
    public int[] checkFullHouse() {

        int threeOfAKind = checkThreeOfAKind();
        int pair = checkPairs().size() > 0 ? checkPairs().get(0) : -1;

        if (threeOfAKind != -1 && pair != -1) {
            return new int[] {threeOfAKind, pair};
        } else {
            return new int[]{-1, -1};
        }
    }

    private void addFullHouse(int[] values) {

        if (values[0] != -1 && values[1] != -1) {

            player.setHandCode(0, 6);
            player.setHandCode(1, values[0] + 2);
            player.setHandCode(2, values[1] + 2);
        }
    }


    // FLUSH
    public int checkFlush() {

        if (player.getHandCode(0) != 0) {

            for (int s = 0; s < 4; s++) {

                if (gameBoard[s][13] >= 5) {

                    return s;
                }
            }
        }

        return -1;
    }

    private void addFlush(int suit) {

        if (suit != -1) {

            player.setHandCode(0, 5);

            int v = 12;
            int count = 0;
            while (count < 5) {

                if (gameBoard[suit][v] == 1) {

                    player.setHandCode(count + 1, v + 2);
                    count++;
                }
                v--;
            }
        }
    }



    // STRAIGHT
    public int checkStraight() {

        if (player.getHandCode(0) != 0) {

            for (int v1 = 12; v1 >= 0; v1--) {

                if (gameBoard[4][v1] > 0) {

                    int count = 0;

                    for (int v2 = v1; v2 >= 0; v2--) {

                        if (gameBoard[4][v2] > 0) {
                            count++;
                            if (v2 == 0 && gameBoard[4][13] > 0) {
                                count++;
                            }
                        } else {
                            break;
                        }
                    }

                    if (count >= 5) return v1;
                }
            }
        }

        return -1;
    }

    private void addStraight(int value) {

        if (value != -1) {

            player.setHandCode(0, 4);
            player.setHandCode(1, value + 2);
        }
    }


    // THREE OF A KIND
    public int checkThreeOfAKind() {

        if (player.getHandCode(0) != 0) {

            for (int v = 12; v >= 0; v--) {

                if (gameBoard[4][v] == 3) {

                    return v;
                }
            }
        }

        return -1;
    }

    private void addThreeOfAKind(int value) {

        if (value != -1) {

            player.setHandCode(0, 3);
            player.setHandCode(1, value + 2);

            addHighCard(2);
        }
    }


    // PAIR
    public ArrayList<Integer> checkPairs() {

        ArrayList<Integer> pairs = new ArrayList<>();

        for (int v = 12; v >= 0 && pairs.size() < 2; v--) {

            if (gameBoard[4][v] == 2) {

                pairs.add(v);
            }
        }

        return pairs;
    }

    private void addPairs(ArrayList<Integer> values) {

        if (values.size() != 0) {

            if (values.size() == 2) {

                addHighCard(1);
                player.setHandCode(0, 2);

            } else if (values.size() == 1) {

                addHighCard(3);
                player.setHandCode(0, 1);

            }

            for (int i = 0; i < 2; i++) {

                if (i < values.size()) {

                    player.setHandCode(i + 1, values.get(i) + 2);

                } else {

                    player.setHandCode(i + 1, -1);
                }
            }
        }
    }




    public void addHighCard(int n) {

        int v = 12;

        //ATTENTION : ERREUR A CORRIGER!!
        while (n > 0 && v >=0) {

            if (gameBoard[4][v] == 1) {

                player.setHandCode(6 - n, v + 2);
                n--;
            }

            v--;
        }
    }
}