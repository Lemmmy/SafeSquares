package sh.lem.safesquares

import net.fabricmc.api.ModInitializer
import net.glasslauncher.mods.gcapi3.api.ConfigRoot
import net.mine_diver.unsafeevents.listener.EventListener
import net.minecraft.client.option.KeyBinding
import net.modificationstation.stationapi.api.client.event.keyboard.KeyStateChangedEvent
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegisterEvent
import net.modificationstation.stationapi.api.event.mod.InitEvent
import org.lwjgl.input.Keyboard
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object SafeSquares: ModInitializer {
  const val MOD_ID: String = "safesquares"
  const val MOD_NAME: String = "SafeSquares"

  @JvmField val log: Logger = LoggerFactory.getLogger(MOD_ID)

  @JvmStatic
  @ConfigRoot(value = MOD_ID, visibleName = MOD_NAME, nameKey = "gui.$MOD_ID.config.name")
  val config = SafeSquaresConfig()
  val keyToggleSafeSquares = KeyBinding("key.$MOD_ID.toggle", Keyboard.KEY_L)

  private var active = false
  val isActive
    get() = active && config.enabled!!

  override fun onInitialize() {}

  @EventListener
  fun onInit(event: InitEvent) {
    log.info("$MOD_NAME initializing")
  }

  @EventListener
  fun onKeyBindingRegister(event: KeyBindingRegisterEvent) {
    event.keyBindings.add(keyToggleSafeSquares)
  }

  @EventListener
  fun onKeyStateChangedEvent(event: KeyStateChangedEvent) {
    if (event.environment != KeyStateChangedEvent.Environment.IN_GAME) return
    if (!config.enabled!!) return

    if (Keyboard.isKeyDown(keyToggleSafeSquares.code)) {
      active = !active
      log.debug("Toggling $MOD_NAME: $active")
      if (active) Checker.check()
    }
  }
}