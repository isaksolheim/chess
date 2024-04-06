package me.isak.chess.game

import me.isak.chess.move.MoveCalculator

abstract class GameOverChecker(private val moveCalculator: MoveCalculator, private val gameState: GameState) {

    public enum class Winner{White,Black,Draw}

    abstract fun checkGameOver(): Boolean

}


