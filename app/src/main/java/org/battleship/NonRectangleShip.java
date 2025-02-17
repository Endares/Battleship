package org.battleship;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;

public class NonRectangleShip<T> extends BasicShip<T>{
    private final String name;
    //private ArrayList<Coordinate> pieces;
    public String getName() {
        return name;
    }
    public NonRectangleShip(String type, String name, Coordinate upperLeft, Character orientation, ShipDisplayInfo<T> displayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
        super(getCoordsForType(type, upperLeft, orientation), displayInfo, enemyDisplayInfo);
        this.name = name;
    }

    public NonRectangleShip(String type, String name, Coordinate upperLeft, Character orientation, T data, T onHit) {
        super(getCoordsForType(type, upperLeft, orientation), new SimpleShipDisplayInfo<>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
        this.name = name;
        // we will tell the parent constructor that for my own view display:
        // 1.data if not hit; 2.onHit if hit
        // for the enemy view:
        // 1.nothing if not hit; 2.data if hit
    }

    private static ArrayList<Coordinate> getCoordsForType(String type, Coordinate upperLeft, Character orientation) {
        type = type.toLowerCase();
        if (type.equals("battleship")) {
            return makeBattleshipCoords(upperLeft, orientation);
        } else if (type.equals("carrier")) {
            return makeCarrierCoords(upperLeft, orientation);
        } else {
            throw new IllegalArgumentException("Invalid ship type: " + type);
        }
    }

    static ArrayList<Coordinate> makeBattleshipCoords(Coordinate upperLeft, Character orientation) {
        //HashSet<Coordinate> coords = new HashSet<>();
        //Coordinate c1, c2, c3, c4;
        ArrayList<Coordinate> coords = new ArrayList<>();
        /**
         * *b            B         Bbb        *b
         * bbb    OR     bb   OR    b     OR  bb
         *               b                     b
         * Up          Right      Down      Left
         */
        if (orientation == 'U') {
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
        } else if (orientation == 'R') {
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
        } else if (orientation == 'D') {
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
        } else { // if (orientation == 'L')
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
        }
        return coords;
    }

    static ArrayList<Coordinate> makeCarrierCoords (Coordinate upperLeft, Character orientation) {
        ArrayList<Coordinate> coords = new ArrayList<>();
        /**
         *    C                       C
         *    c          *cccc        cc       * ccc
         *    cc   OR    ccc      OR  cc   OR  cccc
         *    cc                       c
         *     c                       c
         *
         *    Up         Right       Down       Left
         */
        if (orientation == 'U') {
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn() + 1));
        } else if (orientation == 'R') {
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 4));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 3));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
        } else if (orientation == 'D') {
            coords.add(new Coordinate(upperLeft.getRow() + 4, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 3, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 2, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn()));
        } else { // if (orientation == 'L')
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn()));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 1));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 2));
            coords.add(new Coordinate(upperLeft.getRow() + 1, upperLeft.getColumn() + 3));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 2));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 3));
            coords.add(new Coordinate(upperLeft.getRow(), upperLeft.getColumn() + 4));
        }
        return coords;
    }
}
