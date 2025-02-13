package org.battleship;

/**
 * Chain of Responsibility pattern
 * @param <T>
 */
public abstract class PlacementRuleChecker<T> {
    private final PlacementRuleChecker<T> next;
    // Subclasses will override this method to specify how they check their own rule.
    protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

    public PlacementRuleChecker(PlacementRuleChecker<T> next) {
        this.next = next;
    }

    /**
     * returns a error message if error occurs;
     * else returns null
     * @param theShip
     * @param theBoard
     * @return
     */
    public String checkPlacement (Ship<T> theShip, Board<T> theBoard) {
        String res = checkMyRule(theShip, theBoard);
        //if we fail our own rule: stop the placement is not legal
        if (res != null) {
            return res;
        }
        //otherwise, ask the rest of the chain.
        return (next == null) ? null : next.checkPlacement(theShip, theBoard);
        //if there are no more rules, then the placement is legal
    }
}
