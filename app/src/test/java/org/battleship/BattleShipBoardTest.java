package org.battleship;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BattleShipBoardTest {
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20, 'X');
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
    }

    @Test
    public void test_checkWhatIsAtBoard() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(3, 3, 'X');
        Character[][] expected = new Character[][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
        };
        checkWhatIsAtBoard(board, expected);

        Ship<Character> ship =  new RectangleShip<>(new Coordinate(1, 1), 's', '*');
        board.tryAddShip(ship);

        expected[1][1] = 's';
        checkWhatIsAtBoard(board, expected);
    }
    /**
     * b.whatIsAt(new Coordinate(row, col)) retrieves the actual value from the board.
     * expected[row][col] stores the expected value at that position (null or a specific T value).
     * We use assertEquals(expected[row][col], actual) to verify correctness.
     */
    private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
        int rows = b.getHeight(), cols = b.getWidth();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Coordinate coord = new Coordinate(i, j);
                T actual = b.whatIsAtForSelf(coord);
                T expect = expected[i][j];
                assertEquals(expect, actual);
            }
        }
    }
    @Test
    public void test_tryAddShip_fails() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(5, 5, 'X');
        Ship<Character> ship1 = new RectangleShip<>(new Coordinate(2, 2), 'D', '*');

        // First placement should succeed
        assertTrue(board.tryAddShip(ship1), "Ship should be placed successfully.");

        Ship<Character> ship2 = new RectangleShip<>(new Coordinate(2, 2), 'S', '*');

        // Second placement at same position should fail (collision)
        assertFalse(board.tryAddShip(ship2), "Ship placement should fail due to collision.");

        Ship<Character> ship3 = new RectangleShip<>(new Coordinate(4, 4), 'B', '*');

        // Trying to place a ship partially outside the board
        Ship<Character> ship4 = new RectangleShip<>(new Coordinate(4, 5), 'C', '*');
        assertFalse(board.tryAddShip(ship4), "Ship should not be placed because it exceeds board limits.");
    }

    @Test
    public void test_fireAt() {
        V1ShipFactory factory = new V1ShipFactory();
        BattleShipBoard<Character> board = new BattleShipBoard<>(10, 10, 'X');
        Ship<Character> ship1 = factory.makeDestroyer(new Placement("A0v"));
        board.tryAddShip(ship1);
        // test before firing: enemy should see nothing
        assertNull(board.whatIsAtForEnemy(new Coordinate(0, 0)), "Enemy should not see unhit ships.");
        assertNull(board.whatIsAtForEnemy(new Coordinate(5, 5)), "Enemy should see nothing before firing.");
        // hit miss
        assertNull(board.fireAt(new Coordinate(5, 5)), "Firing at empty water should return null.");
        assertEquals('X', board.whatIsAtForEnemy(new Coordinate(5, 5)), "Enemy should see 'X' for a missed shot.");
        assertThrows(IllegalArgumentException.class, ()->ship1.wasHitAt(new Coordinate(5, 5)));
        // Test hitting a ship
        Ship<Character> hitShip = board.fireAt(new Coordinate(0, 0));
        assertNotNull(hitShip, "Firing at a ship location should return the ship.");
        assertEquals('d', board.whatIsAtForEnemy(new Coordinate(0, 0)), "Enemy should see ship's letter after hit.");
        assertSame(ship1, hitShip, "The hit ship should be ship1.");
        // The difference is that
        // assertSame checks if the objects are exactly the same while
        // assertEquals checks if they are equal.
        assertTrue(ship1.wasHitAt(new Coordinate(0, 0)));
        board.fireAt(new Coordinate(1, 0));
        board.fireAt(new Coordinate(2, 0));
        assertEquals('d', board.whatIsAtForEnemy(new Coordinate(1, 0)), "Enemy should see ship at (1,0) after hit.");
        assertEquals('d', board.whatIsAtForEnemy(new Coordinate(2, 0)), "Enemy should see ship at (2,0) after hit.");
        assertTrue(ship1.isSunk());
    }
}