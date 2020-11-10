import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("How many opponents?");
        int opponents = scanner.nextInt();

        System.out.println("How many games?");
        int games = scanner.nextInt();
        System.out.println();

        new Table(opponents, games);
    }
}
