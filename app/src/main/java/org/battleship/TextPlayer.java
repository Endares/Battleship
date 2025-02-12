package org.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class TextPlayer {
    /**
     * By declaring these fields as final,
     * ensure that the core components of TextPlayer class
     * remain consistent and unchangeable after initialization
     */
    final Board<Character> theBoard;
    final BoardTextView view;
    final BufferedReader inputReader;
    final PrintStream out;
    final AbstractShipFactory<Character> shipFactory;
    private String name;

    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out, AbstractShipFactory<Character> shipFactory) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputReader;
        this.out = out;
        this.name = name;
        this.shipFactory = new V1ShipFactory();
    }
    // Reader reads one character at a time, leading to frequent I/O operations (slow).
    // BufferedReader stores multiple characters in a buffer,
    // reducing I/O interactions and improving efficiency.

    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        // Note that we are not doing error checking YET (later, in task 17).  Also note
        // that we don't print to System.out, we print to out.  Why?  We might want to print
        // somewhere else, especially for testing.   When we want System.out, we'll pass it into
        // the constructor.
        String s = inputReader.readLine();
        return new Placement(s);
    }

    /**
     *   - read a Placement (prompt: "Where would you like to put your ship?")
     *   - Create a basic ship based on the location in that Placement
     *     (orientation doesn't matter yet)
     *   - Add that ship to the board
     *   - Print out the board (to out, not to System.out)
     * @throws IOException
     */
    public void doOnePlacement() throws IOException {
        Placement p = readPlacement("Player " + this.name + " where do you want to place a Destroyer?");
        Ship<Character> s  = shipFactory.makeDestroyer(p);
        // RectangleShip<Character> ship = new RectangleShip<>(currentPlacement.getWhere(), 's', '*'); // ✅ Corrected
        theBoard.tryAddShip(s);
        out.println(view.displayMyOwnBoard());
    }

    /**
     * (a) display the starting (empty) board
     * (b) print the instructions message (from the README,
     *     but also shown again near the top of this file)
     * (c) call doOnePlacement to place one ship
     * @throws IOException
     */
    public void doPlacementPhase() throws IOException {
        out.print(view.displayMyOwnBoard());
        out.print("Player " + this.name + ": you are going to place the following ships (which are all\n" +
                "rectangular). For each ship, type the coordinate of the upper left\n" +
                "side of the ship, followed by either H (for horizontal) or V (for\n" +
                "vertical).  For example M4H would place a ship horizontally starting\n" +
                "at M4 and going to the right.  You have\n" +
                "\n" +
                "2 \"Submarines\" ships that are 1x2 \n" +
                "3 \"Destroyers\" that are 1x3\n" +
                "3 \"Battleships\" that are 1x4\n" +
                "2 \"Carriers\" that are 1x6\n");

        doOnePlacement();
    }

}
