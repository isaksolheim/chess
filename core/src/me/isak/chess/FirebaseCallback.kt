package me.isak.chess

import me.isak.chess.model.FirebaseGameModel

interface FirebaseCallback {
    fun onDataReceived(dataModel: FirebaseGameModel)
}