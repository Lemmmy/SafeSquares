package sh.lem.safesquares.mixin;

import net.minecraft.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.lem.safesquares.Checker;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
  @Inject(
    method = "tick",
    at = @At("RETURN")
  )
  public void onClientTick(CallbackInfo ci) {
    Checker.autoCheck();
  }
}
