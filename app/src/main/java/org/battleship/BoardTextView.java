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
