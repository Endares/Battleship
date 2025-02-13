package org.battleship;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class TextPlayerTest {
    @Test
    void test_read_placement() throws IOException {
        // StringReader: we can construct it with a String,
        // and then read from it like an input stream.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);

        String prompt = "Please enter a location for a ship:";
        Placement[] expected = new Placement[3];
        expected[0] = new Placement(new Coordinate(1, 2), 'V');
        expected[1] = new Placement(new Coordinate(2, 8), 'H');
        expected[2] = new Placement(new Coordinate(0, 4), 'V');

        //  get those three Placements from app.readPlacement
        for (int i = 0; i < expected.length; i++) {
            Placement p = player.readPlacement(prompt);
            assertEquals(p, expected[i]); //did we get the right Placement back
            assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
            bytes.reset(); //clear out bytes for next time around
        }
    }

//    @Disabled
//    @Test
//    public void test_doOnePlacement() throws IOException {
//        // Step 1: Simulate user input for placement (e.g., "A0V")
//        // Step 2: Create board and App instance
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        TextPlayer player = createTextPlayer(3, 3, "a0v\n", bytes);
//        // Step 3: Run `doOnePlacement()`
//        player.doOnePlacement();
//
//        // Step 4: Expected output after placing the ship
//        String expectedOutput =
//                "  0|1|2\n" +
//                        "A d| |  A\n" +
//                        "B d| |  B\n" +
//                        "C d| |  C\n" +
//                        "  0|1|2\n";
//        String prompt = "Player A where do you want to place a Destroyer?\n";
//        // Step 5: Verify board output
//        assertEquals((prompt + expectedOutput).trim(), bytes.toString().trim());
//    }

    private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
        BufferedReader input = new BufferedReader(new StringReader(inputData));
        PrintStream output = new PrintStream(bytes, true);
        Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        return new TextPlayer("A", board, input, output, shipFactory);
    }

//    @Disabled
//    @Test
//    public void test_doPlacementPhase() throws IOException {
//        String inputData = "A0V\nB2H\nC4V\n";
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        TextPlayer player = createTextPlayer(10, 10, inputData, bytes);
//        String expectedEmptyBoard =
//                "  0|1|2|3|4|5|6|7|8|9\n" +
//                        "A  | | | | | | | | |  A\n" +
//                        "B  | | | | | | | | |  B\n" +
//                        "C  | | | | | | | | |  C\n" +
//                        "D  | | | | | | | | |  D\n" +
//                        "E  | | | | | | | | |  E\n" +
//                        "F  | | | | | | | | |  F\n" +
//                        "G  | | | | | | | | |  G\n" +
//                        "H  | | | | | | | | |  H\n" +
//                        "I  | | | | | | | | |  I\n" +
//                        "J  | | | | | | | | |  J\n" +
//                        "  0|1|2|3|4|5|6|7|8|9\n";
//
//        player.doPlacementPhase();
//
//        String expectedBoard =
//                        "  0|1|2|3|4|5|6|7|8|9\n" +
//                        "A d| | | | | | | | |  A\n" +
//                        "B d| | | | | | | | |  B\n" +
//                        "C d| | | | | | | | |  C\n" +
//                        "D  | | | | | | | | |  D\n" +
//                        "E  | | | | | | | | |  E\n" +
//                        "F  | | | | | | | | |  F\n" +
//                        "G  | | | | | | | | |  G\n" +
//                        "H  | | | | | | | | |  H\n" +
//                        "I  | | | | | | | | |  I\n" +
//                        "J  | | | | | | | | |  J\n" +
//                        "  0|1|2|3|4|5|6|7|8|9\n";
//        String expectedOutput = expectedEmptyBoard + "Player A: you are going to place the following ships (which are all\n" +
//                "rectangular). For each ship, type the coordinate of the upper left\n" +
//                "side of the ship, followed by either H (for horizontal) or V (for\n" +
//                "vertical).  For example M4H would place a ship horizontally starting\n" +
//                "at M4 and going to the right.  You have\n" +
//                "\n" +
//                "2 \"Submarines\" ships that are 1x2 \n" +
//                "3 \"Destroyers\" that are 1x3\n" +
//                "3 \"Battleships\" that are 1x4\n" +
//                "2 \"Carriers\" that are 1x6\n" +
//                "Player A where do you want to place a Destroyer?\n" +
//                expectedBoard;
//
//        assertEquals(expectedOutput.trim(), bytes.toString().trim());
//    }

    @Test
    void test_readPlacement_EOF() throws IOException {
        // Simulate an empty input (EOF scenario)
        BufferedReader emptyInput = new BufferedReader(new StringReader(""));
        PrintStream out = new PrintStream(new ByteArrayOutputStream());
        Board<Character> board = new BattleShipBoard<>(5, 5, 'X');
        V1ShipFactory shipFactory = new V1ShipFactory();
        TextPlayer player = new TextPlayer("Player1", board, emptyInput, out, shipFactory);

        // Expect an EOFException when input is exhausted
        assertThrows(EOFException.class, () -> player.readPlacement("Enter ship placement:"));
    }
}