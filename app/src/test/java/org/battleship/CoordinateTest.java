package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {
    @Test
    public void test_equals() {
        Coordinate c1 = new Coordinate(1, 2);
        Coordinate c2 = new Coordinate(1, 2);
        Coordinate c3 = new Coordinate(1, 3);
        Coordinate c4 = new Coordinate(3, 2);
        assertEquals(c1, c1);   //equals should be reflexsive
        assertEquals(c1, c2);   //different objects bu same contents
        assertNotEquals(c1, c3);  //different contents
        assertNotEquals(c1, c4);
        assertNotEquals(c3, c4);
        assertNotEquals(c1, "(1, 2)"); //different types
    }
    @Test
    void test_string_constructor_valid_cases() {
        Coordinate c1 = new Coordinate("B3");
        assertEquals(1, c1.getRow());
        assertEquals(3, c1.getColumn());
        Coordinate c2 = new Coordinate("D5");
        assertEquals(3, c2.getRow());
        assertEquals(5, c2.getColumn());
        Coordinate c3 = new Coordinate("a9");
        assertEquals(0, c3.getRow());
        assertEquals(9, c3.getColumn());
        Coordinate c4 = new Coordinate("Z0");
        assertEquals(25, c4.getRow());
        assertEquals(0, c4.getColumn());

    }
    @Test
    public void test_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(27, 5));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(4,11));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(-1, 5));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate(4, -5));
    }
    @Test
    public void test_string_constructor_error_cases() {
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("00"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("AA"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("@0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("[0"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A/"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A:"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A"));
        assertThrows(IllegalArgumentException.class, () -> new Coordinate("A12"));
    }
    @Test
    public void testToString() {
        Coordinate c1 = new Coordinate(0, 1);
        assertEquals("(0, 1)", c1.toString());

        Coordinate c2 = new Coordinate(2, 3);
        assertEquals("(2, 3)", c2.toString());

        Coordinate c3 = new Coordinate("A5");
        assertEquals("(0, 5)", c3.toString());

        Coordinate c4 = new Coordinate("D9");
        assertEquals("(3, 9)", c4.toString());
    }

    @Test
    public void testHashCode() {
        Coordinate c1 = new Coordinate(2, 3);
        Coordinate c2 = new Coordinate(2, 3);
        Coordinate c3 = new Coordinate("C3");

        assertEquals(c1.hashCode(), c2.hashCode());
        assertEquals(c1.hashCode(), c3.hashCode());

        Coordinate c4 = new Coordinate(3, 4);
        assertNotEquals(c1.hashCode(), c4.hashCode());

        Coordinate c5 = new Coordinate("A1");
        Coordinate c6 = new Coordinate("B2");
        assertNotEquals(c5.hashCode(), c6.hashCode());


        Coordinate c7 = new Coordinate("b2");
        assertEquals(c6.hashCode(), c7.hashCode());
    }
}