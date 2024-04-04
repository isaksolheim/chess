package me.isak.chess.models

data class FirebaseGameModel(
    val board: String,
    val turnId: Int,
    val players: Map<String, String>, // "white" -> UID, "black" -> UID
    val currentTurn: String
)