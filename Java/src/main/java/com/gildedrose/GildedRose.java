package com.gildedrose;

import com.gildedrose.item_runnables.ItemRunnableFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * GildedRose driver. This class contains the logic for the GildedRoseSpecifications.
 * It takes an array of {@link Item} where it will perform update operation on {@link Item#quality} and
 * {@link Item#sellIn} modifying the objects on each updateQuality call.
 */
public class GildedRose {
    Item[] items; // TODO: Got to fight the goblin before we can remove this unused property
    private final Iterable<Runnable> runnableItems;

    /**
     * @param items Simple GildedRose items. Will be modified by GildedRose.
     */
    public GildedRose(Item[] items) {
        this(items, ItemRunnableFactory::defaultRunnableFactory);
    }

    public GildedRose(Item[] items, ItemRunnableFactory factory) {
        this(Arrays.stream(items)
            .map(factory::getRunnable)
            .toList());
    }

    public GildedRose(Iterable<Runnable> runnables) {
        runnableItems = runnables;
    }

    /**
     * Does the update logic of gilded rose. Modifies all Item objects passed in to the GildedRoseObjects
     * according to the GildedRoseSpecs.
     *
     * @see <a href="https://github.com/emilybache/GildedRose-Refactoring-Kata/blob/main/GildedRoseRequirements.md">Specs</a>
     */
    public void updateQuality() {
        runnableItems.forEach(Runnable::run);
    }

    /**
     * calls updateQuality for n days
     *
     * @param days is the amount of days we want to call updateQuality for.
     */
    public void updateQualityForDays(int days) {
        IntStream.range(0, days).forEach(i -> updateQuality());
    }
}
