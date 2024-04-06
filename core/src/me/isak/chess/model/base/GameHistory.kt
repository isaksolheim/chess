package me.isak.chess.model.base


interface GameHistory {

    fun checkHistory(move: Move): Boolean
    fun changeHistory(move: Move): Unit

}