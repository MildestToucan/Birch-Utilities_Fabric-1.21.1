package net.mildtoucan.birch_util.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.entity.LivingEntity.getSlotForHand;

//Taking example on the VanillaShears Mod by Apis035, adapting to current Java/MC versions

public class BirchShearsItem extends ShearsItem {
    public BirchShearsItem(Settings settings) {
        super(settings.maxDamage(595) //About 2.5x more durability than regular shears.
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent()));
    }

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

        return ActionResult.PASS;
    }


    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {

        return super.postMine(stack, world, state, pos, miner);
    }
}
