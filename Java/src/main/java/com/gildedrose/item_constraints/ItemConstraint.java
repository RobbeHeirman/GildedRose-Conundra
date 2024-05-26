package com.gildedrose.item_constraints;

import com.gildedrose.Constants;
import com.gildedrose.Item;

/**
 * Class will contain functionality to enforce constraints on items.
 * The constraints can be used as a pre, post condition check on item operations
 */
public interface ItemConstraint {
    void checkConstraint(Item item);

    /**
     * Method will return the correct {@link ItemConstraint} based on what item is passed.
     * Will {@link ItemConstraint#legendaryItemConstraintsCheck} for a legendary item and
     * {@link ItemConstraint#defaultItemConstraintsCheck} otherwise.
     */
    static ItemConstraint getItemConstraints(Item item) {
        return item.name.equals(Constants.LEGENDARY_ITEM) ? ItemConstraint::legendaryItemConstraintsCheck : ItemConstraint::defaultItemConstraintsCheck;
    }

    /**
     * Checks the constraints on a default item.
     * Will trhow aan {@link IllegalArgumentException} if item is less then {@link Constants#MIN_ITEM_QUALITY}
     * Or more then {@link Constants#MAX_ITEM_QUALITY}
     */
    static void defaultItemConstraintsCheck(Item item) {
        minQualityConstraint(item, Constants.MIN_ITEM_QUALITY);
        maxQualityConstraint(item, Constants.MAX_ITEM_QUALITY);
    }

    /**
     * Constraint check for a legendary item.
     * Will check if the quality of an item is equal to {@link Constants#LEGENDARY_QUALITY} and throws
     * an {@link IllegalArgumentException if not}
     */
    static void legendaryItemConstraintsCheck(Item item) {
        exactQualityConstraint(item, Constants.LEGENDARY_QUALITY);
    }

    static void maxQualityConstraint(Item item, int quality) {
        if (item.quality > quality) {
            throw new IllegalArgumentException("Quality should be less then %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }

    static void minQualityConstraint(Item item, int quality) {
        if (item.quality < quality) {
            throw new IllegalArgumentException("Quality should be more then %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }

    static void exactQualityConstraint(Item item, int quality) {
        if (item.quality != quality) {
            throw new IllegalArgumentException("Quality should be exact %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }
}

