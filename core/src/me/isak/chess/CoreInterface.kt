package me.isak.chess

import me.isak.chess.model.FirebaseGameModel

class CoreInterface : FirebaseInterface {
    override fun getData(key: String, callback: FirebaseCallback) {
        TODO("Not yet implemented")
    }

    override fun pushValue(key: String, value: FirebaseGameModel) {
        TODO("Not yet implemented")
    }

    override fun setValue(key: String, value: FirebaseGameModel) {
        TODO("Not yet implemented")
    }
}