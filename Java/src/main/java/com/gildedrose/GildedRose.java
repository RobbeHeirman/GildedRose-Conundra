package com.gildedrose;

import java.util.stream.IntStream;

class GildedRose {
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
        item.quality = Math.min(50, item.quality + addToQuality);
    }

    private static void updateBackStageItem(final Item item) {
        if (item.quality < 50) {
            item.quality = item.quality + 1;
        }
        if (item.sellIn < 11) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }

        if (item.sellIn < 6) {
            if (item.quality < 50) {
                item.quality = item.quality + 1;
            }
        }
        item.sellIn--;

        if (item.sellIn >= 0) {
            return;
        }
        item.quality = item.quality - item.quality;
    }

    public void updateQuality() {
        for (Item item : items) {
            // Item If statement part
            if (item.name.equals("Aged Brie")) {
                updateAgedItem(item);
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                updateBackStageItem(item);
            } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {

                if (item.sellIn >= 0) {
                    return;
                }

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
