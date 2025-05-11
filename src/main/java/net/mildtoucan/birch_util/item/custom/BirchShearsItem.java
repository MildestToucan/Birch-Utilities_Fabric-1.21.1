package net.mildtoucan.birch_util.item.custom;


import net.mildtoucan.birch_util.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.PumpkinBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
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
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        //^^ Grabs info about the clicked block, creating it and the block into a variable we can use.
        //This is done to replicate the onUseWithItem parameters of the blocks we're interacting with.
        if(!world.isClient()) {
            if(clickedBlock instanceof PumpkinBlock) {
                Direction direction = context.getSide();
                Direction direction2 = direction.getAxis() == Direction.Axis.Y ? player.getHorizontalFacing().getOpposite() : direction;
                world.playSound(null, pos, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0f, 1.0f);
                world.setBlockState(pos, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction2), Block.NOTIFY_ALL_AND_REDRAW);

                ItemEntity itemEntity = new ItemEntity(
                        world,
                        pos.getX() + 0.5 + direction2.getOffsetX() * 0.65,
                        pos.getY() + 0.1,
                        pos.getZ() + 0.5 + direction2.getOffsetZ() * 0.65,
                        new ItemStack(Items.PUMPKIN_SEEDS, 4)
                );
                itemEntity.setVelocity(
                        0.05 * direction2.getOffsetX() + world.random.nextDouble() * 0.02, 0.05, 0.05 * direction2.getOffsetZ() + world.random.nextDouble() * 0.02
                );
                world.spawnEntity(itemEntity);

                context.getStack().damage(1,((ServerWorld) world), ((ServerPlayerEntity) context.getPlayer()),
                        item -> context.getPlayer().sendEquipmentBreakStatus(item, EquipmentSlot.MAINHAND));

                world.emitGameEvent(player, GameEvent.SHEAR, pos);
            }
        }
        return ActionResult.SUCCESS;
    }
}
