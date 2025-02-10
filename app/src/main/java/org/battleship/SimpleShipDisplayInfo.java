package org.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
    private T myData;
    private T onHit;

    public SimpleShipDisplayInfo(T md, T oh) {
        this.myData = md;
        this.onHit = oh;
    }
    /**
     * check if (hit) and returns onHit if so, and myData otherwise.
     */
    @Override
    public T getInfo(Coordinate where, boolean hit) {
        if (hit) return onHit;
        else return myData;
    }
}
