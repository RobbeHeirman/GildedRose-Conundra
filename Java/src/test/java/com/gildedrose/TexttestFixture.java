package com.gildedrose;

import com.gildedrose.item_constraints.ItemConstraint;
import com.gildedrose.item_runnables.RunnableItem;
import com.gildedrose.item_update_strategy.StrategySelector;
import com.gildedrose.item_update_strategy.StrategySelectorBuilder;
import com.gildedrose.item_update_strategy.UpdateStrategy;

import java.util.Arrays;
import java.util.List;

public class TexttestFixture {
    public static void main(String[] args) {
        System.out.println("OMGHAI!");

        Item[] items = new Item[]{
            new Item("+5 Dexterity Vest", 10, 20), //
            new Item("Aged Brie", 2, 0), //
            new Item("Elixir of the Mongoose", 5, 7), //
            new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            // this conjured item does not work properly yet
            new Item("Conjured Mana Cake", 3, 6)};

        // Just an example that we can set our strategies at runtime due to our refactor.
        StrategySelector selector = new StrategySelectorBuilder()
            .setDefault(UpdateStrategy::updateDefaultRegularItem)
            .setStrategy(Constants.AGED_ITEM, UpdateStrategy::updateDefaultAgedItem)
            .setStrategy(Constants.BACKSTAGE_ITEM, UpdateStrategy::updateDefaultBackStageItem)
            .setStrategy(Constants.LEGENDARY_ITEM, UpdateStrategy::updateDefaultLegendaryItem)
            .setStrategy(Constants.CONJURED_ITEM, UpdateStrategy::updateDefaultConjuredItem)
            .build();

        // An example on how GildedRose can execute any runnable instead of being constraint to items.
        List<Runnable> runnableItems = Arrays.stream(items)
            .map(item -> (Runnable) new RunnableItem(item, selector.getUpdateStrategy(item), ItemConstraint.getItemConstraints(item)))
            .toList();
        GildedRose app = new GildedRose(runnableItems);

        int days = 2;
        if (args.length > 0) {
            days = Integer.parseInt(args[0]) + 1;
        }

        for (int i = 0; i < days; i++) {
            System.out.println("-------- day " + i + " --------");
            System.out.println("name, sellIn, quality");
            for (Item item : items) {
                System.out.println(item);
            }
            System.out.println();
            app.updateQuality();
        }
    }

}
