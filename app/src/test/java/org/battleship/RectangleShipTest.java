package org.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShipTest {
    @Test
    void testGetName() {
        RectangleShip<Character> rs = new RectangleShip<>("testship", new Coordinate(1, 2), 2, 3, 's', '*');
        assertTrue(rs.getName().equals("testship"));
    }
    @Test
    void testOccupyCoordinates() {
        RectangleShip<Character> rs = new RectangleShip<>("testship", new Coordinate(1, 2), 2, 3, 's', '*');
        assertTrue(rs.occupiesCoordinates(new Coordinate(1, 3)));
        assertFalse(rs.occupiesCoordinates(new Coordinate(1, 6)));
    }

    @Test
    void testMakeCoords() {
        checkMakeCoords(
                new Coordinate(1, 1), 2, 2,
                new ArrayList<>(List.of(
                        new Coordinate(1, 1), new Coordinate(1, 2),
                        new Coordinate(2, 1), new Coordinate(2, 2)
                ))
        );

        checkMakeCoords(
                new Coordinate(0, 0), 1, 3,
                new ArrayList<>(List.of(new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0)))
        );

        checkMakeCoords(
                new Coordinate(2, 3), 3, 1,
                new ArrayList<>(List.of(new Coordinate(2, 3), new Coordinate(2, 4), new Coordinate(2, 5)))
        );

        checkMakeCoords(
                new Coordinate(5, 5), 1, 1,
                new ArrayList<>(List.of(new Coordinate(5, 5)))
        );
    }
    private void checkMakeCoords(Coordinate upperLeft, int width, int height, List<Coordinate> expected) {
        ArrayList<Coordinate> result = RectangleShip.makeCoords(upperLeft, width, height);
        assertEquals(expected, result);
    }
//
//    @Test
//    public void test_rotateBarShip() {
//        V2ShipFactory factory = new V2ShipFactory();
//        Board<Character> theBoard = new BattleShipBoard<>(5, 5, 'X');
//        theBoard.tryAddShip(factory.makeSubmarine(new Placement("A1V")));
//        theBoard.tryAddShip(factory.makeSubmarine(new Placement("C3h")));
//        theBoard.tryAddShip(factory.makeDestroyer(new Placement("e1h")));
//
//        assertEquals(" |s| | | |\n" +
//                  " |s| | | |\n" +
//                  " | | |s|s|\n" +
//                  " | | | | |\n" +
//                  " |d|d|d| |\n",
//                printBoard(theBoard));
//
//        Ship<Character> s = theBoard.getShipAt(new Coordinate("c4"));
//        s.rotateBarShip(new Placement("b3v"));
//
//        assertEquals(" |s| | | |\n" +
//              " |s| |s| |\n" +
//              " | | |s| |\n" +
//              " | | | | |\n" +
//              " |d|d|d| |\n",
//                printBoard(theBoard));
//    }

    private String printBoard(Board<Character> board) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < board.getHeight(); ++i) {
            for (int j = 0; j < board.getWidth(); ++j) {
                Character c = board.whatIsAtForSelf(new Coordinate(i, j));
                res.append(c == null ? " " : c).append("|");
            }
            res.append("\n");
        }
        return res.toString();
    }
}