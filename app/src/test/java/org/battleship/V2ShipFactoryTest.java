package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class V2ShipFactoryTest {
    V2ShipFactory factory = new V2ShipFactory();
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
        // Test for all orientations
        checkShip(factory.makeBattleship(new Placement("A0U")), "Battleship", 'b',
                new Coordinate(0, 1),
                new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1, 2));

        checkShip(factory.makeBattleship(new Placement("B2R")), "Battleship", 'b',
                new Coordinate(1, 2),
                new Coordinate(2, 2), new Coordinate(2, 3), new Coordinate(3, 2));

        checkShip(factory.makeBattleship(new Placement("C1D")), "Battleship", 'b',
                new Coordinate(2, 2),
                new Coordinate(3, 2), new Coordinate(2, 1), new Coordinate(2, 3));

        checkShip(factory.makeBattleship(new Placement("D3L")), "Battleship", 'b',
                new Coordinate(4, 4),
                new Coordinate(5, 4), new Coordinate(3, 4), new Coordinate(4, 3));
    }

    @Test
    void testMakeCarrier() {
        // Test for all orientations
        checkShip(factory.makeCarrier(new Placement("A0U")), "Carrier", 'c',
                new Coordinate(0, 0),
                new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3, 0),
                new Coordinate(2, 1), new Coordinate(3, 1), new Coordinate(4, 1));

        checkShip(factory.makeCarrier(new Placement("B2R")), "Carrier", 'c',
                new Coordinate(1, 6),
                new Coordinate(1, 5), new Coordinate(1, 4), new Coordinate(1, 3),
                new Coordinate(2, 4), new Coordinate(2, 3), new Coordinate(2, 2));

        checkShip(factory.makeCarrier(new Placement("C1D")), "Carrier", 'c',
                new Coordinate(6, 2),
                new Coordinate(5, 2), new Coordinate(4, 2), new Coordinate(3, 2),
                new Coordinate(4, 1), new Coordinate(3, 1), new Coordinate(2, 1));

        checkShip(factory.makeCarrier(new Placement("D3L")), "Carrier", 'c',
                new Coordinate(4, 3),
                new Coordinate(4, 4), new Coordinate(4, 5), new Coordinate(4, 6),
                new Coordinate(3, 5), new Coordinate(3, 6), new Coordinate(3, 7));
    }
}