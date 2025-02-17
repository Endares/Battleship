package org.battleship;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class RectangleShip<T> extends BasicShip<T> {
    private final String name;
    public String getName() {
        return name;
    }
    public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> displayInfo, ShipDisplayInfo<T> enemydisplayInfo) {
        super(makeCoords(upperLeft, width, height), displayInfo, enemydisplayInfo);
        this.name = name;
    }

    public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
        this(name, upperLeft, width, height, new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
        // we will tell the parent constructor that for my own view display:
        // 1.data if not hit; 2.onHit if hit
        // for the enemy view:
        // 1.nothing if not hit; 2.data if hit

    }
    // only for testing
    public RectangleShip(Coordinate upperLeft, T data, T onHit) {
        this("testship", upperLeft, 1, 1, data, onHit);
    }

    static ArrayList<Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
        ArrayList<Coordinate> coords = new ArrayList<>();
        for (int i = upperLeft.getRow(); i < upperLeft.getRow() + height; i++) {
            for (int j = upperLeft.getColumn(); j < upperLeft.getColumn() + width; j++) {
                coords.add(new Coordinate(i, j));
            }
        }
        return coords;
    }

//
//    /**
//     * BarShip: ship with width or height 1, special form of rectangle ship
//     * @param ship
//     * @param newPlacement
//     */
//    @Override
//    public void rotateBarShip(Placement newPlacement) {
//        LinkedHashMap<Coordinate, Boolean> newPieces = new LinkedHashMap<>();
//        int index = 0;
//        // left upper coordinate of new ship position
//        int row = newPlacement.getWhere().getRow();
//        int column = newPlacement.getWhere().getColumn();
//
//        if (newPlacement.getOrientation() == 'V') {
//            for (var entry : myPieces.entrySet()) {
//                newPieces.put(new Coordinate(row + index, column), entry.getValue());
//                ++index;
//            }
//        } else if (newPlacement.getOrientation() == 'H') {
//            for (var entry : myPieces.entrySet()) {
//                newPieces.put(new Coordinate(row, column + index), entry.getValue());
//                ++index;
//            }
//        }
//        myPieces = newPieces;
//    }
}
