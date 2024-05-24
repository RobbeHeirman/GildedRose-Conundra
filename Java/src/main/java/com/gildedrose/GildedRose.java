package com.gildedrose;

import java.util.stream.IntStream;



class GildedRose {
    Item[] items;

    private static final int MAX_ITEM_QUALITY = 50;

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
            item.quality = 0;
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
        item.quality = Math.max(0, item.quality - subtractQuality);
    }

    public void updateQuality() {
        for (Item item : items) {
            // Item If statement part
            switch (item.name) {
                case "Aged Brie" -> updateAgedItem(item);
                case "Backstage passes to a TAFKAL80ETC concert" -> updateBackStageItem(item);
                case "Sulfuras, Hand of Ragnaros" -> updateLegendaryItem(item);
                default -> updateRegularItem(item);
            }
        }

    }
}
