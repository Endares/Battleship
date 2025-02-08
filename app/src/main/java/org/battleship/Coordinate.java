package org.battleship;

public class Coordinate {
    private final int row, column;
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public Coordinate(int row, int column) {
        if (row < 0 || row > 26 || column < 0 || column > 10) {
            throw new IllegalArgumentException("Invalid coordinate");
        }
        this.row = row;
        this.column = column;
    }
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
