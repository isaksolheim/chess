package me.isak.chess

import me.isak.chess.models.FirebaseGameModel

/**
 * This interface outlines methods for interacting with Firebase Database,
 * including fetching data, pushing new values, and setting values at the database root.
 */
interface FirebaseInterface {
    /**
     * Retrieves data from Firebase at a specified key and logs the value.
     *
     * @param key The key at which to retrieve the value.
     */
    fun getData(key: String)

    /**
     * Pushes a given value to a specific path in the Firebase database.
     *
     * @param key The key under which to store the value.
     * @param value The value to be stored.
     */
    fun pushValue(key: String, value: FirebaseGameModel)

    /**
     * Sets a given value at the root of the Firebase database.
     *
     * @param key Unused parameter in this context, kept for interface compliance.
     * @param value The value to be set at the root.
     */
    fun setValue(key: String, value: String)
}