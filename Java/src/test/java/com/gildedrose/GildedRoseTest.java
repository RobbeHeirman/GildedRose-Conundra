package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GildedRoseTest {
    static final String REGULAR_ITEM = "Carrot on a stick";
    Map<String, Item> getSomeItemsToTest() {
        Item[] items = {
                new Item(REGULAR_ITEM, 15, 20),
                new Item(Constants.AGED_ITEM, 15, 20),
                new Item(Constants.LEGENDARY_ITEM, 15, Constants.LEGENDARY_QUALITY),
                new Item(Constants.BACKSTAGE_ITEM, 15, 20),
        };

        return Arrays.stream(items)
            .collect(Collectors.toMap(item -> item.name, Function.identity()));
    }

    GildedRose createApp(Map<String, Item> someItemsToTest) {
        Item[] itemArray = someItemsToTest.values().toArray(new Item[0]);
        return new GildedRose(itemArray);
    }

    void checkItem(Item item, int expectedSellIn, int expectedQuality) {
        assertEquals(expectedSellIn, item.sellIn);
        assertEquals(expectedQuality, item.quality);
    }

    @Test
    void defaultStrategiesApplied() {
        Map<String, Item> testItems = getSomeItemsToTest();
        GildedRose app = createApp(testItems);

        app.updateQuality();
        checkItem(testItems.get(REGULAR_ITEM), 14, 19);
        checkItem(testItems.get(Constants.AGED_ITEM), 14, 21);
        checkItem(testItems.get(Constants.LEGENDARY_ITEM), 15, Constants.LEGENDARY_QUALITY);
        checkItem(testItems.get(Constants.BACKSTAGE_ITEM), 14, 21);

        app.updateQualityForDays(5);
        checkItem(testItems.get(Constants.BACKSTAGE_ITEM), 9, 27);

        app.updateQualityForDays(5);
        checkItem(testItems.get(Constants.BACKSTAGE_ITEM), 4, 38);

        app.updateQualityForDays(5);
        checkItem(testItems.get(REGULAR_ITEM), -1, 3);
        checkItem(testItems.get(Constants.AGED_ITEM), -1, 37);
        checkItem(testItems.get(Constants.LEGENDARY_ITEM), 15, Constants.LEGENDARY_QUALITY);
        checkItem(testItems.get(Constants.BACKSTAGE_ITEM), -1, 0);

        app.updateQualityForDays(100);
        checkItem(testItems.get(REGULAR_ITEM), -101, Constants.MIN_ITEM_QUALITY);
        checkItem(testItems.get(Constants.AGED_ITEM), -101, Constants.MAX_ITEM_QUALITY);
        checkItem(testItems.get(Constants.LEGENDARY_ITEM), 15, Constants.LEGENDARY_QUALITY);
        checkItem(testItems.get(Constants.BACKSTAGE_ITEM), -101, Constants.MIN_ITEM_QUALITY);

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

            Item shoes = new Item("Magical shoes that are always one size to small", 10, -1);
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
