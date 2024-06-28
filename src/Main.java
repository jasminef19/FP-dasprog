import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        System.out.println("enter player 1 name:");
        String firstPlayer = read.nextLine();
        System.out.println("Enter player 1 birth date (1-31):");
        int firstPlayerBirthDate = read.nextInt();
        read.nextLine();

        System.out.println("enter player 2 name:");
        String secondPlayer = read.nextLine();
        System.out.println("Enter player 2 birth date (1-31):");
        int secondPlayerBirthDate = read.nextInt();
        read.nextLine();
        Player p1 = new Player(firstPlayer, firstPlayerBirthDate);
        Player p2 = new Player(secondPlayer, secondPlayerBirthDate);
        SnakeAndLadder g1 = new SnakeAndLadder(100);
        g1.initiateGame();
        g1.addPlayer(p1);
        g1.addPlayer(p2);

        int nextPlayerHasReducedRoll = 0;

        do {
            int t1 = g1.getTurn();
            g1.setStatus(1);
            g1.setPlayerInTurn(t1);
            Player playerInTurn = g1.getPlayers().get(t1);
            boolean extraTurn = false;

            do {
                System.out.println("Player in turn is " + playerInTurn.getUserName());
                System.out.println(playerInTurn.getUserName() + " it's your turn, please press enter to roll the dice");
                String input = read.nextLine();
                int x = 0;
                if (input.isEmpty()) {
                    x = playerInTurn.rollDice();
                }

                System.out.println("Dice number: " + x);

                if (nextPlayerHasReducedRoll == 1) {
                    System.out.println(playerInTurn.getUserName() + " had ladder 2 times, next turn the dice is reduced by 2.");
                    if (x < 2) {
                        x = 2;
                    }
                    nextPlayerHasReducedRoll = 0;
                } else {
                    nextPlayerHasReducedRoll = 0;
                }

                g1.movePlayer(playerInTurn, x);

                if (x == 6) {
                    System.out.println(playerInTurn.getUserName() + " gets an extra turn!");
                    extraTurn = true;
                } else {
                    extraTurn = false;
                }
            } while (extraTurn);

            if (playerInTurn.getLadderCount() >= 2) {
                nextPlayerHasReducedRoll = 1;
                playerInTurn.setLadderCount(0);
            }

        } while (g1.getStatus() != 2);
    }
}