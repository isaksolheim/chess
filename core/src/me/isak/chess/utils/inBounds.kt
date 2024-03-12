val directionMap: Map<Char, Int> = mapOf(
    'E' to 1,
    'S' to 1,
    'W' to -1,
    'N' to -1
)

fun decomposeDirection(direction: String): Pair<Int, Int> {
    val dx = direction.sumOf { if (it in setOf('E', 'W')) directionMap[it] ?: 0 else 0 }
    val dy = direction.sumOf { if (it in setOf('S', 'N')) directionMap[it] ?: 0 else 0 }
    return Pair(dx, dy)
}

fun isInBounds(index: Int, direction: String): Boolean {
    val x = index % 8
    val y = index / 8
    val (dx, dy) = decomposeDirection(direction)

    val newX = x + dx
    val newY = y + dy

    return newX in 0 until 8 && newY in 0 until 8
}

fun parseDirection(direction: String): Int {
    val (dx, dy) = decomposeDirection(direction)
    return dx + dy * 8
}
