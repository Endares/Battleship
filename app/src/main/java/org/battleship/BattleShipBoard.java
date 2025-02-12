package org.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     * @throws IllegalArgumentException if the width or height are less than or equal to zero.
     */
    public BattleShipBoard(int w, int h) {
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive, got: " + w + "x" + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null)); // Initialize checker properly
    }

    /**
     * checks the validity of the placement and returns
     * true if the placement was ok, and false if it was invalid (and thus not actually placed)
     */
    public boolean tryAddShip(Ship<T> toAdd) {
        if (placementChecker.checkPlacement(toAdd, this)) {
            myShips.add(toAdd);
            return true;
        }
        return false;
    }

    /**
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that coordinate.  If one is found, we return whatever
     * displayInfo it has at those coordinates.  If
     * none is found, we return null.
     */
    public T whatIsAt(Coordinate where) {
        for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(where)){
                return s.getDisplayInfoAt(where);
            }
        }
        return null;
    }
}
