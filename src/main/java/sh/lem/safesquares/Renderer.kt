package sh.lem.safesquares

import net.glasslauncher.mods.gcapi3.mixin.client.MinecraftAccessor
import net.minecraft.client.render.Tessellator
import org.lwjgl.opengl.GL11

object Renderer {
  private val cfg by SafeSquares::config

  @JvmStatic
  fun render(tickDelta: Float) {
    if (!SafeSquares.isActive) return

    val scale = (cfg.crossScale ?: 1.0) / 2
    val epsilon = cfg.crossEpsilon ?: (2.0 / 16.0)
    val width = cfg.crossLineWidth ?: 2.0f

    val skyEnabled = cfg.safeSkyEnabled ?: true
    val darkEnabled = cfg.safeDarkEnabled ?: true
    if (!skyEnabled && !darkEnabled) return

    val skyColor = ConfigColors.safeSkyColorParsed
    val darkColor = ConfigColors.safeDarkColorParsed

    val grid = Checker.grid
    if (grid.isEmpty) return

    val worldX = grid.worldX
    val worldY = grid.worldY
    val worldZ = grid.worldZ
    val radius = grid.radius

    val player = MinecraftAccessor.getInstance().player ?: return
    val camX = player.lastTickX + (player.x - player.lastTickX) * tickDelta.toDouble()
    val camY = player.lastTickY + (player.y - player.lastTickY) * tickDelta.toDouble()
    val camZ = player.lastTickZ + (player.z - player.lastTickZ) * tickDelta.toDouble()

    GL11.glDisable(GL11.GL_TEXTURE_2D)
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glLineWidth(width)

    val t = Tessellator.INSTANCE
    t.start(GL11.GL_LINES)

    for (z in -radius..radius) {
      for (y in -radius..radius) {
        for (x in -radius..radius) {
          val v = grid.get(x, y, z)
          if (v == 0) continue

          val color = when {
            skyEnabled && v == 1 -> skyColor
            darkEnabled && v == 2 -> darkColor
            else -> continue
          }

          val wx = worldX + x
          val wy = worldY + y
          val wz = worldZ + z

          val cx = -camX + wx + 0.5
          val cy = -camY + wy + epsilon
          val cz = -camZ + wz + 0.5

          val x1 = cx - scale
          val x2 = cx + scale
          val z1 = cz - scale
          val z2 = cz + scale

          with (color) {
            t.color(r, g, b, a)
          }

          // bottom left -> top right
          t.vertex(x1, cy, z1)
          t.vertex(x2, cy, z2)

          // top left -> bottom right
          t.vertex(x1, cy, z2)
          t.vertex(x2, cy, z1)
        }
      }
    }

    t.draw()

    GL11.glLineWidth(2.0f)
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glEnable(GL11.GL_TEXTURE_2D)
  }
}