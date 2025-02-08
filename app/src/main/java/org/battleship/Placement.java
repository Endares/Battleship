package org.battleship;

/**
 * Place a Ship onto Board at coordinate where
 * direction: orientation V / H
 */
public class Placement {
    private final Coordinate where;
    private final char orientation;

    public Placement(Coordinate where, char orientation) {
        orientation = Character.toUpperCase(orientation);
        if (orientation != 'V' && orientation != 'H') {
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
        this.where = where;
        this.orientation = orientation;
    }
    public Coordinate getWhere() {
        return where;
    }
    public char getOrientation() {
        return orientation;
    }
    public Placement(String str) {
        if (str.length() != 3) {
            throw new IllegalArgumentException("Invalid length of placement string");
        }
        char rowLetter = Character.toUpperCase(str.charAt(0));
        char columnLetter = str.charAt(1);
        char orientLetter = Character.toUpperCase(str.charAt(2));
        if (rowLetter < 'A' || rowLetter > 'Z') {
            throw new IllegalArgumentException("Invalid row coordinate: " + rowLetter);
        }
        if (columnLetter < '0' || columnLetter > '9') {
            throw new IllegalArgumentException("Invalid column coordinate: " + columnLetter);
        }
        if (orientLetter != 'V' && orientLetter != 'H') {
            throw new IllegalArgumentException("Invalid orientation letter: " + orientLetter);
        }
        this.where = new Coordinate(rowLetter - 'A', columnLetter - '0');
        this.orientation = orientLetter;
    }

    @Override
    public String toString() {
        return "Placement [where=" + where + ", orientation=" + orientation + "]";
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Placement p = (Placement) o;
            return where.equals(p.where) && orientation == p.orientation;
            // where == p.where compares references, not values.
        }
        return false;
    }
}
