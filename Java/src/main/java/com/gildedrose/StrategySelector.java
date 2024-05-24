package com.gildedrose;

public interface StrategySelector {
    UpdateStrategy getUpdateStrategy(Item item);

    static UpdateStrategy defaultStrategySelector(Item item) {
        return switch (item.name) {
            case Constants.AGED_ITEM -> UpdateStrategy::updateDefaultAgedItem;
            case Constants.BACKSTAGE_ITEM -> UpdateStrategy::updateDefaultBackStageItem;
            case Constants.LEGENDARY_ITEM -> UpdateStrategy::updateDefaultLegendaryItem;
            default -> UpdateStrategy::updateDefaultRegularItem;
        };
    }
}
