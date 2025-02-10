package org.battleship;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RectangleShipTest {
    @Test
    void testOccupyCoordinates() {
        RectangleShip<Character> rs = new RectangleShip<>(new Coordinate(1, 2), 2, 3, 's', '*');
        assertTrue(rs.occupiesCoordinates(new Coordinate(1, 4)));
        assertFalse(rs.occupiesCoordinates(new Coordinate(1, 6)));
    }

    @Test
    void testMakeCoords() {
        checkMakeCoords(
                new Coordinate(1, 1), 2, 2,
                Set.of(new Coordinate(1, 1), new Coordinate(1, 2),
                        new Coordinate(2, 1), new Coordinate(2, 2))
        );

        checkMakeCoords(
                new Coordinate(0, 0), 1, 3,
                Set.of(new Coordinate(0, 0), new Coordinate(0, 1), new Coordinate(0, 2))
        );

        checkMakeCoords(
                new Coordinate(2, 3), 3, 1,
                Set.of(new Coordinate(2, 3), new Coordinate(3, 3), new Coordinate(4, 3))
        );

        checkMakeCoords(
                new Coordinate(5, 5), 1, 1,
                Set.of(new Coordinate(5, 5))
        );
    }
    private void checkMakeCoords(Coordinate upperLeft, int width, int height, Set<Coordinate> expected) {
        HashSet<Coordinate> result = RectangleShip.makeCoords(upperLeft, width, height);
        assertEquals(expected, result);
    }

}