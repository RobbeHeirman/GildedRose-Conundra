package com.gildedrose.item_constraints;

import com.gildedrose.Item;

public class InvariantNameConstraint implements ItemConstraint {
    private final String initialName;

    public InvariantNameConstraint(String initialName) {
        this.initialName = initialName;
    }

    @Override
    public void checkConstraint(Item item) {
        if (!item.name.equals(initialName)) {
            throw new IllegalStateException("The name of the item changed from %s to %s".formatted(initialName, item.name));
        }
    }
}
