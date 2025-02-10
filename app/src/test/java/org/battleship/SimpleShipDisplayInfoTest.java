package org.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleShipDisplayInfoTest {
    @Test
    void testGetInfo() {
        SimpleShipDisplayInfo<Character> info = new SimpleShipDisplayInfo<>('s', '*');

        assertEquals('s', info.getInfo(null, false)); // Not hit, should return 's'
        assertEquals('*', info.getInfo(null, true));  // Hit, should return '*'
    }
}