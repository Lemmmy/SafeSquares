package sh.lem.safesquares

/** packs a 3d grid of 2-bit values into a byte array */
class CheckGrid(
  val radius: Int,
  var worldX: Int = 0,
  var worldY: Int = 0,
  var worldZ: Int = 0,
) {
  val diameter = radius * 2 + 1
  private val total = diameter * diameter * diameter
  private val data = ByteArray((total + 3) / 4) // 4 blocks per byte, 2 bits per block

  var isEmpty = true
    private set

  private fun index(x: Int, y: Int, z: Int) =
    ((z + radius) * diameter + (y + radius)) * diameter + (x + radius)

  fun reset(worldX: Int, worldY: Int, worldZ: Int) {
    this.worldX = worldX
    this.worldY = worldY
    this.worldZ = worldZ
    isEmpty = true
  }

  /**
   * @param value 0 = safe, 1 = unsafe at night, 2 = always unsafe
   */
  fun set(x: Int, y: Int, z: Int, value: Int) {
    val i = index(x, y, z)
    val byteIndex = i ushr 2
    val shift = (i and 3) shl 1

    val mask = 0b11 shl shift
    data[byteIndex] = (data[byteIndex].toInt() and mask.inv() or (value shl shift)).toByte()

    if (value != 0) isEmpty = false
  }

  fun get(x: Int, y: Int, z: Int): Int {
    val i = index(x, y, z)
    val byteIndex = i ushr 2
    val shift = (i and 3) shl 1

    return (data[byteIndex].toInt() ushr shift) and 0b11
  }
}