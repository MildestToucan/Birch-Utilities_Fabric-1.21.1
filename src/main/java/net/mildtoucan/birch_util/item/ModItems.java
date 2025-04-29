package net.mildtoucan.birch_util.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.mildtoucan.birch_util.BirchUtilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {






    //Custom fuel source
    public static final Item BIRCH_CHARCOAL = registerItem("birch_charcoal", new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(BirchUtilities.MOD_ID, name), item);
    }

    public static void registerModItems() {
        BirchUtilities.LOGGER.info("Registering Mod Items for: " + BirchUtilities.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {

        });
    }

}
