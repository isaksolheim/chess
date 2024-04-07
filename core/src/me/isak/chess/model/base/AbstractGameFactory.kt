package me.isak.chess.model.base

import me.isak.chess.model.versions.standard.StandardPieceMap
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker

/**
 * Represents the result of a factory method for creating the components
 * needed to play a game of chess.
 */
data class FactoryResult(
    val moveCalculator: MoveCalculator, 
    val moveExecutor: MoveExecutor, 
    val gameOverChecker: GameOverChecker,
    val gameState: GameState,
    val gameHistory: GameHistory
)

class AbstractGameFactory(version: String) {
    private var pieceMap: PieceMap
    private var simpleMoveCalculator: SimpleMoveCalculator
    private var gameState: GameState
    private var gameHistory: GameHistory
    private var moveCalculator: MoveCalculator
    private var moveExecutor: MoveExecutor
    private var gameOverChecker: GameOverChecker

    init {
        when (version) {
            "standard" -> {
                pieceMap = StandardPieceMap()
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator)
                gameHistory = StandardGameHistory()
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState)
            }
            else -> throw Error("Incorrect version ($version) provided to GameFactory.create")
        }
    }

    fun moveCalculator(): MoveCalculator {
        return moveCalculator
    }

    fun moveExecutor(): MoveExecutor {
        return moveExecutor
    }

    fun gameOverChecker(): GameOverChecker {
        return gameOverChecker
    }

    fun gameState(): GameState {
        return gameState
    }

    fun gameHistory(): GameHistory {
        return gameHistory
    }
}