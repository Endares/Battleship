package org.battleship;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BasicShipTest {
    @Test
    void testRecordHitAtAndWasHitAt() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 1), 2, 2, 's', '*');

        // Initially, no coordinates are hit
        assertFalse(ship.wasHitAt(new Coordinate(1, 1)));
        assertFalse(ship.wasHitAt(new Coordinate(2, 2)));

        // Record a hit
        ship.recordHitAt(new Coordinate(1, 1));
        assertTrue(ship.wasHitAt(new Coordinate(1, 1)));

        // Ensure another coordinate is still unhit
        assertFalse(ship.wasHitAt(new Coordinate(1, 2)));

        // Ensure hitting an invalid coordinate throws an exception
        assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(new Coordinate(3, 3)));
        assertThrows(IllegalArgumentException.class, () -> ship.wasHitAt(new Coordinate(0, 0)));
    }

    @Test
    void testIsSunk() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(2, 2), 2, 2, 's', '*');

        // Initially, ship is not sunk
        assertFalse(ship.isSunk());

        // Hit all coordinates except one
        ship.recordHitAt(new Coordinate(2, 2));
        ship.recordHitAt(new Coordinate(2, 3));
        ship.recordHitAt(new Coordinate(3, 2));
        assertFalse(ship.isSunk()); // Not fully sunk yet

        // Hit the last coordinate
        ship.recordHitAt(new Coordinate(3, 3));
        assertTrue(ship.isSunk()); // Now the ship should be sunk
    }

    @Test
    void testGetDisplayInfoAt() {
        RectangleShip<Character> ship = new RectangleShip<>("submarine", new Coordinate(1, 1), 2, 2, 's', '*');

        // Initially, all coordinates should display 's'
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(1, 1), true));
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(2, 2), true));

        // Hit a coordinate and check if display updates
        ship.recordHitAt(new Coordinate(1, 1));
        assertEquals('*', ship.getDisplayInfoAt(new Coordinate(1, 1), true)); // Should display '*'

        // Unhit coordinates should still show 's'
        assertEquals('s', ship.getDisplayInfoAt(new Coordinate(1, 2), true));

        // Ensure checking an invalid coordinate throws an exception
        assertThrows(IllegalArgumentException.class, () -> ship.getDisplayInfoAt(new Coordinate(3, 3), true));
    }

    @Test
    void testGetCoordinate() {
        RectangleShip<Character> ship = new RectangleShip<>("testship", new Coordinate(1, 1), 2, 2, 's', '*');
        Iterable<Coordinate> coords = ship.getCoordinates();
        assertEquals(coords, Set.of(new Coordinate(1, 1), new Coordinate(1, 2), new Coordinate(2, 1), new Coordinate(2, 2)));

    }
}