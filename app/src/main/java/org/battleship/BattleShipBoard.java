package org.battleship;

import java.util.ArrayList;
import java.util.HashSet;

public class BattleShipBoard<T> implements Board<T> {
    private final int width;
    private final int height;
    private ArrayList<Ship<T>> myShips;
    private final PlacementRuleChecker<T> placementChecker;
    private HashSet<Coordinate> enemyMisses;
    // miss: returns null at enemy's view, fill-in with missInfo(e.g.'X') when print the board
    private final T missInfo;

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    /**
     * Constructs a BattleShipBoard with the specified width
     * and height
     * @param w is the width of the newly constructed board.
     * @param h is the height of the newly constructed board.
     * @throws IllegalArgumentException if the width or height are less than or equal to zero.
     */
    public BattleShipBoard(int w, int h, T missInfo) {
        if (w <= 0 || h <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive, got: " + w + "x" + h);
        }
        this.width = w;
        this.height = h;
        this.myShips = new ArrayList<>();
        this.placementChecker = new NoCollisionRuleChecker<>(new InBoundsRuleChecker<>(null)); // Initialize checker properly
        this.enemyMisses = new HashSet<>();
        this.missInfo = missInfo;
    }

    /**
     * checks the validity of the placement and returns
     * true if the placement was ok, and false if it was invalid (and thus not actually placed)
     */
    public boolean tryAddShip(Ship<T> toAdd) {
        if (placementChecker.checkPlacement(toAdd, this) == null) {
            myShips.add(toAdd);
            return true;
        }
        return false;
    }

    /**
     *                                 Our own               Enemy
     * square has unhit ship     ship's letter (s,d,c,b)     blank
     * square has hit ship               *               ship's letter (s,d,c,b)
     * square has miss                                         X
     * square is empty, unmissed
     */
    public T whatIsAtForSelf(Coordinate where) {
        return whatIsAt(where, true);
    }
    public T whatIsAtForEnemy(Coordinate where) {
        return whatIsAt(where, false);
    }
    /**
     * From our own perspective:
     * This method takes a Coordinate, and sees which (if any) Ship
     * occupies that coordinate.  If one is found, we return whatever
     * displayInfo it has at those coordinates.  If
     * none is found, we return null.
     */
    protected T whatIsAt(Coordinate where, boolean isSelf) {
        for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(where)){  // for ship pieces
                return s.getDisplayInfoAt(where, isSelf);
                // calls self/enemy.getInfo
                // self: on hit: '*'(onHit) else: 'd' (data)
                // enemy: on hit: 'd'(data) else: ' ' (null)
                // called by: public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
                //    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit),
                //        new SimpleShipDisplayInfo<T>(null, data));
                //  }
            }
        }
        // for elsewhere: enemy miss: 'X'(missInfo); else : ' '(null)
        return (!isSelf && enemyMisses.contains(where)) ? missInfo : null;
    }

    /**
     * search for any ship that occupies coordinate c
     * (you already have a method to check that)
     * If one is found, that Ship is "hit" by the attack and should
     * record it (you already have a method for that!).  Then we
     * should return this ship.
     * If no ships are at this coordinate, we should record
     * the miss in the enemyMisses HashSet that we just made,
     * and return null.
     * @param c
     * @return
     */
    public Ship<T> fireAt(Coordinate c) {
        for (Ship<T> s: myShips) {
            if (s.occupiesCoordinates(c)) {
                s.recordHitAt(c);
                return s;
            }
        }
        enemyMisses.add(c);
        return null;
    }

}
