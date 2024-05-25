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

        void testNormalStrategy(Item item,int expectedSellIn, int expectedQuality, int runDays) {
            testStrategy(item, UpdateStrategy::updateDefaultAgedItem, expectedSellIn, expectedQuality, runDays);
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

}
