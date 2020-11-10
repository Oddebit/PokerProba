public class Utility {

    public static void main(String[] args){

        System.out.println(combination(169, 13));

    }

    public static double probabilityToGetNOuts(int deck, int deal, int outsDeck, int outsDeal) {

        if (outsDeck < deal) return 0;

        return combination(outsDeck, outsDeal) * combination(deck - outsDeck, deal - outsDeal)
                / combination(deck, deal);
    }

    public static double combination(int n, int r) {

        if (n < r) return 0;
        if (r == 0) return 1;
        if (n == 0) return 0;
        if (r == n) return 1;
        if (r == 1) return n;

        return factorial(r, n) / factorial(r);
    }

    public static double factorial(int max) {

        return factorial(max - 1, max);
    }

    public static double factorial(int range, int max) {

        range--;
        double result = max - range;

        for (int i = max - range + 1; i <= max; i++) {

            result = result * i;
        }

        return result;
    }
}
