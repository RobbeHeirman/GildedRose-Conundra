package com.gildedrose;

import java.util.stream.IntStream;



class GildedRose {
    private static final int MIN_ITEM_QUALITY = 0;
    private static final int MAX_ITEM_QUALITY = 50;

    private static final String AGED_ITEM = "Aged Brie";
    private static final String LEGENDARY_ITEM = "Sulfuras, Hand of Ragnaros";
    private static final String BACKSTAGE_ITEM = "Backstage passes to a TAFKAL80EE";

    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

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
    }

    private static void updateRegularItem(final Item item) {
        item.sellIn--;

        final int subtractQuality = item.sellIn < 0 ? 2 : 1;
        item.quality = Math.max(MIN_ITEM_QUALITY, item.quality - subtractQuality);
    }

    public void updateQuality() {
        for (Item item : items) {
            // Item If statement part
            switch (item.name) {
                case AGED_ITEM -> updateAgedItem(item);
                case BACKSTAGE_ITEM -> updateBackStageItem(item);
                case LEGENDARY_ITEM-> updateLegendaryItem(item);
                default -> updateRegularItem(item);
            }
        }

    }
}
