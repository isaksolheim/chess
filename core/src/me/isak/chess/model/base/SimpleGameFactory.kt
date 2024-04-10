package me.isak.chess.model.base

import me.isak.chess.model.versions.fisher.FisherGameState
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.versions.koth.KothGameOverChecker
import me.isak.chess.model.versions.horde.HordeGameState
import me.isak.chess.model.versions.horde.HordeGameOverChecker

/**
 * Used to initialize the correct game objects for chess, 
 * depending on the specified version.
 * 
 * The name is slightly misleading, because it does not follow the factory pattern fully.
 * It is still reasonable however, since it abstracts away the creation of objects, 
 * and makes sure the correct family of objects is accessible. 
 */
class SimpleGameFactory(version: String) {
    private var pieceMap: PieceMap
    private var simpleMoveCalculator: SimpleMoveCalculator
    private var gameState: GameState
    private var gameHistory: GameHistory
    private var moveCalculator: MoveCalculator
    private var moveExecutor: MoveExecutor
    private var gameOverChecker: GameOverChecker

    /**
     * Create the game objects based on which version of chess is being played.
     */
    init {
        when (version) {
            "standard" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator)
                gameHistory = StandardGameHistory()
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState)
            }
            "koth" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator)
                gameHistory = StandardGameHistory()
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = KothGameOverChecker(moveCalculator, gameState)
            }
            "horde" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = HordeGameState(simpleMoveCalculator)
                gameHistory = StandardGameHistory()
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = HordeGameOverChecker(moveCalculator, gameState)
            }
            "fisher" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = FisherGameState(simpleMoveCalculator)
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