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

    @Test
    public void test_readAndFire_invalidPlacement() throws IOException {
        V1ShipFactory shipFactory = new V1ShipFactory();
        // Input sequence:
        // 1. Invalid coordinate: Z9
        // 2. Valid coordinate: A1
        String simulatedInput = "a0v\nA1\n";
        BufferedReader input = new BufferedReader(new StringReader(simulatedInput));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        // Initialize player boards
        Board<Character> myBoard = new BattleShipBoard<>(5, 5, 'X');
        Board<Character> enemyBoard = new BattleShipBoard<>(5, 5, 'X');
        TextPlayer playerA = new TextPlayer("PlayerA", myBoard, input, out, new V1ShipFactory());
        TextPlayer playerB = new TextPlayer("PlayerB", enemyBoard, new BufferedReader(new StringReader("")), out, new V1ShipFactory());

        // Place a submarine at A1 on enemy board
        enemyBoard.tryAddShip(shipFactory.makeSubmarine(new Placement("A1V")));

        // Simulate firing phase
        playerA.readAndFire(playerB, "Where do you want to fire at?");

        String output = outputStream.toString();

        // Verify output contains expected prompts
        assertTrue(output.contains("Invalid coordinate. Please try again."), "Should prompt for invalid coordinate.");
        assertTrue(output.contains("You hit a Submarine!"), "Should confirm hit on Submarine.");

    }
//    @Test
//    public void test_readAndFire_inValidCoordinate() throws IOException {
//        V1ShipFactory shipFactory = new V1ShipFactory();
//        // 1. Invalid coordinate: A0V
//        // 2. Valid coordinate: A1
//        String simulatedInput = "a0v\nA1\n";
//        BufferedReader input = new BufferedReader(new StringReader(simulatedInput));
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        PrintStream out = new PrintStream(outputStream);
//
//        // Initialize player boards
//        Board<Character> myBoard = new BattleShipBoard<>(5, 5, 'X');
//        Board<Character> enemyBoard = new BattleShipBoard<>(5, 5, 'X');
//        TextPlayer playerA = new TextPlayer("PlayerA", myBoard, input, out, new V1ShipFactory());
//        TextPlayer playerB = new TextPlayer("PlayerB", enemyBoard, new BufferedReader(new StringReader("")), out, new V1ShipFactory());
//
//        String output = outputStream.toString();
//        // Place a submarine at A1 on enemy board
//        enemyBoard.tryAddShip(shipFactory.makeSubmarine(new Placement("A1V")));
//
//        // Simulate firing phase
//        playerA.readAndFire(playerB, "Where do you want to fire at?");
//
//        // Verify output contains expected prompts
//        assertTrue(output.contains("Invalid coordinate. Please try again."), "Should prompt for invalid coordinate.");
//        assertTrue(output.contains("You hit a Submarine!"), "Should confirm hit on Submarine.");
//    }
    @Test
    public void test_readAndFire_inValidPlacement() throws IOException {
        V1ShipFactory shipFactory = new V1ShipFactory();
        // Input sequence:
        // 1. Invalid placement: A0j
        // 2. Valid placement: a3v
        String simulatedInput = "A0j\na3v\n";
        BufferedReader input = new BufferedReader(new StringReader(simulatedInput));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        // Initialize player boards
        Board<Character> myBoard = new BattleShipBoard<>(5, 5, 'X');
        Board<Character> enemyBoard = new BattleShipBoard<>(5, 5, 'X');
        TextPlayer playerA = new TextPlayer("PlayerA", myBoard, input, out, new V1ShipFactory());
        TextPlayer playerB = new TextPlayer("PlayerB", enemyBoard, new BufferedReader(new StringReader("")), out, new V1ShipFactory());

        // Placement failure test
        playerA.doOnePlacement("Submarine", shipFactory::makeSubmarine);

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input format"),
                "Expected 'Invalid input format' message for wrong placement.");
    }

    @Test
    public void test_readAndFire_eofException() {
        String simulatedInput = "";
        BufferedReader input = new BufferedReader(new StringReader(simulatedInput));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        Board<Character> myBoard = new BattleShipBoard<>(5, 5, 'X');
        Board<Character> enemyBoard = new BattleShipBoard<>(5, 5, 'X');
        TextPlayer playerA = new TextPlayer("PlayerA", myBoard, input, out, new V1ShipFactory());
        TextPlayer playerB = new TextPlayer("PlayerB", enemyBoard, input, out, new V1ShipFactory());

        assertThrows(EOFException.class,
                () -> playerA.readAndFire(playerB, "Where do you want to fire?"),
                "Should throw EOFException。");
    }

    @Test
    public void test_sonarScan() {
        V2ShipFactory factory = new V2ShipFactory();

        Board<Character> theBoard = new BattleShipBoard<>(7, 7, 'X');
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = new PrintStream(System.out);

        TextPlayer playerA = new TextPlayer("PlayerA", theBoard, input, out, new V1ShipFactory());
        theBoard.tryAddShip(factory.makeSubmarine(new Placement("A1V")));
        theBoard.tryAddShip(factory.makeSubmarine(new Placement("C4h")));
        theBoard.tryAddShip(factory.makeDestroyer(new Placement("e1h")));
        theBoard.tryAddShip(factory.makeBattleship(new Placement("d4l")));
        theBoard.tryAddShip(factory.makeCarrier(new Placement("f0r")));

        Coordinate center = new Coordinate(4, 4);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(outputStream);
        TextPlayer testPlayer = new TextPlayer("PlayerA", theBoard, input, testOut, new V2ShipFactory());
        testPlayer.sonarScan(center);
        String output = outputStream.toString();

        String expected = "Submarines occupy 2 squares\n" +
                "Destroyers occupy 3 squares\n" +
                "Battleships occupy 4 squares\n" +
                "Carriers occupy 3 squares\n";

        assertEquals(expected.trim(), output.trim());
    }
}