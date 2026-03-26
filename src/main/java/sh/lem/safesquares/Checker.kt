package sh.lem.safesquares

import net.glasslauncher.mods.gcapi3.mixin.client.MinecraftAccessor
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.NaturalSpawner
import net.minecraft.world.World

object Checker {
  private var ticks: Int = 0
  private val cfg by SafeSquares::config

  private val radius get() = cfg.renderDistance ?: 0
  var grid = CheckGrid(radius)
    private set

  fun check() {
    try {
      if (!SafeSquares.isActive) return

      val player = MinecraftAccessor.getInstance().player ?: return
      performCheck(player)
    } catch (e: Exception) {
      SafeSquares.log.error("Failed to tick SafeSquares checker", e)
    }
  }

  @JvmStatic
  fun checkNextTick() {
    if (!SafeSquares.isActive) return
    ticks = (cfg.updateRate!!) - 1
  }

  @JvmStatic
  fun autoCheck() {
    if (!SafeSquares.isActive) return

    if (ticks++ >= cfg.updateRate!!) {
      check()
    }
  }

  private fun performCheck(player: PlayerEntity) {
    val world = player.world ?: return
    val safeLightLevel = cfg.safeLightLevel ?: 7
    val r = radius

    // reset check timer even on forced checks (block place)
    ticks = 0

    // reset the grid if the radius changed, let the old one get gc'd
    if (grid.radius != r) {
      grid = CheckGrid(r)
    }

    grid.reset(player.x.toInt(), player.y.toInt(), player.z.toInt())

    for (z in -r..r) {
      for (y in -r..r) {
        for (x in -r..r) {
          val wx = grid.worldX + x
          val wy = grid.worldY + y
          val wz = grid.worldZ + z
          if (world.isOutOfHeightLimit(wy)) {
            grid.set(x, y, z, 0) // always safe, skip every check
            continue
          }

          val canSpawn = NaturalSpawner.isValidSpawnPos(SpawnGroup.MONSTER, world, wx, wy, wz)
          if (!canSpawn) {
            grid.set(x, y, z, 0) // always safe, skip light checks
            continue
          }

          // check the block light directly at each ambient light level
          val chunk = world.getChunk(wx shr 4, wz shr 4)
          val cx = wx and 15
          val cz = wz and 15

          val darkLight = chunk.getLight(cx, wy, cz, 16) > safeLightLevel
          if (darkLight) {
            grid.set(x, y, z, 0) // always safe, skip sky light check
            continue
          }

          val skyLight = chunk.getLight(cx, wy, cz, 0) > safeLightLevel
          grid.set(x, y, z, if (skyLight) 1 /* only safe at day */ else 2 /* never safe */)
        }
      }
    }
  }
}