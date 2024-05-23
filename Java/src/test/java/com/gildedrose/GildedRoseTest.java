package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    void assertSellInQualityEqualAfterDays(Item item, int sellIn, int quality, int days) {
        GildedRose app = new GildedRose(new Item[]{item});
        app.updateQualityForDays(days);
        assertEquals(sellIn, item.sellIn);
        assertEquals(quality, item.quality);
    }


    @Nested
    class NormalItemTest {
        Item normalItemFactory(int sellIn, int quality) {
            return new Item("Acolyte's Robe", sellIn, quality);

        }

        @Test
        void happyDay() {
            assertSellInQualityEqualAfterDays(
                normalItemFactory(8, 12),
                1,
                5,
                7
            );

            assertSellInQualityEqualAfterDays(
                normalItemFactory(1, 1),
                0,
                0,
                1
            );
        }

        @Test
        void expiredDegrade() {
            assertSellInQualityEqualAfterDays(
                normalItemFactory(3, 20),
                -3,
                11,
                6
            );

            assertSellInQualityEqualAfterDays(
                normalItemFactory(2, 1),
                0,
                0,
                2
            );
        }

        @Test
        void belowZeroQuality() {
            assertSellInQualityEqualAfterDays(
                normalItemFactory(4, 2),
                1,
                0,
                3
            );
            assertSellInQualityEqualAfterDays(
                normalItemFactory(3, 1),
                0,
                0,
                3
            );

            assertSellInQualityEqualAfterDays(
                normalItemFactory(1, 2),
                -1,
                0,
                2
            );
        }
    }

}
