package com.gildedrose;

public interface UpdateStrategy {
    void update(Item item);

    static UpdateStrategy strategySelector(Item item) {
        return switch (item.name) {
            case Constants.AGED_ITEM -> UpdateStrategy::updateAgedItem;
            case Constants.BACKSTAGE_ITEM -> UpdateStrategy::updateBackStageItem;
            case Constants.LEGENDARY_ITEM -> UpdateStrategy::updateLegendaryItem;
            default -> UpdateStrategy::updateRegularItem;
        };
    }

    private static void updateAgedItem(final Item item) {
        item.sellIn--;

        final int addToQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    private static void updateBackStageItem(final Item item) {
        item.sellIn--;

        if (item.sellIn < 0) {
            item.quality = Constants.MIN_ITEM_QUALITY;
            return;
        }

        final int addToQuality = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    private static void updateLegendaryItem(final Item item) {
        // Dummy method is a spoiler we are going to use a strategy pattern in future steps
    }

    private static void updateRegularItem(final Item item) {
        item.sellIn--;

        final int subtractQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(Constants.MIN_ITEM_QUALITY, item.quality - subtractQuality);
    }
}
