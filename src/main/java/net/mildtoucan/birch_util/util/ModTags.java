package net.mildtoucan.birch_util.util;

import net.mildtoucan.birch_util.BirchUtilities;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {


    public static class Blocks {

        public static final TagKey<Block> SHEARABLE_BLOCKS = createTag("shearable_blocks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(BirchUtilities.MOD_ID, name));
        }
    }


    public static class Items {


    }
}
