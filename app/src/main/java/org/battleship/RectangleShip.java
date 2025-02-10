package org.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
    public RectangleShip(Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> displayInfo) {
        super(makeCoords(upperLeft, width, height), displayInfo);
    }

    public RectangleShip(Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit));
    }
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this(upperLeft, 1, 1, data, onHit);
    }

    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> coords = new HashSet<>();
        for (int i = upperLeft.getRow(); i < upperLeft.getRow() + width; i++) {
            for (int j = upperLeft.getColumn(); j < upperLeft.getColumn() + height; j++) {
                coords.add(new Coordinate(i, j));
            }
        }
        return coords;
    }
}
