package battleship;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 03/26/2021
 */

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BattleField player1 = new BattleField("Player 1");
        BattleField player2 = new BattleField("Player 2");

        player1.setShipsOnBoard();
        System.out.println("\nPress Enter and pass the move to another player");
        scanner.nextLine();
//        promptEnterKey();
        player2.setShipsOnBoard();
        BattleField currentPlayer = player2;
        BattleField otherPlayer = player1;

        while (true) {
            System.out.println("\nPress Enter and pass the move to another player");
            scanner.nextLine();
//            promptEnterKey();
            currentPlayer = currentPlayer.equals(player1) ? player2 : player1;
            otherPlayer = otherPlayer.equals(player1) ? player2 : player1;
            currentPlayer.takeAShot(otherPlayer.board);
            if (currentPlayer.getNumberOfSunkShips() == 5){
                System.out.println("\nYou sank the last ship. You won. Congratulations!");
                currentPlayer.printBoard(currentPlayer.board, currentPlayer.fogOfWar);
                break;
            }
        }
    }

    public static void promptEnterKey() {
        System.out.println("\nPress Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
