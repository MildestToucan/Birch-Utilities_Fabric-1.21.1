package net.mildtoucan.birch_util;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.mildtoucan.birch_util.item.ModItems;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BirchUtilities implements ModInitializer {
	public static final String MOD_ID = "birch_util";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();

		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> {
			content.add(ModItems.BIRCH_CHARCOAL);
		});
		//^ Modifies vanilla item category to add birch charcoal to the ingredients category


		FuelRegistry.INSTANCE.add(ModItems.BIRCH_CHARCOAL, 2400); //2400 ticks of burn time, 12 standard items burnt
	}
}