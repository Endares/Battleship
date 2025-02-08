package org.battleship;

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
    public String displayMyOwnBoard() {
        // display empty board
        return makeHeader() + makeColumns() + makeHeader();
    }

    /**
     * This makes the columns, e.g. A  | | | | | | | | |  A\n
     *
     * @return the String that is the columns for the given board
     */
    String makeColumns () {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < toDisplay.getHeight(); i++) {
            String sep = " ";
            char letter = (char) ('A' + i);
            ans.append(letter).append(" ");
            for (int j = 0; j < toDisplay.getWidth(); j++) {
                ans.append(sep);
                sep = "| ";
            }
            ans.append(" ").append(letter).append('\n');
        }
        return ans.toString();
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
