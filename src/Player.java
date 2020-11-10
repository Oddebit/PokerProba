import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int[] handCode = new int[6];


    public Player() {
        this("X");
    }

    public Player(String name) {

        this.name = name;
        this.hand = new ArrayList<>();
    }

    public void clearHand() {

        this.hand.clear();
        Arrays.fill(this.handCode, 0);
    }

    public void receivesCard(Card card) {

        this.hand.add(card);
    }

    public void setHandCode(int index, int value) {
        this.handCode[index] = value;
    }


    public int getHandCode(int index) {
        return handCode[index];
    }
    public int[] getHandCode() { return handCode; }

    public int getCardValue(int index) {
        return hand.get(index).getValue();
    }

    public String getName() {
        return name;
    }

    public boolean isHandSuited() {
        return hand.get(0).getSuit() == hand.get(1).getSuit();
    }
}
