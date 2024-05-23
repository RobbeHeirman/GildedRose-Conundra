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
        Item createNormalItem(int sellIn, int quality) {
            return new Item("Acolyte's Robe", sellIn, quality);

        }

        @Test
        void happyDay() {
            assertSellInQualityEqualAfterDays(
                createNormalItem(8, 12),
                1,
                5,
                7
            );

            assertSellInQualityEqualAfterDays(
                createNormalItem(1, 1),
                0,
                0,
                1
            );
        }

        @Test
        void expiredDegrade() {
            assertSellInQualityEqualAfterDays(
                createNormalItem(3, 20),
                -3,
                11,
                6
            );

            assertSellInQualityEqualAfterDays(
                createNormalItem(2, 1),
                0,
                0,
                2
            );
        }

        @Test
        void belowZeroQuality() {
            assertSellInQualityEqualAfterDays(
                createNormalItem(4, 2),
                1,
                0,
                3
            );
            assertSellInQualityEqualAfterDays(
                createNormalItem(3, 1),
                0,
                0,
                3
            );

            assertSellInQualityEqualAfterDays(
                createNormalItem(1, 2),
                -1,
                0,
                2
            );
        }
    }

    @Nested
    class AgedItemTest {
        Item createAgedItem(int sellIn, int quality) {
            return new Item("Aged Brie", sellIn, quality);
        }

        @Test
        void happyDay() {
            assertSellInQualityEqualAfterDays(
                createAgedItem(1, 1),
                0,
                2,
                1
            );
        }

        @Test
        void pastSellIn() {
            assertSellInQualityEqualAfterDays(
                createAgedItem(0, 1),
                -1,
                3,
                1
            );
        }

        @Test
        void notBiggerThenMax() {
            assertSellInQualityEqualAfterDays(
                createAgedItem(1, 50),
                0,
                50,
                1
            );

            assertSellInQualityEqualAfterDays(
                createAgedItem(2, 49),
                0,
                50,
                2
            );

            assertSellInQualityEqualAfterDays(
                createAgedItem(0, 49),
                -1,
                50,
                1
            );
        }
    }

    @Nested
    class LegendaryItemTest {
        Item createLegendaryItem(int sellIn, int quality) {
            return new Item("Sulfuras, Hand of Ragnaros", sellIn, quality);
        }

        @Test
        void happyDay() {
            assertSellInQualityEqualAfterDays(
                createLegendaryItem(2, 40),
                2,
                40,
                1
            );

            assertSellInQualityEqualAfterDays(
                createLegendaryItem(2, 50),
                2,
                50,
                2
            );

            assertSellInQualityEqualAfterDays(
                createLegendaryItem(2, 1),
                2,
                1,
                3
            );
        }
    }
}
