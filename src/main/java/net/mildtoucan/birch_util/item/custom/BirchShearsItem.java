package net.mildtoucan.birch_util.item.custom;


import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import static net.minecraft.entity.LivingEntity.getSlotForHand;

//Taking mild inspiration from the VanillaShears Mod by Apis035, made their entity checking code much better though.

public class BirchShearsItem extends ShearsItem {
    public BirchShearsItem(Settings settings) {
        super(settings.maxDamage(595) //About 2.5x more durability than regular shears.
                .component(DataComponentTypes.TOOL, ShearsItem.createToolComponent()));
    }

    @Override
    //Should work for any entity with iShearable interface.
    //This won't work for tamed wolves, due to wolves already having a right click interaction outside of shears,
    //which will get prioritized over this. Need to use Mixins.
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!entity.getWorld().isClient()) {
            if(entity instanceof Shearable) {
                if(((Shearable) entity).isShearable()) {
                    ((Shearable) entity).sheared(SoundCategory.PLAYERS);
                    entity.emitGameEvent(GameEvent.SHEAR, user);
                    stack.damage(1, user, getSlotForHand(hand));
                }
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
            } else if(clickedBlock instanceof BeehiveBlock) {

            }
        }
        return ActionResult.SUCCESS;
    }
}
