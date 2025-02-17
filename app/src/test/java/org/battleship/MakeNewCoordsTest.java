package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MakeNewCoordsTest {
    @Test
    public void test_moveRectangleShip() {
        V2ShipFactory factory = new V2ShipFactory();
        Board<Character> theBoard = new BattleShipBoard<>(5, 5, 'X');
        theBoard.tryAddShip(factory.makeSubmarine(new Placement("A1V")));
        theBoard.tryAddShip(factory.makeSubmarine(new Placement("C3h")));
        theBoard.tryAddShip(factory.makeDestroyer(new Placement("e1h")));

        assertEquals(" |s| | | |\n" +
                  " |s| | | |\n" +
                  " | | |s|s|\n" +
                  " | | | | |\n" +
                  " |d|d|d| |\n",
                printBoard(theBoard));

        Ship<Character> s = theBoard.getShipAt(new Coordinate("c4"));
        theBoard.tryMoveShip(s, new Placement("b3v"));

        assertEquals(" |s| | | |\n" +
              " |s| |s| |\n" +
              " | | |s| |\n" +
              " | | | | |\n" +
              " |d|d|d| |\n",
                printBoard(theBoard));
    }

    @Test
    public void test_moveNonRectangleShip() {
        V2ShipFactory factory = new V2ShipFactory();
        Board<Character> theBoard = new BattleShipBoard<>(8, 8, 'X');
        theBoard.tryAddShip(factory.makeBattleship(new Placement("A1U")));
        theBoard.tryAddShip(factory.makeCarrier(new Placement("e2r")));
        theBoard.tryAddShip(factory.makeBattleship(new Placement("c5d")));

        assertEquals(" | |b| | | | | |\n" +
                        " |b|b|b| | | | |\n" +
                        " | | | | |b|b|b|\n" +
                        " | | | | | |b| |\n" +
                        " | | |c|c|c|c| |\n" +
                        " | |c|c|c| | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        // success movement of battleship
        Ship<Character> s = theBoard.getShipAt(new Coordinate("b2"));
        theBoard.tryMoveShip(s, new Placement("d0l"));

        assertEquals(" | | | | | | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | |b|b|b|\n" +
                        " |b| | | | |b| |\n" +
                        "b|b| |c|c|c|c| |\n" +
                        " |b|c|c|c| | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        // invalid movement due to overlap
        s = theBoard.getShipAt(new Coordinate("f4"));
        assertFalse(theBoard.tryMoveShip(s, new Placement("c5d")), "Can't move ship to c5d, will overlap.");

        // success movement of carrier
        theBoard.tryMoveShip(s, new Placement("c3d"));
        assertEquals(" | | | | | | | |\n" +
                        " | | | | | | | |\n" +
                        " | | |c| |b|b|b|\n" +
                        " |b| |c|c| |b| |\n" +
                        "b|b| |c|c| | | |\n" +
                        " |b| | |c| | | |\n" +
                        " | | | |c| | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        // ship not found at empty coordinate
        s = theBoard.getShipAt(new Coordinate("a2"));
        assertNull(s, "Not ship at a2.");

        // invalid movement due to out-of-bounds
        s = theBoard.getShipAt(new Coordinate("c6"));
        assertFalse(theBoard.tryMoveShip(s, new Placement("c7r")), "Can't move ship to c7r, out-of-bounds.");
    }

    @Test
    public void test_moveAfterFire() {
        V2ShipFactory factory = new V2ShipFactory();
        Board<Character> theBoard = new BattleShipBoard<>(8, 8, 'X');
        theBoard.tryAddShip(factory.makeBattleship(new Placement("A1U")));
        theBoard.tryAddShip(factory.makeCarrier(new Placement("e2r")));
        theBoard.tryAddShip(factory.makeBattleship(new Placement("c5d")));

        assertEquals(" | |b| | | | | |\n" +
                        " |b|b|b| | | | |\n" +
                        " | | | | |b|b|b|\n" +
                        " | | | | | |b| |\n" +
                        " | | |c|c|c|c| |\n" +
                        " | |c|c|c| | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        theBoard.fireAt(new Coordinate("a2"));
        theBoard.fireAt(new Coordinate("a3"));
        theBoard.fireAt(new Coordinate("b2"));
        theBoard.fireAt(new Coordinate("c6"));
        theBoard.fireAt(new Coordinate("e5"));
        assertEquals(" | |*| | | | | |\n" +
                        " |b|*|b| | | | |\n" +
                        " | | | | |b|*|b|\n" +
                        " | | | | | |b| |\n" +
                        " | | |c|c|*|c| |\n" +
                        " | |c|c|c| | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        // success movement of battleship
        Ship<Character> s = theBoard.getShipAt(new Coordinate("b2"));
        theBoard.tryMoveShip(s, new Placement("d0l"));

        assertEquals(" | | | | | | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | |b|*|b|\n" +
                        " |b| | | | |b| |\n" +
                        "*|*| |c|c|*|c| |\n" +
                        " |b|c|c|c| | | |\n" +
                        " | | | | | | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));

        // success movement of carrier
        s = theBoard.getShipAt(new Coordinate("f3"));
        theBoard.tryMoveShip(s, new Placement("c3d"));
        assertEquals(" | | | | | | | |\n" +
                        " | | | | | | | |\n" +
                        " | | |c| |b|*|b|\n" +
                        " |b| |c|c| |b| |\n" +
                        "*|*| |c|c| | | |\n" +
                        " |b| | |*| | | |\n" +
                        " | | | |c| | | |\n" +
                        " | | | | | | | |\n",
                printBoard(theBoard));
    }

    private String printBoard(Board<Character> board) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < board.getHeight(); ++i) {
            for (int j = 0; j < board.getWidth(); ++j) {
                Character c = board.whatIsAtForSelf(new Coordinate(i, j));
                res.append(c == null ? " " : c).append("|");
            }
            res.append("\n");
        }
        return res.toString();
    }
}