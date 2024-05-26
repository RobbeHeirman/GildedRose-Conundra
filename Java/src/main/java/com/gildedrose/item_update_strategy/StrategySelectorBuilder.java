package com.gildedrose.item_update_strategy;

import java.util.HashMap;
import java.util.Map;

/**
 * Example builder class to build a StrategySelector. Mainly here to showcase that we can pick and choose
 * our strategies of item updating at runtime.
 * This builder will return a StrategySelector that will match given Item Names and return the corresponding
 * Strategy. If no match is found it will apply the default strategy.
 * The default strategy chosen is a do nothing strategy.
 */
public class StrategySelectorBuilder {
    private UpdateStrategy defaultUpdateStrategy;
    private final Map<String, UpdateStrategy> mappedStrategies = new HashMap<>();
    public StrategySelectorBuilder() {
        defaultUpdateStrategy = item -> {};
    }

    /**
     * Sets a new default strategy. This will be applied when no Matching Name is found
     */
    public StrategySelectorBuilder setDefault(UpdateStrategy strategy) {
        defaultUpdateStrategy = strategy;
        return this;
    }

    /**
     * adds a strategy to given name. the build {@link StrategySelector} will return this strategy
     * if {@link com.gildedrose.Item#name} matches set name
     */
    public StrategySelectorBuilder setStrategy(String name, UpdateStrategy strategy) {
        mappedStrategies.put(name, strategy);
        return this;
    }

    /**
     * Builds the {@link StrategySelector} with previous set strategies.
     */
    public StrategySelector build() {
        return item -> mappedStrategies.getOrDefault(item.name, defaultUpdateStrategy);
    }
}
