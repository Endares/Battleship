package org.battleship;

import java.util.LinkedHashMap;

public class InBoundsMoveRuleChecker<T> extends MovementRuleChecker<T> {
    public InBoundsMoveRuleChecker(MovementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard, LinkedHashMap<Coordinate, Boolean> newPieces) {
        for (Coordinate c : newPieces.keySet()) {
            if (c.getRow() < 0) return "That placement is invalid: the ship goes off the top of the board.";
            if (c.getRow() >= theBoard.getHeight()) return "That placement is invalid: the ship goes off the bottom of the board.";
            if (c.getColumn() < 0) return "That placement is invalid: the ship goes off the left of the board.";
            if (c.getColumn() >= theBoard.getWidth()) return "That placement is invalid: the ship goes off the right of the board.";
        }
        return null;
    }
}
