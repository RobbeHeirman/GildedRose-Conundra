package com.gildedrose.item_update_strategy;

import com.gildedrose.Constants;
import com.gildedrose.Item;

/**
 * Interface for StrategySelectors. A strategySelector will select an update strategy based on
 * an {@link Item} and it's properties.
 */
public interface StrategySelector {
    UpdateStrategy getUpdateStrategy(Item item);

    /**
     * De default strategy  selector is the selector following the Gilded Rose specs.
     * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">Specs</a>
     */
    static UpdateStrategy defaultStrategySelector(Item item) {
        return switch (item.name) {
            case Constants.AGED_ITEM -> UpdateStrategy::updateDefaultAgedItem;
            case Constants.BACKSTAGE_ITEM -> UpdateStrategy::updateDefaultBackStageItem;
            case Constants.LEGENDARY_ITEM -> UpdateStrategy::updateDefaultLegendaryItem;
            case Constants.CONJURED_ITEM -> UpdateStrategy::updateDefaultConjuredItem;
            default -> UpdateStrategy::updateDefaultRegularItem;
        };
    }
}
