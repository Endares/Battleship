package org.battleship;

/**
 * check the rule that theShip does not collide with anything else
 * on theBoard (that all the squares it needs are empty).
 * @param <T>
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {
    public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (theBoard.whatIsAt(c) != null) return false;
        }
        return true;
    }
}
