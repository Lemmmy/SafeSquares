package sh.lem.safesquares

import net.glasslauncher.mods.gcapi3.api.ConfigEntry
import sh.lem.safesquares.SafeSquares.MOD_ID

const val SAFE_SKY_COLOR_DEFAULT = "#DDDD00"
const val SAFE_DARK_COLOR_DEFAULT = "#FF2222"

private const val C = "gui.${MOD_ID}.config"

class SafeSquaresConfig {
  @JvmField
  @ConfigEntry(
    name = "Enabled",
    nameKey = "$C.enabled",
    description = "Enables or disables SafeSquares completely.",
    descriptionKey = "$C.enabled.desc",
  )
  var enabled: Boolean? = true

  @JvmField
  @ConfigEntry(
    name = "Render distance",
    nameKey = "$C.render_distance",
    description = "How many blocks away from the player to check for safe blocks.",
    descriptionKey = "$C.render_distance.desc",
    minValue = 1.0,
    maxValue = 256.0,
  )
  var renderDistance: Int? = 16

  @JvmField
  @ConfigEntry(
    name = "Update rate",
    nameKey = "$C.update_rate",
    description = "How often blocks should be checked automatically, in ticks.",
    descriptionKey = "$C.update_rate.desc",
    minValue = 1.0,
    maxValue = 100.0,
  )
  var updateRate: Int? = 8

  @JvmField
  @ConfigEntry(
    name = "Show spot safety under the sky",
    nameKey = "$C.safe_sky_enabled",
    description = "If enabled, blocks that are unsafe under the sky will be highlighted.",
    descriptionKey = "$C.safe_sky_enabled.desc",
  )
  var safeSkyEnabled: Boolean? = true

  @JvmField
  @ConfigEntry(
    name = "Sky unsafe color",
    nameKey = "$C.safe_sky_enabled",
    description = "Color of blocks that are unsafe under the sky (hex code, #RRGGBB or #AARRGGBB).",
    descriptionKey = "$C.safe_sky_enabled.desc",
  )
  var safeSkyColor = SAFE_SKY_COLOR_DEFAULT

  @JvmField
  @ConfigEntry(
    name = "Show spot safety in darkness",
    nameKey = "$C.safe_dark_enabled",
    description = "If enabled, blocks that are unsafe in darkness will be highlighted.",
    descriptionKey = "$C.safe_dark_enabled.desc",
  )
  var safeDarkEnabled: Boolean? = true

  @JvmField
  @ConfigEntry(
    name = "Unsafe color",
    nameKey = "$C.safe_dark_enabled",
    description = "Color of blocks that are unsafe in darkness (hex code, #RRGGBB or #AARRGGBB).",
    descriptionKey = "$C.safe_dark_enabled.desc",
  )
  var safeDarkColor = SAFE_DARK_COLOR_DEFAULT

  @JvmField
  @ConfigEntry(
    name = "Cross scale",
    nameKey = "$C.cross_scale",
    description = "Scale of the cross, relative to the size of the block (1).",
    descriptionKey = "$C.cross_scale.desc",
    minValue = 0.1,
    maxValue = 2.0,
  )
  var crossScale: Double? = 1.0

  @JvmField
  @ConfigEntry(
    name = "Cross epsilon",
    nameKey = "$C.cross_epsilon",
    description = "How high above the block to render the cross, relative to the size of the block (1).",
    descriptionKey = "$C.cross_epsilon.desc",
    minValue = -1.0,
    maxValue = 1.0,
  )
  var crossEpsilon: Double? = 2.0 / 16.0

  @JvmField
  @ConfigEntry(
    name = "Cross line width",
    nameKey = "$C.cross_line_width",
    description = "Thickness of the line, in pixels.",
    descriptionKey = "$C.cross_line_width.desc",
    minValue = 1.0,
    maxValue = 10.0,
  )
  var crossLineWidth: Float? = 2.0f

  @JvmField
  @ConfigEntry(
    name = "Safe light level",
    nameKey = "$C.safe_light_level",
    description = "Light level considered safe from monster spawns (default 7).",
    descriptionKey = "$C.safe_light_level.desc",
    minValue = 1.0,
    maxValue = 16.0,
  )
  var safeLightLevel: Int? = 7
}