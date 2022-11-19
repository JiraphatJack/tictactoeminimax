/*
Created by: Jiraphat Jackmetha
Name of the project: TicTacToe with Minimax
Date: 18 November 2020
Name of the file: Board.java
*/
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;

public class Board {
    // instance variables for board class
    private char[][] board;
    private Stack<Integer> stack = new Stack<Integer>();
    private Map<String, Integer> scores = new HashMap<String, Integer>();
    private char[] players = {'O', 'X'};
    private int[] move = new int[2];

    // constant for improve minimax
    private static final int MAX_DEPTH = 6;

    // default constructor for 3x3 board
    public Board() {
        this(3);
    }

    // overload constructor for nxn board
    public Board(int n) {
        board = new char[n][n];
        // fill board with ' '
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = ' ';
            }
        }
        
        // put the score into map
        scores.put("X", 10);
        scores.put("O", -10);
        scores.put("tie", 0);
    }

    // method to show what is the board right now
    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                System.out.print("  - + ");
            }
             else if (i != 3 - 1) {
                System.out.print("- + ");
            } else {
                System.out.println("-");
            }
        }
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                char spot = board[i][j];
                System.out.print(spot + " | "); 
            }
            System.out.println();
            for (int k = 0; k < 3; k++) {
                if (k == 0) {
                    System.out.print("  - + ");
                } else if (k != 3 - 1) {
                    System.out.print("- + ");
                } 
                else {
                    System.out.println("-");
                }
            }
        }
        
    }
 
    // add player piece
    public int addPiece(int pos, int player) {
        int i = (pos - 1) / 3;
        int j = (pos - 1) % 3;
        // check if position is empty
        if (stack.search(pos-1) == -1) {
            stack.push(pos - 1);
            board[i][j] = players[player];
            player = player == 0 ? 1 : 0;
        }
        return player;
    }

    // find best move for cpu by using minimax
    public int bestMove(int player) {
        int bestScore = -1000;
        move[0] = -1;
        move[1] = -1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (stack.search(3 * i + j) == -1) {
                    board[i][j] = players[player];
                    stack.push(3 * i + j);
                    int score = minimax(false, MAX_DEPTH);
                    board[i][j] = ' ';
                    stack.pop();
                    if (score > bestScore) {
                        bestScore = score;
                        move[0] = i;
                        move[1] = j;
                    }
                }
            }
        }
        board[move[0]][move[1]] = players[player];
        stack.push(3*move[0] + move[1]);
        player = 0;
        return player;
    }

    private int minimax(boolean isMaximizing, int depth) {
        String result = checkWinner();
        if (result != null) {
            if (result.equals("X")) {
                return scores.get(result) + depth;
            } else if (result.equals("O")) {
                return scores.get(result) - depth;
            }
            return scores.get(result);
        }
        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (stack.search(3 * i + j) == -1) {
                        board[i][j] = players[1];
                        stack.push(3 * i + j);
                        bestScore = Math.max(bestScore, minimax(!isMaximizing, depth - 1));
                        stack.pop();
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (stack.search(3 * i + j) == -1) {
                        board[i][j] = players[0];
                        stack.push(3 * i + j);
                        bestScore = Math.min(bestScore, minimax(!isMaximizing, depth - 1));
                        stack.pop();
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        }
    }

    // method to check for winner
    public String checkWinner() {
        char winner = ' ';

        // horizontal
        for(int i = 0; i < 3; i++) {
            if (equalRow(board[i])) { 
                winner = board[i][0];
            }
        }
        
       
        char[][] trans = transpose();
        for(int i = 0; i < trans.length; i++) {
            if (equalRow(trans[i])) {
                winner = trans[i][0];
            }
        }

        if (equalDiag()) {
            winner = board[0][0];
        }
        if (equalAntiDiag()) {
            winner = board[0][board.length - 1];
        }

        if (winner == ' ' && stack.size() == 9) {
            return "tie";
        } else if (winner == ' ') {
            return null;
        } else {
            return Character.toString(winner);
        }
    }

    // return how many position is filled
    public int getCount() {
        return stack.size();
    }

    /*--------------------------------private method--------------------------------*/
    // private method to check each row column and diagonol
    private boolean equalRow(char[] row) {
        char first = row[0];
        for (int i = 1; i < row.length; i++) {
            if (row[i] != first) return false;
        }
        return true;
    }

    private char[][] transpose() {
        char[][] trans = new char[board.length][board.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                trans[i][j] = board[j][i];
            }
        }
        return trans;
    }

    private boolean equalDiag() {
        char first = board[0][0];
        for (int i = 0; i < board.length; i++) {
            if (board[i][i] != first) {
                return false;
            }
        }
        return true;
    }

    private boolean equalAntiDiag() {
        char first = board[0][board.length - 1];
        for (int i = 0; i < board.length; i++) {
            if (board[i][board.length - 1 - i] != first) {
                return false;
            }
        }
        return true;
    }
    /*--------------------------------private method--------------------------------*/
}
