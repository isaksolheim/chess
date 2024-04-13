package me.isak.chess.model.base

import me.isak.chess.model.versions.fisher.FisherGameState
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker
import me.isak.chess.model.versions.standard.standardPieceMap
import me.isak.chess.model.versions.koth.KothGameOverChecker
import me.isak.chess.model.versions.horde.HordeGameOverChecker
import me.isak.chess.model.versions.racing.RacingGameOverChecker

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


        val parts = fen.split(" ")
        if (parts.size != 6) throw Error("incorrect fen format used to create game: $fen")
        
        // Extract game info from fen
        val board = processGameString(parts[0])
        val turn = parts[1] == "w"
        val castle = parts[2]
        val enPassant = processEnPassant(parts[3])
        val halfMove = parts[4].toInt()
        val fullMove = parts[5].toInt()

        when (version) {
            "standard" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, board, turn)
                gameHistory = StandardGameHistory(castle, enPassant, halfMove, fullMove)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState)
            }
            "koth" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, board, turn)
                gameHistory = StandardGameHistory(castle, enPassant, halfMove, fullMove)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = KothGameOverChecker(moveCalculator, gameState)
            }
            "horde" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, board, turn)
                gameHistory = StandardGameHistory(castle, enPassant, halfMove, fullMove)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = HordeGameOverChecker(moveCalculator, gameState)
            }
            "fisher" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = FisherGameState(simpleMoveCalculator, board, turn)
                gameHistory = StandardGameHistory(castle, enPassant, halfMove, fullMove)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = StandardGameOverChecker(moveCalculator, gameState)
            }
            "racing" -> {
                pieceMap = PieceMap(standardPieceMap)
                simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
                gameState = StandardGameState(simpleMoveCalculator, board, turn)
                gameHistory = StandardGameHistory(castle, enPassant, halfMove, fullMove)
                moveExecutor = MoveExecutor(gameState, gameHistory)
                moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
                gameOverChecker = RacingGameOverChecker(moveCalculator, gameState)
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

    private fun processGameString(gamestring: String): String {
        val board = gamestring
          .filter{ it != '/'}
          .replace(Regex("\\d")) { " ".repeat(it.value.toInt()) } 

        if (board.length != 64) throw Error("game string $gamestring produced $board board of incorrect size ${board.length}, when it should have been 64")
        return board
    }

    private fun processEnPassant(enPassant: String): Int {
        if (!enPassant.matches(Regex("[a-h][1-8]"))) return -1
        val x = enPassant[0].lowercase().toInt(16) % 10
        val y = 8 - enPassant[1].digitToInt()

        return y * 8 + x
    }
}