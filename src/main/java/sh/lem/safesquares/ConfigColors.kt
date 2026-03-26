package sh.lem.safesquares

object ConfigColors {
  val safeSkyColorParsed by CachedParsedColor(SAFE_SKY_COLOR_DEFAULT) { SafeSquares.config.safeSkyColor }
  val safeDarkColorParsed by CachedParsedColor(SAFE_DARK_COLOR_DEFAULT) { SafeSquares.config.safeDarkColor }

  data class ParsedColor(val r: Float, val g: Float, val b: Float, val a: Float)

  class CachedParsedColor(val default: String, val source: () -> String?) {
    var lastRaw: String? = null
    var lastParsed: ParsedColor = parseColorOrNull(default)!!

    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): ParsedColor {
      val raw = source()
      if (raw === lastRaw || raw == lastRaw) return lastParsed

      lastParsed = parseColorOrNull(raw) ?: parseColorOrNull(default)!!
      lastRaw = raw
      return lastParsed
    }
  }

  fun parseColorOrNull(input: String?): ParsedColor? {
    if (input == null) return null

    val s = input.trim().removePrefix("#")
    val hex = s.toLongOrNull(16) ?: return null

    return when (s.length) {
      6 -> {
        val r = ((hex shr 16) and 0xFF).toFloat() / 255.0f
        val g = ((hex shr 8) and 0xFF).toFloat() / 255.0f
        val b = (hex and 0xFF).toFloat() / 255.0f
        ParsedColor(r, g, b, 1.0f)
      }
      8 -> {
        val a = ((hex shr 24) and 0xFF).toFloat() / 255.0f
        val r = ((hex shr 16) and 0xFF).toFloat() / 255.0f
        val g = ((hex shr 8) and 0xFF).toFloat() / 255.0f
        val b = (hex and 0xFF).toFloat() / 255.0f
        ParsedColor(r, g, b, a)
      }
      else -> null
    }
  }
}