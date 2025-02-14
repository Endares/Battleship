package org.battleship;

import java.util.function.Function;

/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the
 * enemy's board.
 */
public class BoardTextView {
    /**
     * The Board to display
     */
    private final Board<Character> toDisplay;

    /**
     * Constructs a BoardView, given the board it will display.
     *
     * @param toDisplay is the Board to display
     * @throws IllegalArgumentException if the board is larger than 10x26.
     */
    public BoardTextView(Board<Character> toDisplay) {
        this.toDisplay = toDisplay;
        if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
            throw new IllegalArgumentException(
                    "Board must be no larger than 10x26, but is " + toDisplay.getWidth() + "x" + toDisplay.getHeight());
        }
    }

    /**
     * Display 2 boards for each player: owned board and enemy's board:
     * owned board: ' ' for empty, '*' for hit ship,
     *              letter for not-hit ship e.g.'s' for submarine
     * enemy's board: letter for hit, 'X' for miss
     */
    protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
        StringBuilder result = new StringBuilder();
        result.append(makeHeader());
        for (int i = 0; i < toDisplay.getHeight(); i++) {
            String sep = "";
            char letter = (char) ('A' + i);
            result.append(letter).append(' ');
            for (int j = 0; j < toDisplay.getWidth(); j++) {
                result.append(sep);
                // The apply() method is part of Java’s built-in functional interface Function<T, R>.
                // It is used to process an input of type T and return a result of type R.
                Character temp = getSquareFn.apply(new Coordinate(i, j));
                result.append(temp == null ? ' ' : temp);
                sep = "|";
            }
            result.append(' ').append(letter).append('\n');
        }
        result.append(makeHeader());
        return result.toString();
    }

    /**
     * Player A's turn:
     *      Your ocean                           Player B's ocean
     *   0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9
     * A s| | | | | | | |c|  A                A  | | | | | | | | |  A
     * B s| |d| | | | | |c|  B                B  | | | | | | | | |  B
     * C  | |*| | | | | |c|  C                C  | |X| | | | | | |  C
     * D  | |d| | | | | |c|  D                D  |X|d|d| | | | | |  D
     * E  | | | | | | | |c|  E                E  | |X| | | | | | |  E
     * F  | |d| | | | | |c|  F                F  | | | | | | | | |  F
     * G  | |d| | | |b| | |  G                G  | | | | | | | | |  G
     * H  | |d| | | |b| | |  H                H  | | | | | | | | |  H
     * I  | | | | | |b| | |  I                I  | | | | | | | | |  I
     * J  | | | | | |b| | |  J                J  | | |X| | | | | |  J
     * K c|c|c|c|c|c| | | |  K                K  | | | | | | | | |  K
     * L  | | | | | | | | |  L                L  | | | |X| | | | |  L
     * M  | | | |s|s| | | |  M                M  | | | | | | | | |  M
     * N  | | | | | | | | |  N                N  | | | | | | | | |  N
     * O  | | | | | |b| | |  O                O  | | | | |s|s| | |  O
     * P  | | | | | |b| | |  P                P  | | | | | | | | |  P
     * Q  | | | | | |b| | |  Q                Q  | | | | | | | | |  Q
     * R  | | | | | |b| | |  R                R  | | | | | | | | |  R
     * S  | | | | | | | | |  S                S  | | | | | | | | |  S
     * T d|d|d| | | | | | |  T                T  | | | | | | | | |  T
     *   0|1|2|3|4|5|6|7|8|9                    0|1|2|3|4|5|6|7|8|9
     */
    public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
        // Get the string representations of both boards
        String[] myLines = displayMyOwnBoard().split("\\n");
        String[] enemyLines = enemyView.displayEnemyBoard().split("\\n");

        // Calculate spacing
        int boardWidth = toDisplay.getWidth();
        int myHeaderStart = 5;
        int enemyStartCol = 2 * boardWidth + 19;
        int enemyHeaderStart = 2 * boardWidth + 22;

        // Create a StringBuilder for the result
        StringBuilder result = new StringBuilder();

        // Add headers
        result.append(String.format("%" + myHeaderStart + "s%s%"
                        + (enemyHeaderStart - myHeaderStart - myHeader.length()) + "s%s\n",
                "", myHeader, "", enemyHeader));
        // %5s: Prints a string with a minimum width of 5 (right-aligned).
        // %s: Prints the player’s header (myHeader), e.g., "Your Ocean".
        // % + (enemyHeaderStart - myHeader.length()) + "s":
        // Adds padding spaces between the two headers.
        // %s: Prints the enemy’s header (enemyHeader), e.g., "Enemy's Ocean".

        // Combine each line from both boards
        for (int i = 0; i < myLines.length && i < enemyLines.length; i++) {
            result.append(String.format("%-" + enemyStartCol + "s%s\n", myLines[i], enemyLines[i]));
        }
        // %-<width>s: Left-aligns myLines[i] with a fixed width (enemyStartCol).
        // %s: Appends enemyLines[i] immediately after.

        return result.toString();
    }

    public String displayMyOwnBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForSelf(c));
        // Passes a lambda function (c) -> toDisplay.whatIsAtForSelf(c) to displayAnyBoard().
    }

    public String displayEnemyBoard() {
        return displayAnyBoard((c)->toDisplay.whatIsAtForEnemy(c));
    }

    /**
     * This makes the header line, e.g. 0|1|2|3|4\n
     *
     * @return the String that is the header line for the given board
     */
    String makeHeader() {
        StringBuilder ans = new StringBuilder("  ");
        String sep = ""; // start with nothing to separate, then switch to | to separate
        for (int i = 0; i < toDisplay.getWidth(); i++) {
            ans.append(sep);
            ans.append(i);
            sep = "|";
        }
        ans.append("\n");
        return ans.toString();
    }
}
