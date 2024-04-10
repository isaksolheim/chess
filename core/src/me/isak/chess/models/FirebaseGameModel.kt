package me.isak.chess.models

/**
 * Model for storing games in Firebase Realtime Database
 * */
data class FirebaseGameModel(
    val id: String = "",
    var board: String = "",
    var currentTurn: String = ""
)
