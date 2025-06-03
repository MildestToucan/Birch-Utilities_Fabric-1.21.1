package net.mildtoucan.birch_util.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.mildtoucan.birch_util.item.ModItems;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = true) //Need to disable this from any actual release
@Mixin(WolfEntity.class)
public abstract class WolfBirchShearsInteractMixin {
    @WrapWithCondition(method = "interactMob", //I do not understand this syntax but if it works, it works
            slice = @Slice( //Targeted calls who are only used once within interactMob to be as precise as possible.
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copyWithCount(I)Lnet/minecraft/item/ItemStack;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;isInSittingPose()Z")
            ),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
    )
    private boolean isItemBirchShears(ItemStack instance, Item item) {
        return !(instance.isOf(ModItems.BIRCH_SHEARS));
    }

}
