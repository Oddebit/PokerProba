import java.util.Arrays;

public class WinProbTable {

    public double[][] wins = new double[13][13];
    public double[][] counts = new double[13][13];

    public double[][] proba = new double[13][13];

    public void setProba() {

        for (int i = 0; i < 13; i++) {

            for (int j = 0; j < 13; j++) {

                if (counts[i][j] == 0) {

                    proba[i][j] = 0;

                } else {

                    proba[i][j] = wins[i][j] / counts[i][j] * 100;

                }
            }

            System.out.println(Arrays.toString(proba[i]));
        }
    }
}
