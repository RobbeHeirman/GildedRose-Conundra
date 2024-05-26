package com.gildedrose.item_runnables;

import com.gildedrose.Item;
import com.gildedrose.item_constraints.ItemConstraint;
import com.gildedrose.item_update_strategy.StrategySelector;
import com.gildedrose.item_update_strategy.UpdateStrategy;

/**
 * Interface for ItemRunnableFactories. A factory will take an {@link Item} and return a {@link Runnable}.
 */
public interface ItemRunnableFactory {
    Runnable getRunnable(Item item);

    /**
     * Will return a runnable for the item corresponding to the gilded rose specs.
     * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">Specs</a>
     */
    static Runnable defaultRunnableFactory(Item item) {
        ItemConstraint constraint = ItemConstraint.getItemConstraints(item);
        UpdateStrategy strategy = StrategySelector.defaultStrategySelector(item);
        return new RunnableItem(item, strategy, constraint);
    }
}
