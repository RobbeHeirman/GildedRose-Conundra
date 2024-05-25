package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                createLegendaryItem(2, Constants.LEGENDARY_QUALITY),
                2,
                Constants.LEGENDARY_QUALITY,
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

    @Nested
    class BackstagePassTest {
        Item createBackstagePasses(int sellIn, int quality) {
            return new Item(Constants.BACKSTAGE_ITEM, sellIn, quality);
        }

        @Test
        void happyDay() {
            assertSellInQualityEqualAfterDays(
                createBackstagePasses(12, 14),
                11,
                15,
                1
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(12, 14),
                10,
                16,
                2
            );
        }

        @Test
        void belowTen() {
            assertSellInQualityEqualAfterDays(
                createBackstagePasses(10, 34),
                9,
                36,
                1
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(11, 34),
                9,
                37,
                2
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(11, 34),
                5,
                45,
                6
            );
        }

        @Test
        void belowFive() {
            assertSellInQualityEqualAfterDays(
                createBackstagePasses(5, 12),
                4,
                15,
                1
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(6, 12),
                4,
                17,
                2
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(5, 12),
                0,
                27,
                5
            );
        }

        @Test
        void expiredPasses() {
            assertSellInQualityEqualAfterDays(
                createBackstagePasses(1, 40),
                -1,
                Constants.MIN_ITEM_QUALITY,
                2
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(0, 1),
                -2,
                Constants.MIN_ITEM_QUALITY,
                2
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(1, 49),
                -1,
                Constants.MIN_ITEM_QUALITY,
                2
            );
        }

        @Test
        void notAboveMax() {
            assertSellInQualityEqualAfterDays(
                createBackstagePasses(20, 50),
                19,
                Constants.MAX_ITEM_QUALITY,
                1
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(10, 49),
                8,
                Constants.MAX_ITEM_QUALITY,
                2
            );

            assertSellInQualityEqualAfterDays(
                createBackstagePasses(5, 49),
                2,
                Constants.MAX_ITEM_QUALITY,
                3
            );
        }
    }

    @Nested
    class PreConditionTests {
        GildedRose createGildedRose(Item... items) {
            return new GildedRose(items);
        }

        @Test
        @Disabled
        void testNegativeQuality() {
            // https://www.wowhead.com/classic/item=22691/corrupted-ashbringer
            Item corruptedAshbringer = new Item("Corrupted Ashbringer", 10, -25);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(corruptedAshbringer));

            Item shoes  = new Item("Magical shoes that are always one size to small", 10, -1);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(shoes));

            assertThrows(IllegalArgumentException.class, () -> createGildedRose(corruptedAshbringer, shoes));

        }

        @Test
        @Disabled
        void testToMuchQuality() {
            Item Ashbringer = new Item("Ashbringer", 10, 51);
            Item cookies = new Item("Home Made Cookies", 10, 999999999);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(Ashbringer, cookies));
        }
    }
}
