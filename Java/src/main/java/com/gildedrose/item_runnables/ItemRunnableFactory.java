package com.gildedrose.item_runnables;

import com.gildedrose.Item;
import com.gildedrose.item_constraints.ItemConstraints;
import com.gildedrose.item_update_strategy.StrategySelector;
import com.gildedrose.item_update_strategy.UpdateStrategy;

public interface ItemRunnableFactory {
    Runnable getRunnable(Item item);

    static Runnable defaultRunnableFactory(Item item) {
        ItemConstraints.defaultItemConstraintsCheck(item);
        UpdateStrategy strategy = StrategySelector.defaultStrategySelector(item);
        return () -> strategy.update(item);
    }
}
