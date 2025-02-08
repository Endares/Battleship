package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlacementTest {
    @Test
    void placementTest() {
        Coordinate c1 = new Coordinate(3, 5);
        Coordinate c2 = new Coordinate(3, 5);
        Placement p1 = new Placement(c1, 'v');
        Placement p2 = new Placement(c2, 'V');
        assertEquals(p1, p2);
    }
    @Test
    public void testConstructorWithCoordinate() {
        Coordinate c = new Coordinate(2, 3);
        Placement p = new Placement(c, 'v');

        assertEquals(c, p.getWhere());
        assertEquals('V', p.getOrientation());
    }

    @Test
    public void testConstructorWithString() {
        Placement p = new Placement("C3H");

        assertEquals(new Coordinate(2, 3), p.getWhere());
        assertEquals('H', p.getOrientation());
    }

    @Test
    public void testInvalidOrientation() {
        Coordinate c = new Coordinate(2, 3);
        assertThrows(IllegalArgumentException.class, () -> new Placement(c, 'X'));
    }

    @Test
    public void testInvalidStringPlacement() {
        assertThrows(IllegalArgumentException.class, () -> new Placement("C3X"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("C33"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("C#H"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("A2V3"));
        assertThrows(IllegalArgumentException.class, () -> new Placement("#2V"));
    }

    @Test
    public void testGetters() {
        Placement p = new Placement("B4V");

        assertEquals(new Coordinate(1, 4), p.getWhere());
        assertEquals('V', p.getOrientation());
    }

    @Test
    public void testToString() {
        Placement p = new Placement("D5H");
        assertEquals("Placement [where=(3, 5), orientation=H]", p.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Placement p1 = new Placement("E6V");
        Placement p2 = new Placement("E6v");
        Placement p3 = new Placement("E6H");
        Placement p4 = new Placement("F6V");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);

        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }
    @Test
    public void testEquals_DifferentClass() {
        Placement p1 = new Placement("B2H");
        String otherObject = "Not a Placement";
        assertNotEquals(p1, otherObject);
    }
}