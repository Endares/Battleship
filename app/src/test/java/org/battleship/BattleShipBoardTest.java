package org.battleship;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BattleShipBoardTest {
    @Test
    public void test_width_and_height() {
        Board<Character> b1 = new BattleShipBoard<>(10, 20);
        assertEquals(10, b1.getWidth());
        assertEquals(20, b1.getHeight());
    }

    @Test
    public void test_invalid_dimensions() {
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5));
        assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20));
    }

    @Test
    public void test_checkWhatIsAtBoard() {
        BattleShipBoard<Character> board = new BattleShipBoard<>(3, 3);
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
                T actual = b.whatIsAt(coord);
                T expect = expected[i][j];
                assertEquals(expect, actual);
            }
        }
    }
}