package org.battleship;

import java.util.HashSet;

public class RectangleShip<T> extends BasicShip<T> {
    private final String name;
    public String getName() {
        return name;
    }
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> displayInfo) {
        super(makeCoords(upperLeft, width, height), displayInfo);
        this.name = name;
    }

    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit));
    }
    // only for testing
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    static HashSet<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        HashSet<Coordinate> coords = new HashSet<>();
        for (int i = upperLeft.getRow(); i < upperLeft.getRow() + height; i++) {
            for (int j = upperLeft.getColumn(); j < upperLeft.getColumn() + width; j++) {
                coords.add(new Coordinate(i, j));
            }
        }
        return coords;
    }
}
