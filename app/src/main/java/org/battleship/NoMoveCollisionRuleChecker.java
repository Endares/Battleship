package org.battleship;

import java.util.LinkedHashMap;

public class NoMoveCollisionRuleChecker<T> extends MovementRuleChecker<T> {
    public NoMoveCollisionRuleChecker(MovementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected String checkMyRule(Ship<T> theShip, Board<T> theBoard, LinkedHashMap<Coordinate, Boolean> newPieces) {
        for (Coordinate c : newPieces.keySet()) {
            Ship<T> s = theBoard.getShipAt(c);
            if (s != null && s != theShip) return "That movement is invalid: the ship overlaps another ship.";
        }
        return null;
    }
}
