package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementRuleCheckerTest {
    private final BattleShipBoard<Character> board = new BattleShipBoard<>(10, 10);
    private final V1ShipFactory factory = new V1ShipFactory();
    private final InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);

    @Test
    void testShipLegalInBounds() {
        // Place a 3x1 Battleship at (5,5) vertically
        Ship<Character> ship = factory.makeBattleship(new Placement("E5V"));
        assertTrue(board.tryAddShip(ship), "Battleship should be placed legally.");
    }

    @Test
    void testShipOutOfBounds_Bottom() {
        // Place a 3x1 Destroyer at (0,0) vertically (partially out of the top boundary)
        Ship<Character> ship = factory.makeDestroyer(new Placement("I0V"));
        assertFalse(board.tryAddShip(ship), "Destroyer exceeds the bottom boundary and should be illegal.");
    }

    @Test
    void testNoCollision() {
        Ship<Character> ship1 = factory.makeDestroyer(new Placement("A0V"));
        assertTrue(board.tryAddShip(ship1), "Destroyer should be placed legally.");

        Ship<Character> ship2 = factory.makeCarrier(new Placement("A1V"));
        assertTrue(board.tryAddShip(ship2), "Carrier should be placed legally.");

        Ship<Character> ship3 = factory.makeCarrier(new Placement("D1V"));
        assertFalse(board.tryAddShip(ship3), "Placing another Carrier at the same position should fail due to collision.");
    }
}