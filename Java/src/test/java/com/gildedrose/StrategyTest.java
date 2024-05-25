package com.gildedrose;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyTest {
    void testStrategy(Item item, UpdateStrategy strategy, int expectedSellIn, int expectedQuality, int runDays) {
        IntStream.range(0, runDays).forEach(num -> strategy.update(item));
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }

    @Nested
    class DefaultNormalStrategyTest {
        Item createNormalItem(int sellIn, int quality) {
            return new Item("Acolyte's Robe", sellIn, quality);

        }

        void testNormalStrategy(Item item, int expectedSellIn, int expectedQuality, int runDays) {
            testStrategy(item, UpdateStrategy::updateDefaultRegularItem, expectedSellIn, expectedQuality, runDays);
        }

        @Test
        void happyDay() {
            testNormalStrategy(
                createNormalItem(8, 12),
                1,
                5,
                7
            );

            testNormalStrategy(
                createNormalItem(1, 1),
                0,
                0,
                1
            );
        }

        @Test
        void expiredDegrade() {
            testNormalStrategy(
                createNormalItem(3, 20),
                -3,
                11,
                6
            );

            testNormalStrategy(
                createNormalItem(2, 1),
                0,
                0,
                2
            );
        }

        @Test
        void belowZeroQuality() {
            testNormalStrategy(
                createNormalItem(4, 2),
                1,
                0,
                3
            );
            testNormalStrategy(
                createNormalItem(3, 1),
                0,
                0,
                3
            );

            testNormalStrategy(
                createNormalItem(1, 2),
                -1,
                0,
                2
            );
        }
    }

    @Nested
    class LegendaryItemTest {
        Item createLegendaryItem(int sellIn, int quality) {
            return new Item(Constants.LEGENDARY_ITEM, sellIn, quality);
        }

        void testLegendaryStrategy(Item item, int expectedSellIn, int expectedQuality, int runDays) {
            testStrategy(item, UpdateStrategy::updateDefaultLegendaryItem, expectedSellIn, expectedQuality, runDays);
        }

        @Test
        void happyDay() {
            testLegendaryStrategy(
                createLegendaryItem(2, 40),
                2,
                40,
                1
            );

            testLegendaryStrategy(
                createLegendaryItem(2, Constants.LEGENDARY_QUALITY),
                2,
                Constants.LEGENDARY_QUALITY,
                2
            );

            testLegendaryStrategy(
                createLegendaryItem(2, 1),
                2,
                1,
                3
            );
        }

        @Nested
        class BackstagePassTest {
            Item createBackstagePasses(int sellIn, int quality) {
                return new Item(Constants.BACKSTAGE_ITEM, sellIn, quality);
            }

            void testBackstagePassStrategy(Item item, int expectedSellIn, int expectedQuality, int runDays) {
                testStrategy(item, UpdateStrategy::updateDefaultBackStageItem, expectedSellIn, expectedQuality, runDays);
            }

            @Test
            void happyDay() {
                testBackstagePassStrategy(
                    createBackstagePasses(12, 14),
                    11,
                    15,
                    1
                );

                testBackstagePassStrategy(
                    createBackstagePasses(12, 14),
                    10,
                    16,
                    2
                );
            }

            @Test
            void belowTen() {
                testBackstagePassStrategy(
                    createBackstagePasses(10, 34),
                    9,
                    36,
                    1
                );

                testBackstagePassStrategy(
                    createBackstagePasses(11, 34),
                    9,
                    37,
                    2
                );

                testBackstagePassStrategy(
                    createBackstagePasses(11, 34),
                    5,
                    45,
                    6
                );
            }

            @Test
            void belowFive() {
                testBackstagePassStrategy(
                    createBackstagePasses(5, 12),
                    4,
                    15,
                    1
                );

                testBackstagePassStrategy(
                    createBackstagePasses(6, 12),
                    4,
                    17,
                    2
                );

                testBackstagePassStrategy(
                    createBackstagePasses(5, 12),
                    0,
                    27,
                    5
                );
            }

            @Test
            void expiredPasses() {
                testBackstagePassStrategy(
                    createBackstagePasses(1, 40),
                    -1,
                    Constants.MIN_ITEM_QUALITY,
                    2
                );

                testBackstagePassStrategy(
                    createBackstagePasses(0, 1),
                    -2,
                    Constants.MIN_ITEM_QUALITY,
                    2
                );

                testBackstagePassStrategy(
                    createBackstagePasses(1, 49),
                    -1,
                    Constants.MIN_ITEM_QUALITY,
                    2
                );
            }

            @Test
            void notAboveMax() {
                testBackstagePassStrategy(
                    createBackstagePasses(20, 50),
                    19,
                    Constants.MAX_ITEM_QUALITY,
                    1
                );

                testBackstagePassStrategy(
                    createBackstagePasses(10, 49),
                    8,
                    50,
                    2
                );

                testBackstagePassStrategy(
                    createBackstagePasses(5, 49),
                    2,
                    Constants.MAX_ITEM_QUALITY,
                    3
                );
            }


        }
    }

}
