package com.gildedrose.item_update_strategy;

import com.gildedrose.Constants;
import com.gildedrose.Item;

public interface UpdateStrategy {
    void update(Item item);

    static void updateDefaultAgedItem(final Item item) {
        item.sellIn--;

        final int addToQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    static void updateDefaultBackStageItem(final Item item) {
        item.sellIn--;

        if (item.sellIn < 0) {
            item.quality = Constants.MIN_ITEM_QUALITY;
            return;
        }

        final int addToQuality = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    static void updateDefaultLegendaryItem(final Item item) {
        // Dummy method is a spoiler we are going to use a strategy pattern in future steps
    }

    static void updateDefaultRegularItem(final Item item) {
        item.sellIn--;

        final int subtractQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(Constants.MIN_ITEM_QUALITY, item.quality - subtractQuality);
    }
}
