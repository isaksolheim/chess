package me.isak.chess.model

/**
 * Represents the model for a game's state as stored in Firebase.
 *
 * This data class encapsulates the essential information about a chess game.
 * It is used for both storing the game state in Firebase
 * and retrieving it for game logic and UI updates.
 *
 * @property id A unique identifier for the game, used as the key in Firebase.
 * @property board A string representation of the game board, where characters represent pieces.
 * @property currentTurn Indicates which player's turn it is, "white" or "black".
 */
data class FirebaseGameModel(
    val id: String = "",
    var board: String = "",
    var currentTurn: String = ""
)
