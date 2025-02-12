package org.battleship;

/**
 * Chain of Responsibility pattern
 * @param <T>
 */
public abstract class PlacementRuleChecker<T> {
    private final PlacementRuleChecker<T> next;
    // Subclasses will override this method to specify how they check their own rule.
    protected abstract boolean checkMyRule(Ship<T> theShip, Board<T> theBoard);

    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }
    public boolean checkPlacement (Ship<T> theShip, Board<T> theBoard) {
        //if we fail our own rule: stop the placement is not legal
        if (!checkMyRule(theShip, theBoard)) {
            return false;
        }
        //otherwise, ask the rest of the chain.
        if (next != null) {
            return next.checkPlacement(theShip, theBoard);
        }
        //if there are no more rules, then the placement is legal
        return true;
    }
}
