package org.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public class ComputerTextPlayer extends TextPlayer {
    public ComputerTextPlayer(String name, Board<Character> theBoard, BufferedReader inputReader, PrintStream out, AbstractShipFactory<Character> shipFactory) {
        super(name, theBoard, inputReader, out, shipFactory);
    }

    final String[] SpecifiedPlacements = {"a0v", "a1v", "a2v", "a6v", "a8v",
            "k5d", "f2r", "q0l", "n2l", "f6d"};

    @Override
    public void makeActionChoice(TextPlayer enemy) throws IOException {
        int action; // 0 - F, 1 - M, 2 - S
        if (remainMovements == 0 && remainSonar == 0) {
            action = 0; // 0
        } else if (remainSonar == 0) {
            action = (int) (Math.random() * 2); // 0,1
        } else if (remainMovements == 0) {
            action = (int) (Math.random() * 2) * 2; // 0,2
        } else action = (int) (Math.random() * 3); // 0,1,2
        label:
        switch (action) {
            case 0:
                doFirePhase(enemy);
                break label;
            case 1:
                doMovementPhase();
                break label;
            case 2:
                sonarScanPhase(enemy);
                break label;
            default:
        }
    }

    @Override
    public void doPlacementPhase() throws IOException {
        doSpecifiedPlacement();
    }

    public void doSpecifiedPlacement() throws IOException {
        int i = 0;
        for (String s : shipsToPlace) {
            Placement p = new Placement(SpecifiedPlacements[i]);
            Ship<Character> ship = shipCreationFns.get(s).apply(p);
            ++i;
            boolean isSuccess = theBoard.tryAddShip(ship);
            // Check placement result
            if (!isSuccess) {
                out.println("Invalid placemen of " + ship.getName() + " at " + p);
                break;
            }
        }
    }

    @Override
    public void firePhase(TextPlayer enemy, String myHeader, String enemyHeader) throws IOException {
        randomFire(enemy);
    }

    public void randomFire(TextPlayer enemy) throws IOException {
        // fire at enemy's board
        enemy.theBoard.fireAt(getRandomCoordinate());
    }

    /**
     * Get a random, valid coordinate in the board
     */
    private Coordinate getRandomCoordinate() {
        int randomRow = (int) (Math.random() * theBoard.getHeight());
        int randomColumn = (int) (Math.random() * theBoard.getWidth());
        return new Coordinate(randomRow, randomColumn);
    }

    /**
     * Scan a random coordinate
     */
    @Override
    public void sonarScanPhase(TextPlayer enemy) throws IOException {
        // do not need to scan as computer player doesn't need to display scan results
        // enemy.sonarScan(getRandomCoordinate());
        out.println("Player " + name + " used a special action");
        --remainSonar;
    }


    @Override
    public void doMovementPhase() throws IOException {
        // 1. select a ship randomly from shipList
        Ship<Character> ship = theBoard.getRandomShip();
        // 2. move the ship to a new Placement (maybe the same as old)
        makeRandomMovement(ship);
        --remainMovements;
        out.println("Player " + name + " used a special action");
    }

    /**
     * Move the ship to the first available placement by iteration
     */
    protected void makeRandomMovement(Ship<Character> ship) {
        for (int i = 0; i < theBoard.getHeight(); ++i) {
            for (int j = 0; j < theBoard.getWidth(); ++j) {
                Coordinate c = new Coordinate(i, j);
                // if the current coordinate is occupied, shortcut.
                // however, to make sure this function can always work,
                // we need to leave a chance for moving to the ship's current placement
                if (theBoard.displayShipAt(c) == null || theBoard.displayShipAt(c).equals(ship.getName())) {
                    if (ship.getClass() == RectangleShip.class) { // rectangle ships
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'V'))) { // success
                            return;
                        }
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'H'))) { // success
                            return;
                        }
                    } else { // non-rectangle ships
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'U'))) { // success
                            return;
                        }
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'R'))) { // success
                            return;
                        }
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'D'))) { // success
                            return;
                        }
                        if (theBoard.tryMoveShip(ship, new Placement(c, 'L'))) { // success
                            return;
                        }
                    }
                }
            }
        }
        //throw new IOException("No where to move any ship");
    }


}
