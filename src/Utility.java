public class Utility {

    public static void main(String[] args){

        System.out.println(probWantedInToDealOutDeck(2, 4, 6));
    }

    public static double probWantedInToDealOutDeck(int wanted, int toDeal, int inDeck) {

        return (combination(toDeal - wanted, inDeck - wanted))
                / combination(toDeal, inDeck);
    }

    public static double combination(int toDeal, int inDeck) {

        return factorial(inDeck) / factorial(toDeal) * (factorial(inDeck - toDeal));
    }

    public static double factorial(int n) {

        double result = 1;

        for (int i = 2; i <= n; i++) {

            result = result* i;
        }

        return (result);
    }
}
