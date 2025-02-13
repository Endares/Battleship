package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementRuleCheckerTest {
    private final BattleShipBoard<Character> board = new BattleShipBoard<>(10, 10);
    private final V1ShipFactory factory = new V1ShipFactory();
    private final PlacementRuleChecker<Character> checker = new InBoundsRuleChecker<>(new NoCollisionRuleChecker<>(null));

    @Test
    void testShipLegalInBounds() {
        // Place a 3x1 Battleship at (5,5) vertically
        Ship<Character> ship = factory.makeBattleship(new Placement("E5V"));
        assertNull(checker.checkPlacement(ship, board), "Battleship should be placed legally.");
    }

    @Test
    void testShipOutOfBounds_Top() {
        // Place a 3x1 Destroyer at (-1,5) vertically (out of top boundary)
        Ship<Character> ship = factory.makeDestroyer(new Placement(new Coordinate(-1, 5), 'V'));
        assertEquals("That placement is invalid: the ship goes off the top of the board.",
                checker.checkPlacement(ship, board));
    }

    @Test
    void testShipOutOfBounds_Bottom() {
        // Place a 3x1 Destroyer at (9,0) vertically (partially out of the bottom boundary)
        Ship<Character> ship = factory.makeDestroyer(new Placement("J0V"));
        assertEquals("That placement is invalid: the ship goes off the bottom of the board.",
                checker.checkPlacement(ship, board));
    }

    @Test
    void testShipOutOfBounds_Left() {
        // Place a 3x1 Carrier at (5,-1) horizontally (partially out of the left boundary)
        Ship<Character> ship = factory.makeCarrier(new Placement(new Coordinate(5, -1), 'H'));
        assertEquals("That placement is invalid: the ship goes off the left of the board.",
                checker.checkPlacement(ship, board));
    }

    @Test
    void testShipOutOfBounds_Right() {
        // Place a 3x1 Carrier at (5,9) horizontally (partially out of the right boundary)
        Ship<Character> ship = factory.makeCarrier(new Placement("E9H"));
        assertEquals("That placement is invalid: the ship goes off the right of the board.",
                checker.checkPlacement(ship, board));
    }

    @Test
    void testNoCollision() {
        Ship<Character> ship1 = factory.makeDestroyer(new Placement("A0V"));
        assertNull(checker.checkPlacement(ship1, board), "Destroyer should be placed legally.");
        board.tryAddShip(ship1); // Place ship

        Ship<Character> ship2 = factory.makeCarrier(new Placement("A1V"));
        assertNull(checker.checkPlacement(ship2, board), "Carrier should be placed legally.");
        board.tryAddShip(ship2); // Place ship

        Ship<Character> ship3 = factory.makeCarrier(new Placement("B0V"));
        assertEquals("That placement is invalid: the ship overlaps another ship.",
                checker.checkPlacement(ship3, board));
    }
}