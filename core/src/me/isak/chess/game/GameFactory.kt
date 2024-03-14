package me.isak.chess.game

import me.isak.chess.versions.standard.StandardMovesetMap
import me.isak.chess.versions.standard.StandardGameState
import me.isak.chess.versions.standard.StandardGameHistory
import me.isak.chess.move.MoveCalculator
import me.isak.chess.game.GameHistory
import me.isak.chess.move.MovesetMap

data class FactoryResult(val calculator: MoveCalculator, val gameState: GameState, val gameHistory: GameHistory)

class GameFactory {

    fun create(version: String): FactoryResult {

        if (version == "standard") {
            val movesetMap = StandardMovesetMap()
            val calculator = MoveCalculator(movesetMap)
            val gameState = StandardGameState(calculator)
            val gameHistory = StandardGameHistory()
            return FactoryResult(calculator, gameState, gameHistory)
        }

        throw Error("Incorrect version provided to GameFactory.create")
    }
}