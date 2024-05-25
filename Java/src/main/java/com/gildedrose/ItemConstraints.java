package com.gildedrose;

/**
 * Class will contain functionality to enforce constraints on items.
 * The constraints can be used as a pre, post condition check on item operations
 */
public class ItemConstraints {
    public static void maxQualityConstraint(Item item, int quality) {
        if (item.quality > quality) {
            throw new IllegalArgumentException("Quality should be less then %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }

    public static void minQualityConstraint(Item item, int quality) {
        if (item.quality < quality) {
            throw new IllegalArgumentException("Quality should be more then %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }

    public static void exactQualityConstraint(Item item, int quality) {
        if (item.quality != quality) {
            throw new IllegalArgumentException("Quality should be exact %s. But is %s for %s"
                .formatted(quality, item.quality, item));
        }
    }

    public static void defaultItemConstraintsCheck(Item item) {
        minQualityConstraint(item, Constants.MIN_ITEM_QUALITY);
        maxQualityConstraint(item, Constants.MAX_ITEM_QUALITY);
    }

    public static void  legendaryItemConstraintsCheck(Item item) {
        exactQualityConstraint(item, Constants.LEGENDARY_QUALITY);
    }
}

