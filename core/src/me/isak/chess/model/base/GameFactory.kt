package me.isak.chess.model.base

import me.isak.chess.model.versions.standard.StandardPieceMap
import me.isak.chess.model.versions.standard.StandardGameState
import me.isak.chess.model.versions.standard.StandardGameHistory
import me.isak.chess.model.versions.standard.StandardGameOverChecker

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
            // add move version here later
            throw Error("Incorrect version $version provided to GameFactory.create")
        }
        val moveExecutor = MoveExecutor(gameState, gameHistory)

        return FactoryResult(moveCalculator, moveExecutor, gameOverChecker, gameState, gameHistory)
    }
}