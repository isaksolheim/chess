package me.isak.chess.model.base

import me.isak.chess.model.versions.fisher.FisherGameState
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.versions.koth.KothGameOverChecker
import me.isak.chess.model.versions.horde.HordeGameOverChecker
import me.isak.chess.model.versions.racing.RacingGameOverChecker
import me.isak.chess.model.versions.racing.RacingGameState

/**
 * Used to initialize the correct game objects for chess, 
 * depending on the specified version.
 * 
 * The name is slightly misleading, because it does not follow the factory pattern fully.
 * It is still reasonable however, since it abstracts away the creation of objects, 
 * and makes sure the correct family of objects is accessible. 
 */
class SimpleGameFactory(version: String, _fen: String) {
    private var pieceMap: PieceMap
    private var simpleMoveCalculator: SimpleMoveCalculator
    private var gameState: GameState
    private var gameHistory: GameHistory
    private var moveCalculator: MoveCalculator
    private var moveExecutor: MoveExecutor
    private var gameOverChecker: GameOverChecker

    /**
     * Create the game objects based on which version of chess is being played.
     * Start by extracting relevant information about the game from fen string.
     * Then create the correct set of objects, and give game info to them.
     */
    init {

        // Some games vary only in how the game is setup. 
        // This function will make sure they get the right starting information
        val fen = when (version) {
            "racing" -> "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1"
            "horde" -> "rnbqkbnr/pppppppp/8/1PP2PP1/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w kq - 0 1"
            else -> _fen
        }

        when (version) {
            "standard" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "koth" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = KothGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "horde" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = HordeGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "fisher" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = FisherGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "racing" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = RacingGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = RacingGameOverChecker(moveCalculator, gameState, gameHistory)
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