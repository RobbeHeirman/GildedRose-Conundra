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

    public void updateQuality() {
        for (Item item : items) {
            // Item If statement part
            if (item.name.equals("Aged Brie")) {
                updateAgedItem(item);
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                updateBackStageItem(item);
            } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
                updateLegendaryItem(item);
            } else {
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
                item.sellIn--;

                if (item.sellIn >= 0) {
                    return;
                }
                if (item.quality > 0) {
                    item.quality = item.quality - 1;
                }
            }
        }

    }
}
