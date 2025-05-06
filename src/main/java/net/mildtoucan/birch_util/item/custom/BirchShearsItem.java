package net.mildtoucan.birch_util.item.custom;


import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Map;

import static net.minecraft.entity.LivingEntity.getSlotForHand;

//Taking example on the VanillaShears Mod by Apis035, adapting to current Java/MC versions

public class BirchShearsItem extends ShearsItem {
    public BirchShearsItem(Settings settings) {
        super(settings.maxDamage(595) //About 2.5x more durability than regular shears.
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent()));
    }


    //This is using the same system as the chisel tool in KaupenJoe's 1.21 Fabric Tutorial.
    //Very unoptimized for a single block, will need to refine this into a better solution later.
    public static final Map<Block, Block> PUMPKIN_SHEAR_MAP =
            Map.of(
                    Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN
            );

    @Override //This Override essentially is for shearing entities.
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(entity.getWorld().isClient)
            return ActionResult.PASS;


        if(entity instanceof SheepEntity sheepEntity) {
            if(sheepEntity.isShearable()) {
                sheepEntity.sheared(SoundCategory.PLAYERS);
                sheepEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, getSlotForHand(hand));
            }
        }

        if(entity instanceof MooshroomEntity mooshroomEntity) {
            if(mooshroomEntity.isShearable()) {
                mooshroomEntity.sheared(SoundCategory.PLAYERS);
                mooshroomEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, getSlotForHand(hand));
            }
        }

        if(entity instanceof SnowGolemEntity snowGolemEntity) {
            if(snowGolemEntity.isShearable()) {
                snowGolemEntity.sheared(SoundCategory.PLAYERS);
                snowGolemEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, getSlotForHand(hand));
            }
        }

        if(entity instanceof BoggedEntity boggedEntity) {
            if(boggedEntity.isShearable()) {
                boggedEntity.sheared(SoundCategory.PLAYERS);
                boggedEntity.emitGameEvent(GameEvent.SHEAR, user);
                stack.damage(1, user, getSlotForHand(hand));
            }
        }

        return ActionResult.PASS;
    }// tyyyyyyyyyyyyy Message courtesy of my cat Hety

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block clickedBlock = world.getBlockState(context.getBlockPos()).getBlock();
        //Those two lines grab info on the world to determine the block being clicked on
        if(!world.isClient()) {
            if(PUMPKIN_SHEAR_MAP.containsKey(clickedBlock)) {
                world.setBlockState(context.getBlockPos(), PUMPKIN_SHEAR_MAP.get(clickedBlock).getDefaultState());

                context.getStack().damage(1, ((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.playSound(null, context.getBlockPos(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS);
                return ActionResult.SUCCESS;
            } else if(clickedBlock.equals(Blocks.BEEHIVE) || clickedBlock.equals(Blocks.BEE_NEST)) {

            }
        }



        return ActionResult.PASS;
    }
}
