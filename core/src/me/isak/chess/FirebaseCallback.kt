package me.isak.chess

import me.isak.chess.model.FirebaseGameModel

/**
 * Callback interface for receiving data updates from Firebase.
 *
 * Implementations of this interface should handle the reception of updated
 * data models from Firebase, typically in response to a data fetch or listen operation.
 *
 * @see FirebaseGameModel for the structure of the data model expected in callbacks.
 */
interface FirebaseCallback {
    /**
     * Called when new data is received from Firebase.
     *
     * @param dataModel The [FirebaseGameModel] instance containing the updated game data.
     */
    fun onDataReceived(dataModel: FirebaseGameModel)
}
