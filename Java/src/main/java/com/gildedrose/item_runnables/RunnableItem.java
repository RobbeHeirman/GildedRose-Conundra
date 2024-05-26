package com.gildedrose.item_runnables;

import com.gildedrose.Item;
import com.gildedrose.item_constraints.ItemConstraint;
import com.gildedrose.item_update_strategy.UpdateStrategy;

import java.util.Arrays;

/**
 * Wrapper class for creating a runnable item. This can be done with functions but if we need to have some
 * sort of state for RunnableItems later a class could be more convenient.
 * Will take an item, a strategy and some constraints on the item. And apply the strategy to the item if
 * the run method is called.
 */
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
