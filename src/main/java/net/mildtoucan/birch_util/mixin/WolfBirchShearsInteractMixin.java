package net.mildtoucan.birch_util.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mildtoucan.birch_util.item.ModItems;
import net.mildtoucan.birch_util.item.custom.BirchShearsItem;
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
    @WrapOperation(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean isNotBirchShearsItem(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) && !(instance.isOf(ModItems.BIRCH_SHEARS));
    }

}
