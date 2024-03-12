

/* Time for chaos */

val rook = listOf(
    Mb().directions(arrayOf("E", "S", "W", "N")).stopAfter(enemies).build()
)

val knight = listOf(
    Mb().directions(arrayOf("NNE", "EEN", "EES", "SSE", "SSW", "WWS", "WWN", "NNW")).build()
)

val bishop = listOf(
    Mb().directions(arrayOf("NE", "SE", "SW", "NW")).stopAfter(enemies).build()
)

val queen = listOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).stopAfter(enemies).build()
)

val whiteKing = listOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).build(),
    Mb().directions(arrayOf("EE"))
        .boardCondition("I  R$".toRegex())
        .replacement(" RK ")
        .tag("K")
        .build(),
    Mb().directions(arrayOf("WW"))
        .boardCondition("R   I(...)".toRegex())
        .replacement("  KR {1}")
        .tag("Q")
        .build()
)

val blackKing = listOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).build(),
    Mb().directions(arrayOf("EE"))
        .boardCondition("^(....)I  r".toRegex())
        .replacement("{1} rk ")
        .tag("k")
        .build(),
    Mb().directions(arrayOf("WW"))
        .boardCondition("^r   I".toRegex())
        .replacement("  kr ")
        .tag("q")
        .build()
)

val whitePawn = listOf(
    Mb().directions(arrayOf("NE", "NW")).stopBefore(notEnemies).tag("P").build(),
    Mb().directions(arrayOf("N")).stopBefore(occupied).tag("P").build(),
    Mb().directions(arrayOf("NN"))
        .stopBefore(occupied)
        .boardCondition(" .{7}I.{8,15}$".toRegex())
        .tag("whitePawnDoubleForward")
        .build(),
    Mb().directions(arrayOf("NE"))
        .stopBefore(occupied)
        .boardCondition(" (.{6})Ip(.{32,38})$".toRegex())
        .replacement("P{1}  {2}")
        .tag("enPassant")
        .build(),
    Mb().directions(arrayOf("NW"))
        .stopBefore(occupied)
        .boardCondition(" (.{7})pI(.{32,38})$".toRegex())
        .replacement("P{1}  {2}")
        .tag("enPassant")
        .build()
)

val blackPawn = listOf(
    Mb().directions(arrayOf("SE", "SW")).stopBefore(notEnemies).tag("p").build(),
    Mb().directions(arrayOf("S")).stopBefore(occupied).tag("p").build(),
    Mb().directions(arrayOf("SS"))
        .stopBefore(occupied)
        .boardCondition("^.{8,15}I.{7} ".toRegex())
        .tag("blackPawnDoubleForward")
        .build(),
    Mb().directions(arrayOf("SE"))
        .stopBefore(occupied)
        .boardCondition("^(.{32,38})IP(.{7}) ".toRegex())
        .replacement("{1}  {2}p")
        .tag("enPassant")
        .build(),
    Mb().directions(arrayOf("SW"))
        .stopBefore(occupied)
        .boardCondition("^(.{32,38})PI(.{6}) ".toRegex())
        .replacement("{1}  {2}p")
        .tag("enPassant")
        .build()
)

val movesetMap: Map<Char, List<Moveset>> = mapOf(
    'r' to rook,
    'R' to rook,
    'n' to knight,
    'N' to knight,
    'b' to bishop,
    'B' to bishop,
    'q' to queen,
    'Q' to queen,
    'k' to blackKing,
    'K' to whiteKing,
    'P' to whitePawn,
    'p' to blackPawn
)
