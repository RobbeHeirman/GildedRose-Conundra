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

    public void updateQuality() {
        for (Item item : items) {
            if (item.name.equals("Aged Brie")
                || item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1;

                        if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
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
                        }
                    }
                } else {
                if (item.quality > 0) {
                    if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                        item.quality = item.quality - 1;
                    }
                }
            }

            if (item.name.equals("Aged Brie")) {
                item.sellIn--;

                if (item.sellIn >= 0) {
                    return;
                }

                if (item.quality < 50) {
                    item.quality = item.quality + 1;
                }
            } else if (item.name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                item.sellIn--;

                if (item.sellIn >= 0) {
                    return;
                }
                item.quality = item.quality - item.quality;
            } else if (item.name.equals("Sulfuras, Hand of Ragnaros")) {

                if (item.sellIn >= 0) {
                    return;
                }

            } else {
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
