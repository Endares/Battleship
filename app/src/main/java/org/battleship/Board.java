package org.battleship;

public interface Board<T> {
    public int getWidth();
    public int getHeight();
    public boolean tryAddShip(Ship<T> toAdd);
    public boolean tryMoveShip(Ship<T> toMove, Placement newPlacement);
    public T whatIsAtForSelf(Coordinate where);
    public T whatIsAtForEnemy(Coordinate where);
    public Ship<T> fireAt(Coordinate c);
    public boolean allSunk();
    public String displayShipAt(Coordinate c);
    public Ship<T> getShipAt(Coordinate c);
    public Ship<T> getRandomShip();
}
