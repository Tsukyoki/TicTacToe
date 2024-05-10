// Program: TicTacToe
// Written by: Kyle Nguyen
// File name: TicTacToe
// File description: Tic tac toe game where the user versus a bot in a 3x3 grid. The program uses a HashMap to store the positions of each player. The keys represent the coordinates on the grid, and the values represent the player and bot
// Other files in this project: None
// Challenges: creating algorithms to iterate and search through the current board, logic/game conditions(win conditions, filled spaces)
// Time Spent: 
//
//               Revision History
// Date:         By:             Action:
// ---------------------------------------------------
// 5/7/2024     KN             Created file, main method, outlined interface
// 5/9/2024     KN             Created and defined methods, created hashmap to hold the game
// 5/10/2024     KN            Modified game to allow mulitple plays, exit, error handling (invalid inputs, full board)

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    // static variables to read input from the user and random generator for the bot's moves
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    
    public static void main(String[] args) {
        // main method with a while loop the keep the game looped
        while (true) {
            playGame();
            
            System.out.print("Do you want to play again? (yes/no): ");
            String playAgain = scanner.next().toLowerCase();
            if (!playAgain.equals("yes")) {
                System.out.println("Thanks for playing! Goodbye!");
                break;
            }
        }
    }
    // method to initialize the tic tac toe game
    private static void playGame() {
        // initialize the board and stores it into the hashmap
        HashMap<String, Character> board = initializeBoard();
        boolean playerTurn = true;

        while (true) {
            // displays the current state of the board
            displayBoard(board);
            // player can make a move when it is the player's turn
            if (playerTurn) {
                playerMove(board);
                // after player's move, it will check if the player has won
                if (checkWin(board, 'X')) {
                    displayBoard(board);
                    System.out.println("Congratulations! You win!");
                    break;
                }
                // generates a move for the bot and checks if the bot has won the game
            } else {
                botMove(board);
                if (checkWin(board, 'O')) {
                    displayBoard(board);
                    System.out.println("Sorry, you lose!");
                    break;
                }
            }
            // if the whole board is full and no one has won, it's a draw
            if (isBoardFull(board)) {
                displayBoard(board);
                System.out.println("It's a draw!");
                break;
            }
            // switches who goes first
            playerTurn = !playerTurn;
        }
    }
    // method to create the board, String represents the cell coordinates and character represents tthe content 
    private static HashMap<String, Character> initializeBoard() {
        // creates a hashmap to represent the game, the strings 
        HashMap<String, Character> board = new HashMap<>();
        // for loop that iterates the rows and colums that puts _ for each empty value
        // i represents te row and j represents the collum ( i = 0 j=0 is the top left value
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.put("" + i + j, '_');
            }
        }
        return board;
    }
    // method to display the full board that takes in the hashmap
    private static void displayBoard(HashMap<String, Character> board) {
        System.out.println("  0 1 2");
        // i iterates through the rows of the board, also iterating through each column j printing out the coordinate of each cell (i, j)
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board.get("" + i + j) + " ");
            }
            System.out.println();
        }
    }
    // method to handle player moves
    private static void playerMove(HashMap<String, Character> board) {
        while (true) {
            System.out.print("Enter your move (row column), or type 'exit' to quit: ");
            String input = scanner.next();
            if (input.equals("exit")) {
                System.out.println("Exiting the game...");
                System.exit(0);
            }
            // promts the user to enter their desired move, row then column
            int row, col;
            try {
                row = Integer.parseInt(input);
                col = scanner.nextInt();
                // exception handling if the player inputs an invalid character
            } catch (NumberFormatException | java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter row and column numbers.");
                scanner.nextLine(); // Consume the invalid input
                continue;
            }
            // if the desired move is available, it will place an X marking the players move
            if (isValidMove(board, row, col)) {
                board.put("" + row + col, 'X');
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }
    // method to check if a mvoe is valid, taking in the board parameter, row, and column 
    private static boolean isValidMove(HashMap<String, Character> board, int row, int col) {
        // checks if the row/column number is between 0-3 and checks if the value on the board is an _
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board.get("" + row + col) == '_';
    }
    
    // method to make a random bot move that takes in the hashmap as a parameter
    private static void botMove(HashMap<String, Character> board) {
        while (true) {
            System.out.print("Bot's Move: ");
            // picks a random number between 1-3 and then checks if its a valid move
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (isValidMove(board, row, col)) {
                board.put("" + row + col, 'O');
                System.out.print("Bot has made a move at: " + row + " " + col);
                System.out.println();
                break;
            }
        }
    }
    // method that checks each possilbe 3 in a row/column/diagnol to see if a player wins
    private static boolean checkWin(HashMap<String, Character> board, char player) {
        // check rows, column, and diagnols for a winner
        for (int i = 0; i < 3; i++) {
            // row win
            if (board.get("" + i + 0) == player && board.get("" + i + 1) == player && board.get("" + i + 2) == player) {
                return true; 
            }
            // column win
            if (board.get("0" + i) == player && board.get("1" + i) == player && board.get("2" + i) == player) {
                return true; 
            }
        }
        // top left to bottom right diagonal win
        if (board.get("00") == player && board.get("11") == player && board.get("22") == player) {
            return true;
        }
        // top right to bottome left diagonal win
        if (board.get("02") == player && board.get("11") == player && board.get("20") == player) {
            return true; 
        }
        return false;
    }
    //  method that checks if the board has no more empty spaces
    private static boolean isBoardFull(HashMap<String, Character> board) {
        // iterates through the boartd to check if the value is empty (_)
        for (char value : board.values()) {
            if (value == '_') return false;
        }
        return true;
    }
}
