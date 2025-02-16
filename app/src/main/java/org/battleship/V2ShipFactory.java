package org.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character> {
    protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) {
        if (where.getOrientation() != 'V' && where.getOrientation() != 'H') {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
        }
        return (where.getOrientation() == 'V')
                ? new RectangleShip<>(name, where.getWhere(), w, h, letter, '*')
                : new RectangleShip<>(name, where.getWhere(), h, w, letter, '*');
    }

    protected Ship<Character> createNonRectangleShip(Placement where, char letter, String name, String type) {
        if (where.getOrientation() != 'L' && where.getOrientation() != 'R'
                && where.getOrientation() != 'D' && where.getOrientation() != 'U') {
            throw new IllegalArgumentException("That placement is invalid: it does not have the correct format.");
        }
        return new NonRectangleShip<>(type, name, where.getWhere(), where.getOrientation(), letter, '*');
    }

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createRectangleShip(where, 1,2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createRectangleShip(where, 1,3, 'd', "Destroyer");
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        return createNonRectangleShip(where, 'b', "Battleship", "battleship");
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        return createNonRectangleShip(where, 'c', "Carrier", "carrier");
    }

}
