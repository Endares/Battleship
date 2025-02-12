package org.battleship;

/**
 * Check if a ship is within the bounds of the board
 * No ship collision check here
 * @param <T>
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {
    public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
        super(next);
    }

    @Override
    protected boolean checkMyRule(Ship<T> theShip, Board<T> theBoard) {
        for (Coordinate c : theShip.getCoordinates()) {
            if (c.getRow() > theBoard.getHeight() - 1 || c.getRow() < 0 || c.getColumn() > theBoard.getWidth() - 1 || c.getColumn() < 0) {
                return false;
            }
        }
        return true;
    }
}
