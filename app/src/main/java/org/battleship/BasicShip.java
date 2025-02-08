package org.battleship;

/**
 * Basic Ship is a kind of Ship that is implemented by Characters
 */
public class BasicShip implements Ship<Character> {
    private final Coordinate myLocation;
    public BasicShip(Coordinate location) {
        myLocation = location;
    }
    @Override
    public boolean occupiesCoordinates(Coordinate where) {
        return where.equals(myLocation);
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public void recordHitAt(Coordinate where) {

    }

    @Override
    public boolean wasHitAt(Coordinate where) {
        return false;
    }

    @Override
    public Character getDisplayInfoAt(Coordinate where) {
        return 's';
    }
}
