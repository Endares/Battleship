package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V1ShipFactoryTest {
    V1ShipFactory factory = new V1ShipFactory();
    /**
     * Helper method to check ship properties.
     */
    private void checkShip(Ship<Character> testShip, String expectedName,
                           char expectedLetter, Coordinate... expectedLocs) {
        assertEquals(expectedName, testShip.getName());

        // Check that all expected locations are occupied
        for (Coordinate c : expectedLocs) {
            assertTrue(testShip.occupiesCoordinates(c), expectedName + " should occupy " + c);
        }
    }

    @Test
    void testMakeSubmarine() {
        checkShip(factory.makeSubmarine(new Placement("A0V")), "Submarine", 's',
                new Coordinate(0, 0), new Coordinate(1, 0));
        checkShip(factory.makeSubmarine(new Placement("B1H")), "Submarine", 's',
                new Coordinate(1, 1), new Coordinate(1, 2));
    }

    @Test
    void testMakeDestroyer() {
        checkShip(factory.makeDestroyer(new Placement("C2V")), "Destroyer", 'd',
                new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2));
        checkShip(factory.makeDestroyer(new Placement("D3H")), "Destroyer", 'd',
                new Coordinate(3, 3), new Coordinate(3, 4), new Coordinate(3, 5));
    }

    @Test
    void testMakeBattleship() {
        checkShip(factory.makeBattleship(new Placement("E4V")), "Battleship", 'b',
                new Coordinate(4, 4), new Coordinate(5, 4), new Coordinate(6, 4), new Coordinate(7, 4));
        checkShip(factory.makeBattleship(new Placement("F5H")), "Battleship", 'b',
                new Coordinate(5, 5), new Coordinate(5, 6), new Coordinate(5, 7), new Coordinate(5, 8));
    }

    @Test
    void testMakeCarrier() {
        checkShip(factory.makeCarrier(new Placement("G1V")), "Carrier", 'c',
                new Coordinate(6, 1), new Coordinate(7, 1), new Coordinate(8, 1),
                new Coordinate(9, 1), new Coordinate(10, 1), new Coordinate(11, 1));
        checkShip(factory.makeCarrier(new Placement("H2H")), "Carrier", 'c',
                new Coordinate(7, 2), new Coordinate(7, 3), new Coordinate(7, 4),
                new Coordinate(7, 5), new Coordinate(7, 6), new Coordinate(7, 7));
    }
}