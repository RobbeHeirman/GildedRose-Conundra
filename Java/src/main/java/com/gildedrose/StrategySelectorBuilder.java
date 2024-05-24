package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

public class StrategySelectorBuilder<T> {
    private T defaultUpdateStrategy;
    private final Map<String, T> mappedStrategies = new HashMap<>();
    StrategySelectorBuilder() {
    }
    public StrategySelectorBuilder<T> setDefault(T strategy) {
        defaultUpdateStrategy = strategy;
        return this;
    }

    public StrategySelectorBuilder<T> setStrategy(String name, T strategy) {
        mappedStrategies.put(name, strategy);
        return this;
    }

    public StrategySelector<T> build() {
        return (item -> mappedStrategies.getOrDefault(item.name, defaultUpdateStrategy));
    }
}
