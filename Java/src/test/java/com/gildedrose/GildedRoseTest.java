package com.gildedrose;

import com.gildedrose.item_runnables.ItemRunnableFactory;
import com.gildedrose.item_update_strategy.StrategySelector;
import com.gildedrose.item_update_strategy.UpdateStrategy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
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

    @Test
    void customRunnableSelectorTest() {
        //Rats doing trick?: https://www.youtube.com/watch?v=AV9z0c1hjnA
        UpdateStrategy infestedRatStrategy = item -> {
            item.sellIn--;
            item.quality = 0;
        };
        UpdateStrategy legendaryItemFeedsOnRats = item -> item.quality++;
        ItemRunnableFactory selector = item -> {
            UpdateStrategy strat = item.name.equals(Constants.LEGENDARY_ITEM) ? legendaryItemFeedsOnRats : infestedRatStrategy;
            return () -> strat.update(item);
        };
        Map<String, Item> someItemsToTest = getSomeItemsToTest();
        GildedRose app = new GildedRose(someItemsToTest.values().toArray(new Item[0]), selector);

        app.updateQuality();
        checkItem(someItemsToTest.get(REGULAR_ITEM), 14, 0);
        checkItem(someItemsToTest.get(Constants.AGED_ITEM), 14, 0);
        checkItem(someItemsToTest.get(Constants.LEGENDARY_ITEM), 15, Constants.LEGENDARY_QUALITY + 1);
        checkItem(someItemsToTest.get(Constants.BACKSTAGE_ITEM), 14, 0);
    }

    @Test
    void directRunnable() {
        UpdateStrategy cheeseLovingStoreCleric = item -> {
            item.quality /= 2;
            item.sellIn--;
        };

        Map<String, Item> someItemsToTest = getSomeItemsToTest();
        List<Runnable> runnableList = someItemsToTest.values()
            .stream()
            .map(item -> (Runnable) () -> {
                UpdateStrategy strategy = item.name.equals(Constants.AGED_ITEM) ? cheeseLovingStoreCleric : StrategySelector.defaultStrategySelector(item);
                strategy.update(item);
            })
            .toList();

        GildedRose app = new GildedRose(runnableList);

        app.updateQuality();
        checkItem(someItemsToTest.get(Constants.AGED_ITEM), 14, 10);
        checkItem(someItemsToTest.get(REGULAR_ITEM), 14, 19);
    }

    @Nested
    class PreConditionTests {
        GildedRose createGildedRose(Item... items) {
            return new GildedRose(items);
        }

        @Test
        void testNegativeQuality() {
            // https://www.wowhead.com/classic/item=22691/corrupted-ashbringer
            Item corruptedAshbringer = new Item("Corrupted Ashbringer", 10, -25);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(corruptedAshbringer));

            Item shoes = new Item("Magical shoes that are always one size to small", 10, -1);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(shoes));

            assertThrows(IllegalArgumentException.class, () -> createGildedRose(corruptedAshbringer, shoes));

        }

        @Test
        void testToMuchQuality() {
            Item Ashbringer = new Item("Ashbringer", 10, 51);
            Item cookies = new Item("Home Made Cookies", 10, 999999999);
            assertThrows(IllegalArgumentException.class, () -> createGildedRose(Ashbringer, cookies));
        }
    }
}
