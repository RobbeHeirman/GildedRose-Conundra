package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConstraintsTest {

    @Test
    public void minimumConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.minQualityConstraint(new Item("money", 15, 1), 2)
        );
    }

    @Test
    void maximumConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.maxQualityConstraint(new Item("bills", 15, 2), 1)
        );
    }

    @Test
    void exactConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.exactQualityConstraint(new Item("Cat", 2, 1000), 9999)
        );
    }

    @Test
    void normalItemConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.defaultItemConstraintsCheck(new Item("Blackened Defias Armor", 10, 51))
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.defaultItemConstraintsCheck(new Item("Cruel Barb", 10, -1))
        );
    }

    @Test
    void legendaryConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraints.legendaryItemConstraintsCheck(new Item(Constants.LEGENDARY_ITEM, 15, 79))
        );
    }
}
