package org.battleship;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTextPlayerTest {

    @Test
    void testComputerPlayerActions() throws IOException {
        BufferedReader input = new BufferedReader(new StringReader(""));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);

        Board<Character> theBoard = new BattleShipBoard<>(10, 10, 'X');
        V2ShipFactory factory = new V2ShipFactory();

        ComputerTextPlayer computerPlayer = new ComputerTextPlayer("Computer", theBoard, input, out, factory);

        computerPlayer.doPlacementPhase();
        String placementOutput = outputStream.toString();
        assertTrue(placementOutput.contains("Invalid placemen") || !placementOutput.isEmpty(),
                "Placement phase should produce output");

        outputStream.reset();

        ComputerTextPlayer enemyPlayer = new ComputerTextPlayer("Enemy", theBoard, input, out, factory);
        computerPlayer.makeActionChoice(enemyPlayer);
        String actionOutput = outputStream.toString();
        assertTrue(actionOutput.contains("Player Computer") || !actionOutput.isEmpty(),
                "Action choice should produce output");

        outputStream.reset();

        computerPlayer.randomFire(enemyPlayer);
        String fireOutput = outputStream.toString();
        assertTrue(fireOutput.contains("hit") || fireOutput.contains("missed"),
                "Fire action should result in either hit or missed");

        outputStream.reset();

        assertDoesNotThrow(() -> computerPlayer.doMovementPhase(),
                "doMovementPhase should not throw any exceptions");

        System.out.println("Test Output:\n" + outputStream);
    }
}
