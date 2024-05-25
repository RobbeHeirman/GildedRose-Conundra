package com.gildedrose.item_update_strategy;

import java.util.HashMap;
import java.util.Map;

public class StrategySelectorBuilder {
    private UpdateStrategy defaultUpdateStrategy;
    private final Map<String, UpdateStrategy> mappedStrategies = new HashMap<>();
    StrategySelectorBuilder() {
        defaultUpdateStrategy = (item) -> {};
    }
    public StrategySelectorBuilder setDefault(UpdateStrategy strategy) {
        defaultUpdateStrategy = strategy;
        return this;
    }

    public StrategySelectorBuilder setStrategy(String name, UpdateStrategy strategy) {
        mappedStrategies.put(name, strategy);
        return this;
    }

    public StrategySelector build() {
        return (item -> mappedStrategies.getOrDefault(item.name, defaultUpdateStrategy));
    }
}
