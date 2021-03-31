package battleship;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * @author Mack_TB
 * @version 1.0.7
 * @since 03/26/2021
 */

/**
 * The meaning of different symbols used in the battlefield:
 * - ~: fog;
 * - O: cell of a ship
 * - X: hit
 * - M: miss
 */

public class BattleField {
    public final char[][] board;
    public final char[][] fogOfWar;
//    private  Map<Ship, Integer[]> fleet = new HashMap<>();
    private static final char[] symbols = {'~', 'O', 'X', 'M'};
    private int row0, col0, row1, col1;
    private final String playerName;
    private int numberOfSunkShips = 0;

    public BattleField(String name) {
        this.playerName = name;
        board = new char[10][10];
        initialize(board);

        fogOfWar = new char[10][10];
        initialize(fogOfWar);
    }

    public void initialize(char[][] board) {
        for (int i = 0; i < 10; i++) { // row
            for (int j = 0; j < 10; j++) { // col
                board[i][j] = symbols[0];
            }
        }
    }

    public int getNumberOfSunkShips() {
        return numberOfSunkShips;
    }

    /*    public void run() {
        setShipsOnBoard();
        playGame();
    }*/

    public void setShipsOnBoard() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("%s, place your ships on the game field%n", playerName);
        printBoard(board, true);
        for (Ship ship : Ship.values()) {
            System.out.printf("%nEnter the coordinates of the %s (%d cells):%n%n",
                    ship.getName(), ship.getNumberCells());
            do {
                String firstCoord = scanner.next();
                String secondCoord = scanner.next();
                row0 = Character.getNumericValue(firstCoord.charAt(0)) - 9;
                col0 = Integer.parseInt(firstCoord.substring(1));
                row1 = Character.getNumericValue(secondCoord.charAt(0)) - 9;
                col1 = Integer.parseInt(secondCoord.substring(1));
                swapVal();

                if (handleErrors(firstCoord, secondCoord, ship)) continue;

                if (firstCoord.charAt(0) == secondCoord.charAt(0)) { // row
                    IntStream.rangeClosed(col0, col1)
                            .forEach(j -> board[row0 - 1][j - 1] = symbols[1]);
                } else { // col
                    IntStream.rangeClosed(row0, row1)
                            .forEach(j -> board[j - 1][col0 - 1] = symbols[1]);
                }
                break;
            } while (true);

            printBoard(board, true);
        }
    }

    public void takeAShot(char[][] foesBoard) {
        Scanner scanner = new Scanner(System.in);
        printBoard(board, fogOfWar);
        System.out.printf("%n%s, it's your turn:%n%n", playerName);
        do {
            String coordinate = scanner.next();
            row0 = Character.getNumericValue(coordinate.charAt(0)) - 9;
            col0 = Integer.parseInt(coordinate.substring(1));
            if (row0 > 10 || col0 > 10) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            if (foesBoard[row0 - 1][col0 - 1] == symbols[1]) {
                fogOfWar[row0 - 1][col0 - 1] = symbols[2];
                foesBoard[row0 - 1][col0 - 1] = symbols[2];
                numberOfSunkShips = setNumberOfSunkShips(foesBoard);
            } else if (foesBoard[row0 - 1][col0 - 1] == symbols[2]) {
                System.out.print("\nYou hit a ship!");
            } else {
                fogOfWar[row0 - 1][col0 - 1] = symbols[3];
                foesBoard[row0 - 1][col0 - 1] = symbols[3];
                System.out.print("\nYou missed!");
            }
            break;
        } while (true);
    }

    /*private void playGame() {
        System.out.println("\nThe game starts!");
        printBoard(fogOfWar, true);
        System.out.println("\nTake a shot!\n");
        int numberOfSunkShips = 0;
        do {
            String coordinate = scanner.next();
            row0 = Character.getNumericValue(coordinate.charAt(0)) - 9;
            col0 = Integer.parseInt(coordinate.substring(1));
            if (row0 > 10 || col0 > 10) {
                System.out.println("\nError! You entered the wrong coordinates! Try again:\n");
                continue;
            }

            if (board[row0 - 1][col0 - 1] == symbols[1]) {
                fogOfWar[row0 - 1][col0 - 1] = symbols[2];
                board[row0 - 1][col0 - 1] = symbols[2];
                printBoard(fogOfWar, true);
                numberOfSunkShips = setNumberOfSunkShips(numberOfSunkShips);
                if (numberOfSunkShips == 5){
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                }
            } else if (board[row0 - 1][col0 - 1] == symbols[2]) {
                printBoard(fogOfWar, true);
                System.out.println("\nYou hit a ship! Try again:");
            } else {
                fogOfWar[row0 - 1][col0 - 1] = symbols[3];
                board[row0 - 1][col0 - 1] = symbols[3];
                printBoard(fogOfWar, true);
                System.out.println("\nYou missed!");
            }
        } while (true);
    }*/

    /***
     *   |
     * --+--
     *   |
     * @return int
     */
    private int setNumberOfSunkShips(char[][] foesBoard) {
        int row = row0;
        int col = col0 - 1;
        int i = 1;
        while (i <= 4) {
            if (1 <= row && row <= 10 &&
                1 <= col && col <= 10 &&
                    (foesBoard[row - 1][col - 1] == symbols[1] ||
                    foesBoard[row - 1][col - 1] == symbols[2] && row < 10 && foesBoard[row][col - 1] == symbols[1] ||
                    foesBoard[row - 1][col - 1] == symbols[2] && col < 10 && foesBoard[row - 1][col] == symbols[1])) {
                System.out.print("\nYou hit a ship!");
                break;
            } else {
                if (i <= 2) {
                    col += 2;
                } else {
                    row += 2;
                }
                if (i == 2) {
                    row = row0 - 1;
                    col = col0;
                } else if (i == 4) {
                    numberOfSunkShips++;
                    if (numberOfSunkShips < 5) {
                        System.out.print("\nYou sank a ship! Specify a new target next time");
                    }
                }
            }
            i++;
        }
        return numberOfSunkShips;
    }

    private boolean handleErrors(String firstCoord, String secondCoord, Ship ship) {
        if (row0 != row1 && col0 != col1) {
            System.out.printf("%nError! Wrong ship location! Try again:%n%n");
            return true;
        } else if (row0 - 2 >= 0 && board[row0 - 2][col0 -1] != '~' || (row1 < 10 && board[row1][col1 -1] != '~')) {
            System.out.printf("%nError! You placed it too close to another one. Try again:%n%n");
            return true;
        } else if ((firstCoord.charAt(0) == secondCoord.charAt(0) && col1 - col0 + 1 != ship.getNumberCells()) ||
                (firstCoord.charAt(0) != secondCoord.charAt(0) && row1 - row0 + 1 != ship.getNumberCells())) {
            System.out.printf("%nError! Wrong length of the %s! Try again:%n%n", ship.getName());
            return true;
        }
        return false;
    }

    private void swapVal() {
        if (col0 > col1) { // digit
            col0 = col0 + col1;
            col1 = col0 - col1;
            col0 = col0 - col1;
        } else if (row0 > row1) { // character
            row0 = row0 + row1;
            row1 = row0 - row1;
            row0 = row0 - row1;
        }
    }

    public void printBoard(char[][] board, boolean newLine) {
        char letter = 'A';
        if (newLine) {
            System.out.println();
        }
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (int i = 0; i < board.length; i++) { // row
            System.out.print(letter);
            for (int j = 0; j < board.length; j++) { // col
                System.out.print(" " + board[i][j]);
            }
            System.out.println();
            letter++;
        }
    }

    /**
     * First board contains enemy ships
     * Second board contains your ships
     * @param board
     * @param fogOfWar
     */
    public void printBoard(char[][] board, char[][] fogOfWar) {
        printBoard(fogOfWar, true);
        System.out.println("---------------------");
        printBoard(board, false);
    }
}
