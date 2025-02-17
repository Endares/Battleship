package org.battleship;

import java.util.LinkedHashMap;

public abstract class MovementRuleChecker<T> {
    private final MovementRuleChecker<T> next;
    // Subclasses will override this method to specify how they check their own rule.
    protected abstract String checkMyRule(Ship<T> theShip,
                                          Board<T> theBoard,
                                          LinkedHashMap<Coordinate, Boolean> newPieces);

    public MovementRuleChecker(MovementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * returns a error message if error occurs;
     * else returns null
     * @param theShip
     * @param theBoard
     * @return
     */
    public String checkMovement(Ship<T> theShip, Board<T> theBoard, LinkedHashMap<Coordinate, Boolean> newPieces) {
        String res = checkMyRule(theShip, theBoard, newPieces);
        //if we fail our own rule: stop the placement is not legal
        if (res != null) {
            return res;
        }
        //otherwise, ask the rest of the chain.
        return (next == null) ? null : next.checkMovement(theShip, theBoard, newPieces);
        //if there are no more rules, then the placement is legal
    }
}
