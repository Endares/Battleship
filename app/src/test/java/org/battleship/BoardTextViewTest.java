package org.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTextViewTest {
    @Test
    public void test_display_empty_2by2() {
        String expectedHeader = "  0|1\n";
        String expectedBody =
                "A  |  A\n" +
                "B  |  B\n";
        emptyBoardHelper(2, 2, expectedHeader, expectedBody);
    }
    @Test
    public void test_display_empty_3by2() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                "A  | |  A\n" +
                "B  | |  B\n";
        emptyBoardHelper(3, 2, expectedHeader, expectedBody);
    }
    @Test
    public void test_display_empty_3by5() {
        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                "A  | |  A\n" +
                "B  | |  B\n" +
                "C  | |  C\n" +
                "D  | |  D\n" +
                "E  | |  E\n";
        emptyBoardHelper(3, 5, expectedHeader, expectedBody);
    }

    private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
        Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
        BoardTextView view = new BoardTextView(b1);
        assertEquals(expectedHeader, view.makeHeader());
        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_display_nonempty_3by3() {
        Board<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
        BoardTextView view = new BoardTextView(b1);

        b1.tryAddShip(new RectangleShip<>(new Coordinate(1, 1), 's', '*'));
        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                        "A  | |  A\n" +
                        "B  |s|  B\n" +
                        "C  | |  C\n";

        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayMyOwnBoard());
    }

    @Test
    public void test_invalid_board_size() {
        Board<Character> wideBoard = new BattleShipBoard<Character>(11,20, 'X');
        Board<Character> tallBoard = new BattleShipBoard<Character>(10,27, 'X');
        //you should write two assertThrows here
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
        assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
    }


    @Test
    public void test_display_enemy_board() {
        Board<Character> b1 = new BattleShipBoard<Character>(3, 3, 'X');
        BoardTextView view = new BoardTextView(b1);
        V1ShipFactory factory = new V1ShipFactory();

        // add a ship
        Ship<Character> ship = factory.makeDestroyer(new Placement("A0V"));
        b1.tryAddShip(ship);

        // fire
        b1.fireAt(new Coordinate(0, 0)); // hit
        b1.fireAt(new Coordinate(2, 2)); // miss
        b1.fireAt(new Coordinate(2, 1)); // miss

        String expectedHeader = "  0|1|2\n";
        String expectedBody =
                "A d| |  A\n" +
                        "B  | |  B\n" +
                        "C  |X|X C\n";

        String expected = expectedHeader + expectedBody + expectedHeader;
        assertEquals(expected, view.displayEnemyBoard());
    }
}