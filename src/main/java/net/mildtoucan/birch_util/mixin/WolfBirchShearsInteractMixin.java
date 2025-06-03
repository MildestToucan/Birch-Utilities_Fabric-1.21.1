package net.mildtoucan.birch_util.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mildtoucan.birch_util.item.ModItems;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Debug(export = false) //Need to disable this from any actual release
@Mixin(WolfEntity.class)
public abstract class WolfBirchShearsInteractMixin {
    @WrapOperation(method = "interactMob", //I do not understand this syntax but if it works, it works
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copyWithCount(I)Lnet/minecraft/item/ItemStack;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;isInSittingPose()Z")
            ),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z")
    )
    private boolean isItemBirchShears(ItemStack instance, Item item, Operation<Boolean> original) {
        return !(original.call(instance, item) && instance.isOf(ModItems.BIRCH_SHEARS));
    }

}
