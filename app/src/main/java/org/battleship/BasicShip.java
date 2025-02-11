package org.battleship;

import java.util.HashMap;

/**
 * Basic Ship is a kind of Ship that is implemented by Characters
 */
public abstract class BasicShip<T> implements Ship<T> {
    //private final Coordinate myLocation;
    protected HashMap<Coordinate, Boolean> myPieces;
    protected ShipDisplayInfo<T> myDisplayInfo;
    /**
     * Track if the piece at a coordinate has been hit
     * if we have a coordinate c, and we look it up in the map:
     *    if myPieces.get(c)  is null, c is not part of this Ship
     *    if myPieces.get(c)  is false, c is part of this ship and has not been hit
     *    if myPieces.get(c)  is true, c is part of this ship and has been hit
     */

    /**
     * initialize myPieces to have each Coordinate in where mapped to false.
     * @param where
     */
    public BasicShip(Iterable<Coordinate> where, ShipDisplayInfo<T> myDisplayInfo) {
        myPieces = new HashMap<Coordinate, Boolean>();
        for (Coordinate c : where) {
            myPieces.put(c, false);
        }
        this.myDisplayInfo = myDisplayInfo;
    }

    /**
     * Check if this ship occupies the given coordinate.
     *
     * @param where is the Coordinate to check if this Ship occupies
     * @return true if where is inside this ship, false if not.
     */
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return myPieces.containsKey(where);
    }

    @Override
    public boolean isSunk() {
        for (Coordinate c : myPieces.keySet()) {
            if (myPieces.get(c) == false) {
                return false;
            }
        }
        return true; // all pieces are hit
    }

    @Override
    public void recordHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        myPieces.put(where, true);
    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        return myPieces.get(where);
    }

    @Override
    public T getDisplayInfoAt(Coordinate where) {
        checkCoordinateInThisShip(where);
        //TODO this is not right.  We need to
        //look up the hit status of this coordinate
        return myDisplayInfo.getInfo(where, myPieces.get(where));
    }

    /**
     * check if c is part of this ship (in myPieces),
     * and if not, throw an IllegalArgumentException.
     * @param c
     */
    protected void checkCoordinateInThisShip(Coordinate c) {
        if (!myPieces.containsKey(c)) {
            throw new IllegalArgumentException("Coordinate " + c + " is not present in this ship");
        }
    }
}
