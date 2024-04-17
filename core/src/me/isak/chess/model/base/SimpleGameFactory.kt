package me.isak.chess.model.base

import me.isak.chess.model.versions.fischer.fischerGameState
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.versions.koth.KothGameOverChecker
import me.isak.chess.model.versions.horde.HordeGameOverChecker
import me.isak.chess.model.versions.racing.RacingGameOverChecker
import me.isak.chess.model.versions.racing.RacingGameState
import me.isak.chess.model.versions.threecheck.ThreeCheckGameOverChecker
import me.isak.chess.model.versions.gounki.makrukPieceMap
import me.isak.chess.model.versions.makruk.MakrukGameState
import me.isak.chess.model.versions.makruk.MakrukGameHistory
import me.isak.chess.model.versions.makruk.MakrukGameOverChecker
import me.isak.chess.model.versions.atomic.AtomicGameState
import me.isak.chess.model.versions.atomic.AtomicGameOverChecker

/**
 * Used to initialize the correct game objects for chess, 
 * depending on the specified version.
 * 
 * The name is slightly misleading, because it does not follow the factory pattern fully.
 * It is still reasonable however, since it abstracts away the creation of objects, 
 * and makes sure the correct family of objects is accessible. 
 */
class SimpleGameFactory(version: String, _fen: String?) {
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

        // Use passed fen if it exist. If not, use fen specified for each version.
        val fen = _fen ?: when (version) {
            "racing" -> "8/8/8/8/8/8/krbnNBRK/qrbnNBRQ w - - 0 1"
            "horde" -> "rnbqkbnr/pppppppp/8/1PP2PP1/PPPPPPPP/PPPPPPPP/PPPPPPPP/PPPPPPPP w kq - 0 1"
            "makruk"-> "rnbqkbnr/8/pppppppp/8/8/PPPPPPPP/8/RNBQKBNR w - - 0 1"
            else -> "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
        }

        when (version) {
            "standard" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "koth" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = KothGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "horde" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = HordeGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "fischer" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = fischerGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "racing" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = RacingGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = RacingGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "threecheck" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = StandardGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = ThreeCheckGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "makruk" -> {
                pieceMap = PieceMap(makrukPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "general")
                gameState = MakrukGameState(simpleMoveCalculator, fen)
                gameHistory = MakrukGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = MakrukGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            "atomic" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap, "standard")
                gameState = AtomicGameState(simpleMoveCalculator, fen)
                gameHistory = StandardGameHistory(fen)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = AtomicGameOverChecker(moveCalculator, gameState, gameHistory)
            }
            else -> throw IllegalArgumentException("Incorrect version ($version) provided to GameFactory.create")
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