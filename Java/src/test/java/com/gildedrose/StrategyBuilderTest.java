package com.gildedrose;

import com.gildedrose.item_update_strategy.StrategySelector;
import com.gildedrose.item_update_strategy.StrategySelectorBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrategyBuilderTest {
    @Test
    void happyDay() {
        StrategySelector selector = new StrategySelectorBuilder().setStrategy("Robbe", (item -> item.quality += 1000))
            .build();

        Item item = new Item("Robbe", 10, 10);
        selector.getUpdateStrategy(item).update(item);
        assertEquals(1010, item.quality);
    }

    @Test
    void testDefault() {
        StrategySelector selector = new StrategySelectorBuilder()
            .setDefault(item -> item.quality *= 2)
            .build();

        Item item = new Item("Robbe", 10, 10);
        selector.getUpdateStrategy(item).update(item);
        assertEquals(20, item.quality);

        Item item2 = new Item("Iebe", 3, 4);
        selector.getUpdateStrategy(item2).update(item2);
        assertEquals(8, item2.quality);

        StrategySelector selector2 = new StrategySelectorBuilder().build();

        selector2.getUpdateStrategy(item).update(item);
        assertEquals(20, item.quality);

        selector2.getUpdateStrategy(item2).update(item);
        assertEquals(8, item2.quality);
    }

    @Test
    void testComplete() {
        StrategySelector selector = new StrategySelectorBuilder().setStrategy("Ayla", item -> item.quality = 42)
            .setDefault(item -> item.quality = 0)
            .build();

        Item aylaItem  = new Item("Ayla", 10, 10);
        selector.getUpdateStrategy(aylaItem).update(aylaItem);
        assertEquals(42, aylaItem.quality);

        Item defaultItem = new Item("", 3, 4);
        selector.getUpdateStrategy(defaultItem).update(defaultItem);
        assertEquals(0, defaultItem.quality);
    }
 }
