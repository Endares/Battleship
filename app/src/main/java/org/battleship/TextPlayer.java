package org.battleship;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

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

    // an ArrayList of ships' names
    final ArrayList<String> shipsToPlace;
    // a map from ship name to the lambda to create it
    final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

    public String getName() {
        return name;
    }

    public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out, AbstractShipFactory<Character> shipFactory) {
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = inputReader;
        this.out = out;
        this.name = name;
        this.shipFactory = new V1ShipFactory();
        this.shipsToPlace = new ArrayList<>();
        this.shipCreationFns = new HashMap<>();

        // Initialize ship creation mappings and ship order
        setupShipCreationMap();
        setupShipCreationList();
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
        if (s == null) {
            throw new EOFException("End of input reached. Exiting the game.");
        }
        return new Placement(s);
    }

    /**
     * put all possible(all valid kinds of ships)
     * String -> lambda mappings into shipCreationFns
     */
    protected void setupShipCreationMap() {
        shipCreationFns.put("Submarine", shipFactory::makeSubmarine);
        shipCreationFns.put("Destroyer", shipFactory::makeDestroyer);
        shipCreationFns.put("Battleship", shipFactory::makeBattleship);
        shipCreationFns.put("Carrier", shipFactory::makeCarrier);
    }

    /**
     * we'll put in the ships we want to add, in the order we want to add them.
     * If there are multiple copies, we'll include the item for specific number of times
     */
    protected void setupShipCreationList() {
        // Collections.nCopies(int n, T o) is a static method in Java's Collections class.
        // It creates an immutable list containing n copies of the specified object o.
        shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
        shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
        shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
        shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
    }

    /**
     *   - read a Placement (prompt: "Where would you like to put your ship?")
     *   - Create a basic ship based on the location in that Placement
     *     (orientation doesn't matter yet)
     *   - Add that ship to the board
     *   - Print out the board (to out, not to System.out)
     *   - Function<Placement, Ship<Character>> is an object an apply method that takes a Placement and returns a Ship<Character>
     * @throws IOException
     */
    public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
//        Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
//        Ship<Character> s = createFn.apply(p);
//        theBoard.tryAddShip(s);
//        out.print(view.displayMyOwnBoard());
        while (true) {
            try {
                // Prompt the user for placement input
                Placement p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
                // Create the ship based on user input
                Ship<Character> s = createFn.apply(p);
                // Attempt to place the ship on the board
                boolean isSuccess = theBoard.tryAddShip(s);
                // Check placement result
                if (!isSuccess) {
                    out.println("Invalid placement: overlaps another ship or out of bounds. Please try again.");
                } else {
                    // Successful placement: display the board and break out of the loop
                    out.print(view.displayMyOwnBoard());
                    break; // Exit loop on success
                }
            } catch (IllegalArgumentException e) {
                // Handle invalid input format from Placement constructor
                out.println("Invalid input format: " + e.getMessage() + ". Please try again.");
            }
        }
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

        for (String s : shipsToPlace) {
            doOnePlacement(s, shipCreationFns.get(s));
        }
    }

    public void firePhase(TextPlayer enemy, String myHeader, String enemyHeader) throws IOException {
        out.println("Player" + name + "'s turn:");
        out.print(view.displayMyBoardWithEnemyNextToIt(enemy.view, myHeader, enemyHeader));
        readAndFire(enemy, "Player" + name + ", where do you want to fire at?");
        out.println(view.displayMyBoardWithEnemyNextToIt(enemy.view, myHeader, enemyHeader));
    }

    /**
     * Read a line (coordinate) to fire at and fire.
     * @param prompt
     * @throws IOException
     */
    public void readAndFire(TextPlayer enemy, String prompt) throws IOException {
        while (true) {
            try {
                out.println(prompt);
                // Note that we are not doing error checking YET (later, in task 17).  Also note
                // that we don't print to System.out, we print to out.  Why?  We might want to print
                // somewhere else, especially for testing.   When we want System.out, we'll pass it into
                // the constructor.
                String s = inputReader.readLine();
                if (s == null) {
                    throw new EOFException("End of input reached. Exiting the game.");
                }
                Ship<Character> ship = enemy.theBoard.fireAt(new Coordinate(s));
                if (ship == null) {
                    out.println("You missed!");
                } else {
                    out.println("You hit a " + ship.getName() + "!");
                }
                break;
            } catch (IllegalArgumentException e) {
                // Handle invalid coordinate format
                out.println("Invalid coordinate. Please try again.");
            }
        }
    }

    /**
     * If all ships are sunk, then this player lost
     * @return
     */
    public boolean isLost() {
        return theBoard.allSunk();
    }
}
