package sh.lem.safesquares.mixin;

import net.minecraft.client.InteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sh.lem.safesquares.Checker;

@Mixin(InteractionManager.class)
public class InteractionManagerMixin {
  @Inject(
    method = "interactBlock",
    at = @At("RETURN")
  )
  public void onPlaceBlock(
    PlayerEntity world,
    World item,
    ItemStack x,
    int y,
    int z,
    int side,
    int par7,
    CallbackInfoReturnable<Boolean> cir
  ) {
    // trigger an immediate safe block check on block place
    if (cir.getReturnValueZ()) {
      Checker.checkNextTick();
    }
  }

  @Inject(
    method = "breakBlock",
    at = @At("RETURN")
  )
  public void onBreakBlock(
    int y,
    int z,
    int direction,
    int par4,
    CallbackInfoReturnable<Boolean> cir
  ) {
    // trigger an immediate safe block check on block break
    if (cir.getReturnValueZ()) {
      Checker.checkNextTick();
    }
  }
}
