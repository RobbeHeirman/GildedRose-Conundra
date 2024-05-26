package com.gildedrose.item_update_strategy;

import com.gildedrose.Constants;
import com.gildedrose.Item;

/**
 * Interface for UpdateStrategies. And UpdateStrategy will take an item and probably to some operations
 * on it. It may change the state of an Item.
 */
public interface UpdateStrategy {
    void update(Item item);

    /**
     * Default strategy for an AgedItem based on the gilded Rose specs.
     * Will decrement items.sellIn by one
     * Will increment the items quality with 1 if sellIn is bigger than 0 and with 2 if sellIn is 0 or smaller
     */
    static void updateDefaultAgedItem(final Item item) {
        item.sellIn--;

        final int addToQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }
    /**
     * Default strategy for a Backstage pass item based on the gilded Rose specs.
     * Will decrement items.sellIn by one
     * "Backstage passes" increases in Quality as its SellIn value approaches;
     * Quality increases by 2 when there are 10 days or less and by 3 when there are 5 days or less but
     * Quality drops to 0 after the sellInDate
     */
    static void updateDefaultBackStageItem(final Item item) {
        item.sellIn--;

        if (item.sellIn < 0) {
            item.quality = Constants.MIN_ITEM_QUALITY;
            return;
        }

        final int addToQuality = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(Constants.MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    /**
     * Default strategy for legendary items
     * Legendary items never change.
     */
    static void updateDefaultLegendaryItem(final Item item) {
    }

    /**
     * Default strategy for regular items.
     * Items sellIn and Quality decrement by one after each update.
     * If the sellIn is 0 or lowe Quality will drop by 2.
     */
    static void updateDefaultRegularItem(final Item item) {
        defaultItemDegeneration(item, 1);
    }

    /**
     * Conjured items is the same as updateDefaultRegularItem. But those items degrade twice as fast
     */
    static void updateDefaultConjuredItem(final Item item) {
        defaultItemDegeneration(item, 2);
    }

    private static void defaultItemDegeneration(final Item item, final float degenerationRate) {
        item.sellIn--;

        final int subtractQuality = Math.round((item.sellIn < 0 ? 2 : 1) * degenerationRate);
        item.quality = Math.max(Constants.MIN_ITEM_QUALITY, item.quality - subtractQuality);
    }
}
