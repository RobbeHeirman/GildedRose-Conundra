package com.gildedrose;

public interface ItemRunnableFactory {
    Runnable getRunnable(Item item);

    static Runnable defaultRunnableFactory(Item item) {
        ItemConstraints.defaultItemConstraintsCheck(item);
        UpdateStrategy strategy = StrategySelector.defaultStrategySelector(item);
        return () -> strategy.update(item);
    }
}
