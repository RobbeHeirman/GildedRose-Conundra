package com.gildedrose;

import java.util.stream.IntStream;

/**
 * GildedRose driver. This class contains the logic for the GildedRoseSpecifications.
 * It takes an array of {@link Item} where it will perform update operation on {@link Item#quality} and
 * {@link Item#sellIn} modifying the objects on each updateQuality call.
 */
class GildedRose {

    private static final int MIN_ITEM_QUALITY = 0;
    private static final int MAX_ITEM_QUALITY = 50;

    private static final String AGED_ITEM = "Aged Brie";
    private static final String LEGENDARY_ITEM = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_ITEM = "Backstage passes to a TAFKAL80EE";

    final Item[] items;

    /**
     *
     * @param items Simple GildedRose items. Will be modified by GildedRose.
     */
    public GildedRose(Item[] items) {
        this.items = items;
    }

    /**
     * Does the update logic of gilded rose. Modifies all Item objects passed in to the GildedRoseObjects
     * according to the GildedRoseSpecs.
     * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">Specs</a>
     */
    public void updateQuality() {
        for (Item item : items) {
            switch (item.name) {
                case AGED_ITEM -> updateAgedItem(item);
                case BACKSTAGE_ITEM -> updateBackStageItem(item);
                case LEGENDARY_ITEM -> updateLegendaryItem(item);
                default -> updateRegularItem(item);
            }
        }
    }

    /**
     * calls updateQuality for n days
     * @param days is the amount of days we wantto call updateQuality for.
     */
    public void updateQualityForDays(int days) {
        IntStream.range(0, days).forEach(i -> updateQuality());
    }

    private static void updateAgedItem(final Item item) {
        item.sellIn--;
        final int addToQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.min(MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    private static void updateBackStageItem(final Item item) {
        item.sellIn--;

        if (item.sellIn < 0) {
            item.quality = MIN_ITEM_QUALITY;
            return;
        }

        final int addToQuality = item.sellIn < 5 ? 3 : item.sellIn < 10 ? 2 : 1;
        item.quality = Math.min(MAX_ITEM_QUALITY, item.quality + addToQuality);
    }

    private static void updateLegendaryItem(final Item item) {
        // Dummy method is a spoiler we are going to use a strategy pattern in future steps
    }

    private static void updateRegularItem(final Item item) {
        item.sellIn--;

        final int subtractQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(MIN_ITEM_QUALITY, item.quality - subtractQuality);
    }
}
