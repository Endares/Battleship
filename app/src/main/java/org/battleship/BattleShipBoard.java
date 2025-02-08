package org.battleship;

import java.util.ArrayList;

public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private ArrayList<Ship<T>> myShips;

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
        if (w <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + w);
        }
        if (h <= 0) {
            throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
    }

    /**
     * checks the validity of the placement and returns
     * true if the placement was ok, and false if it was invalid (and thus not actually placed)
     */
    public boolean tryAddShip(Ship<T> toAdd) {
        myShips.add(toAdd);
        return true;
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
