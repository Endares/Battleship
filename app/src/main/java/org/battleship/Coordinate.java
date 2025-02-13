package org.battleship;

public class Coordinate {
    private final int row, column;
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }

    /**
     * This constructor will not test the legalness of row and column,
     * so wemay have row = -1 here.
     * @param row
     * @param column
     */
    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * This constructor will test the legalness of the String to convert it into coordinate
     * @param descr
     */
    public Coordinate(String descr) {
        if (descr.length() != 2) {
            throw new IllegalArgumentException("Invalid description length");
        }

        char rowLetter = Character.toUpperCase(descr.charAt(0));
        char columnLetter = descr.charAt(1);
        if (rowLetter < 'A' || rowLetter > 'Z') {
            throw new IllegalArgumentException("Invalid row coordinate: " + rowLetter);
        }
        if (columnLetter < '0' || columnLetter > '9') {
            throw new IllegalArgumentException("Invalid column coordinate: " + columnLetter);
        }

        this.row = rowLetter - 'A';
        this.column = columnLetter - '0';
    }

    @Override
    public String toString() {
        return "("+row+", " + column+")";
    }
    @Override
    public boolean equals(Object o) {
        if (o.getClass().equals(getClass())) {
            Coordinate c = (Coordinate) o;
            return row == c.row && column == c.column;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
