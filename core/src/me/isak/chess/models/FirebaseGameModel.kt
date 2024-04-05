package me.isak.chess.models

data class FirebaseGameModel(
    val id: String = "",
    var board: String = "",
    val turnId: Int = 0,
    val players: Map<String, String> = mapOf("white" to "", "black" to ""),
    var currentTurn: String = ""
)
