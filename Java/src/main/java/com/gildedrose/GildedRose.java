package com.gildedrose;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * GildedRose driver. This class contains the logic for the GildedRoseSpecifications.
 * It takes an array of {@link Item} where it will perform update operation on {@link Item#quality} and
 * {@link Item#sellIn} modifying the objects on each updateQuality call.
 */
public class GildedRose {
    final Item[] items;

    /**
     *
     * @param items Simple GildedRose items. Will be modified by GildedRose.
     */
    public GildedRose(Item[] items) {
        if (!testItemsAreValid(items)) {
            throw new IllegalArgumentException("The given items list contains invalid items");
        }
        this.items = items;
    }

    /**
     * Does the update logic of gilded rose. Modifies all Item objects passed in to the GildedRoseObjects
     * according to the GildedRoseSpecs.
     * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">Specs</a>
     */
    public void updateQuality() {
        Arrays.stream(items).forEach(item -> UpdateStrategy.strategySelector(item).update(item));
    }

    /**
     * calls updateQuality for n days
     * @param days is the amount of days we wantto call updateQuality for.
     */
    public void updateQualityForDays(int days) {
        IntStream.range(0, days).forEach(i -> updateQuality());
    }

    protected boolean testItemsAreValid(Item[] items) {
        return Arrays.stream(items)
            .noneMatch(item -> item.quality > Constants.MAX_ITEM_QUALITY || item.quality < Constants.MIN_ITEM_QUALITY);
    }
}
