package sh.lem.safesquares.mixin;

import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sh.lem.safesquares.Renderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
  @Inject(
    method = "renderFrame",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/client/render/WorldRenderer;renderClouds(F)V"
    )
  )
  public void renderSafeSquares(float time, long par2, CallbackInfo ci) {
    Renderer.render(time);
  }
}
