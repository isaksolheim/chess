package me.isak.chess.game

import me.isak.chess.versions.standard.StandardGameState
import me.isak.chess.versions.standard.StandardGameHistory
import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.PieceMap
import me.isak.chess.move.MoveExecutor
import me.isak.chess.move.SimpleMoveCalculator
import me.isak.chess.game.GameHistory
import me.isak.chess.versions.standard.StandardGameOverChecker
import me.isak.chess.versions.standard.StandardPieceMap

data class FactoryResult(
    val moveCalculator: MoveCalculator, 
    val moveExecutor: MoveExecutor, 
    val gameOverChecker: GameOverChecker,
    val gameState: GameState,
    val gameHistory: GameHistory
)

class GameFactory {

    fun create(version: String): FactoryResult {
        val pieceMap: PieceMap
        val gameState: GameState
        val gameHistory: GameHistory
        val simpleMoveCalculator: SimpleMoveCalculator

        val moveCalculator: MoveCalculator
        val gameOverChecker: GameOverChecker
        
        if (version == "standard") {
            pieceMap = StandardPieceMap()
            simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
            gameState = StandardGameState(simpleMoveCalculator)
            gameHistory = StandardGameHistory()
            moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
            gameOverChecker = StandardGameOverChecker(moveCalculator, gameState)
        } else {   
            throw Error("Incorrect version $version provided to GameFactory.create")
        }
        val moveExecutor = MoveExecutor(gameState, gameHistory)

        return FactoryResult(moveCalculator, moveExecutor, gameOverChecker, gameState, gameHistory)
    }
}