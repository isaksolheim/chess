package me.isak.chess

import me.isak.chess.models.FirebaseGameModel

interface FirebaseCallback {
    fun onDataReceived(dataModel: FirebaseGameModel)
}