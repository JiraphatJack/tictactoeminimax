/*
Created by: Jiraphat Jackmetha
Name of the project: TicTacToe with Minimax
Date: 18 November 2020
Name of the file: TicTacToe.java
*/
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // create a new board
        Board board1 = new Board(3);

        // random for current player
        int currentPlayer = (int)Math.round(Math.random());
        // set winner to null
        String winner = null;
        // while board is nor full
        while(board1.getCount() < 9) {
            // if current player is the player
            while (currentPlayer == 0) {
                // ask the position from user
                System.out.printf("Enter your Position(1-%d): \n", 9);
                int pos = in.nextInt();
                // player turn will check if that position is empty or not and put player piece to that position
                currentPlayer = playerTurn(pos, currentPlayer, board1);
                // show what current condition of board
                board1.printBoard();
                System.out.println("------------------------------");
                // check if there a winner
                winner = board1.checkWinner();
            }
            // if there is a winner, get out of the loop
            if (winner != null && winner.length() > 0) {
                break;
            }
            // this is for cpu player
            while(currentPlayer == 1) {
                // cpu turn will find the best move for cpu and put cpu in to that position
                currentPlayer = cpuTurn(currentPlayer, board1);
                // show current condition of the board
                board1.printBoard();
                System.out.println("------------------------------");
                // check the winner
                winner = board1.checkWinner();
            }
            // if there is a winner, get out of the loop
            if (winner != null && winner.length() > 0) {
                break;
            }
        }

        // show who is the winner
        if (!winner.equals("tie")) {
            System.out.println("The winner is: " + winner);
        } else {
            System.out.println("The result is tie");
        }
        in.close();
    }
    
    // method to call player turn
    private static int playerTurn(int pos, int currentPlayer, Board board) {
        currentPlayer = board.addPiece(pos, currentPlayer);
        if (currentPlayer == 0) {
            System.out.println("Can't use this position. Please enter new position");
        }
        return currentPlayer;
        
    }
    
    // method to do cpu move
    private static int cpuTurn(int currentPlayer, Board board) {
        currentPlayer = board.bestMove(currentPlayer);
        return currentPlayer;
    }
}
