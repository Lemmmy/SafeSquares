package sh.lem.safesquares.mixin;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.lem.safesquares.Checker;

@Mixin(World.class)
public class WorldMixin {
  @Inject(
    method = "tick",
    at = @At("RETURN")
  )
  public void onWorldTick(CallbackInfo ci) {
    Checker.autoCheck();
  }
}
