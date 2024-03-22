package me.isak.chess.game

import me.isak.chess.move.Move

interface GameHistory {

    fun checkHistory(move: Move): Boolean
    fun changeHistory(move: Move): Unit

}