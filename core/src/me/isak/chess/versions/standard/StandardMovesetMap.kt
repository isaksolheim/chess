package me.isak.chess.versions.standard


import me.isak.chess.move.Moveset
import me.isak.chess.move.MovesetMap
import me.isak.chess.move.Mb


val occupied = Regex(".\\w")
val always = Regex("..")

val enemies = Regex("[A-Z][a-z]|[a-z][A-Z]")
val friends = Regex("[A-Z][A-Z]|[a-z][a-z]")

val notEnemies = Regex("[A-Z][A-Z ]|[a-z][a-z ]")


val rook = arrayOf(
    Mb().directions(arrayOf("E", "S", "W", "N")).stopAfter(enemies).build()
)

val knight = arrayOf(
    Mb().directions(arrayOf("NNE", "EEN", "EES", "SSE", "SSW", "WWS", "WWN", "NNW")).build()
)

val bishop = arrayOf(
    Mb().directions(arrayOf("NE", "SE", "SW", "NW")).stopAfter(enemies).build()
)

val queen = arrayOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).stopAfter(enemies).build()
)

val whiteKing = arrayOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).build(),
    Mb().directions(arrayOf("EE")).boardCondition(Regex("I  R$")).replacement(" RK ").tag("K").build(),
    Mb().directions(arrayOf("WW")).boardCondition(Regex("R   I(...)")).replacement("  KR $1").tag("Q").build()
)

val blackKing = arrayOf(
    Mb().directions(arrayOf("NE", "E", "SE", "S", "SW", "W", "NW", "N")).build(),
    Mb().directions(arrayOf("EE")).boardCondition(Regex("^(....)I  r")).replacement("$1 rk ").tag("k").build(),
    Mb().directions(arrayOf("WW")).boardCondition(Regex("^r   I")).replacement("  kr ").tag("q").build()
)

val whitePawn = arrayOf(
    Mb().directions(arrayOf("NE", "NW")).stopBefore(notEnemies).tag("P").build(),
    Mb().directions(arrayOf("N")).stopBefore(occupied).tag("P").build(),
    Mb().directions(arrayOf("NN")).stopBefore(occupied).boardCondition(Regex(" .{7}I.{8,15}")).tag("whitePawnDoubleForward").build(),
    Mb().directions(arrayOf("NE")).stopBefore(occupied).boardCondition(Regex(" (.{6})Ip(.{32,38})$")).replacement("P$1  $2").tag("enPassant").build(),
    Mb().directions(arrayOf("NW")).stopBefore(occupied).boardCondition(Regex(" (.{7})pI(.{32,38})$")).replacement("P$1  $2").tag("enPassant").build()
)

val blackPawn = arrayOf(
    Mb().directions(arrayOf("SE", "SW")).stopBefore(notEnemies).tag("p").build(),
    Mb().directions(arrayOf("S")).stopBefore(occupied).tag("p").build(),
    Mb().directions(arrayOf("SS")).stopBefore(occupied).boardCondition(Regex("^.{8,15}I.{7} ")).tag("blackPawnDoubleForward").build(),
    Mb().directions(arrayOf("SE")).stopBefore(occupied).boardCondition(Regex("^(.{32,38})IP(.{7}) ")).replacement("$1  $2p").tag("enPassant").build(),
    Mb().directions(arrayOf("SW")).stopBefore(occupied).boardCondition(Regex("^(.{32,38})PI(.{6}) ")).replacement("$1  $2p").tag("enPassant").build()
)

val standardMovesetMap: Map<Char, Array<Moveset>> = mapOf(
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
class StandardMovesetMap: MovesetMap {

    override fun getMoveset(piece: Char): Array<Moveset> { 
        return standardMovesetMap.get(piece) ?: throw Error("Moveset does not exist for this piece")
    }   
}

