package org.battleship;

import javax.sound.sampled.Line;
import java.util.LinkedHashMap;

/**
 * Return new <Coordinates, isHit> List for all pieces when moving a ship
 * @param <T>
 */
public class MakeNewCoords<T> {
    /**
     * get newPieces List for ship to move to newPlacement
     */
    public LinkedHashMap<Coordinate, Boolean> getNewCoords(Ship<T> ship, Placement newPlacement) {
        String name = ship.getName().toLowerCase();
        // rectangle ship
        switch (name) {
            case "submarine", "destroyer" -> {
                return makeRectCoords(ship.getMyPieces(), newPlacement);
            }
            case "battleship" -> {
                // Placement's constructor makes sure right format of Placement
                if (newPlacement.getOrientation() == 'U') {
                    return makeUpBattle(ship.getMyPieces(), newPlacement);
                } else if (newPlacement.getOrientation() == 'R') {
                    return makeRightBattle(ship.getMyPieces(), newPlacement);
                } else if (newPlacement.getOrientation() == 'D') {
                    return makeDownBattle(ship.getMyPieces(), newPlacement);
                } else {
                    return makeLeftBattle(ship.getMyPieces(), newPlacement);
                }
                // Placement's constructor makes sure right format of Placement
            }
            case "carrier" -> {
                if (newPlacement.getOrientation() == 'U') {
                    return makeUpCarrier(ship.getMyPieces(), newPlacement);
                } else if (newPlacement.getOrientation() == 'R') {
                    return makeRightCarrier(ship.getMyPieces(), newPlacement);
                } else if (newPlacement.getOrientation() == 'D') {
                    return makeDownCarrier(ship.getMyPieces(), newPlacement);
                } else {
                    return makeLeftCarrier(ship.getMyPieces(), newPlacement);
                }
            }
            default -> throw new IllegalArgumentException("Undefined ship kind: " + name);
        }
    }

    /**
     * Make new Coordinate Sets for a moved RectangleShip
     * orientation can only be H/V
     * @param oldPieces: set of old coords, important info about hit/miss
     * @param newPlacement: move to here
     * @return
     */
    public LinkedHashMap<Coordinate, Boolean> makeRectCoords(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        LinkedHashMap<Coordinate, Boolean> newPieces = new LinkedHashMap<>();
        int index = 0;
        // left upper coordinate of new ship position
        int row = newPlacement.getWhere().getRow();
        int column = newPlacement.getWhere().getColumn();

        if (newPlacement.getOrientation() == 'V') {
            for (var entry : oldPieces.entrySet()) {
                newPieces.put(new Coordinate(row + index, column), entry.getValue());
                ++index;
            }
        } else if (newPlacement.getOrientation() == 'H') {
            for (var entry : oldPieces.entrySet()) {
                newPieces.put(new Coordinate(row, column + index), entry.getValue());
                ++index;
            }
        }
        return newPieces;
    }


    /**
     * Used for NonRectangleShip: battleship and carrier
     * @param offsets : new Placement's offest from UpperLeft of each piece
     */
    protected LinkedHashMap<Coordinate, Boolean> makeCoordsKey(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement, int[][] offsets) {
        LinkedHashMap<Coordinate, Boolean> newPieces = new LinkedHashMap<>();
        // left upper coordinate of new ship position
        int baseRow = newPlacement.getWhere().getRow();
        int baseCol = newPlacement.getWhere().getColumn();
        int index = 0;
        for (var entry : oldPieces.entrySet()) {
            int newRow = baseRow + offsets[index][0];
            int newCol = baseCol + offsets[index][1];
            newPieces.put(new Coordinate(newRow, newCol), entry.getValue());
            ++index;
        }
        return newPieces;
    }

    /**
     * Make a coord list for a up-oriented battleship
     * *  b1
     * b2 b3 b4  , * or B stands for upperleft
     */
    public LinkedHashMap<Coordinate, Boolean> makeUpBattle(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {0, 1},  // index 0
                {1, 0},  // index 1
                {1, 1},  // index 2
                {1, 2}   // index 3
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a right-oriented battleship
     *    B2
     *    b3 b1
     *    b4
     */
    public LinkedHashMap<Coordinate, Boolean> makeRightBattle(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {1, 1},  // index 0
                {0, 0},  // index 1
                {1, 0},  // index 2
                {2, 0}   // index 3
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a down-oriented battleship
     * B4 b3 b2
     *    b1
     */
    public LinkedHashMap<Coordinate, Boolean> makeDownBattle(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {1, 1},  // index 0
                {0, 2},  // index 1
                {0, 1},  // index 2
                {0, 0}   // index 3
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a left-oriented battleship
     * *  b4
     * b1 b3
     *    b2
     */
    public LinkedHashMap<Coordinate, Boolean> makeLeftBattle(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {1, 0},  // index 0
                {2, 1},  // index 1
                {1, 1},  // index 2
                {0, 1}   // index 3
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a up-oriented carrier
     * C1
     * c2
     * c3 c5
     * c4 c6
     *    c7
     */
    public LinkedHashMap<Coordinate, Boolean> makeUpCarrier(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {0, 0},  // index 0
                {1, 0},  // index 1
                {2, 0},  // index 2
                {3, 0},   // index 3
                {2, 1},  // index 4
                {3, 1},  // index 5
                {4, 1},  // index 6
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a right-oriented carrier
     * *  c4 c3 c2 c1
     * c7 c6 c5
     */
    public LinkedHashMap<Coordinate, Boolean> makeRightCarrier(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {0, 4},  // index 0
                {0, 3},  // index 1
                {0, 2},  // index 2
                {0, 1},   // index 3
                {1, 2},  // index 4
                {1, 1},  // index 5
                {1, 0},  // index 6
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }

    /**
     * Make a coord list for a down-oriented carrier
     * C7
     * c6 c4
     * c5 c3
     *    c2
     *    c1
     */
    public LinkedHashMap<Coordinate, Boolean> makeDownCarrier(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {4, 1},  // index 0
                {3, 1},  // index 1
                {2, 1},  // index 2
                {1, 1},   // index 3
                {2, 0},  // index 4
                {1, 0},  // index 5
                {0, 0},  // index 6
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }


    /**
     * Make a coord list for a left-oriented carrier
     * *     c5 c6 c7
     * c1 c2 c3 c4
     */
    public LinkedHashMap<Coordinate, Boolean> makeLeftCarrier(LinkedHashMap<Coordinate, Boolean> oldPieces, Placement newPlacement) {
        int[][] offsets = {
                {1, 0},  // index 0
                {1, 1},  // index 1
                {1, 2},  // index 2
                {1, 3},   // index 3
                {0, 2},  // index 4
                {0, 3},  // index 5
                {0, 4},  // index 6
        };
        return makeCoordsKey(oldPieces, newPlacement, offsets);
    }
}
