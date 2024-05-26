package com.gildedrose;

import com.gildedrose.item_constraints.InvariantNameConstraint;
import com.gildedrose.item_constraints.ItemConstraint;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConstraintsTest {

    @Test
    public void minimumConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.minQualityConstraint(new Item("money", 15, 1), 2)
        );
    }

    @Test
    void maximumConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.maxQualityConstraint(new Item("bills", 15, 2), 1)
        );
    }

    @Test
    void exactConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.exactQualityConstraint(new Item("Cat", 2, 1000), 9999)
        );
    }

    @Test
    void normalItemConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.defaultItemConstraintsCheck(new Item("Blackened Defias Armor", 10, 51))
        );

        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.defaultItemConstraintsCheck(new Item("Cruel Barb", 10, -1))
        );
    }

    @Test
    void legendaryConstraintTest() {
        assertThrows(
            IllegalArgumentException.class,
            () -> ItemConstraint.legendaryItemConstraintsCheck(new Item(Constants.LEGENDARY_ITEM, 15, 79))
        );
    }

    @Test
    void nameInvariantConstraintTest() {
        assertThrows(
            IllegalStateException.class,
            () -> {
                InvariantNameConstraint constraint = new InvariantNameConstraint("Robbe");
                constraint.checkConstraint(new Item("Ayla", 4, 5));

            }
        );
    }
}
