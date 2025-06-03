package net.mildtoucan.birch_util;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.mildtoucan.birch_util.item.ModItems;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.item.ItemGroups;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.mildtoucan.birch_util.item.ModItems.BIRCH_SHEARS;

public class BirchUtilities implements ModInitializer {
	public static final String MOD_ID = "birch_util";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.registerModItems();





		//Register modded items into vanilla item groups.
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(BIRCH_SHEARS));
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(content -> content.add(ModItems.BIRCH_CHARCOAL));


		FuelRegistry.INSTANCE.add(ModItems.BIRCH_CHARCOAL, 2400); //2400 ticks of burn time, 12 standard items burnt

		//Registers the dispenser behavior, based on WoodenShears mod by Cech12 in WoodenShears mod.
		DispenserBlock.registerBehavior(BIRCH_SHEARS, new ShearsDispenserBehavior());
	}
}