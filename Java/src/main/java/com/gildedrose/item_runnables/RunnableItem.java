package com.gildedrose.item_runnables;

import com.gildedrose.Item;
import com.gildedrose.item_constraints.ItemConstraint;
import com.gildedrose.item_update_strategy.UpdateStrategy;

import java.util.Arrays;

public class RunnableItem implements Runnable {
    private final Item item;
    private final UpdateStrategy strategy;

    public RunnableItem(Item item, UpdateStrategy strategy, ItemConstraint... constraints) {
        Arrays.stream(constraints).forEach(constraint -> constraint.checkConstraint(item));

        this.item = item;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        strategy.update(item);
    }
}
