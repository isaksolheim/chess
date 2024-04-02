package me.isak.chess.versions.standard
import me.isak.chess.game.GameHistory
import me.isak.chess.game.GameOverChecker
import me.isak.chess.game.GameState
import me.isak.chess.move.MoveCalculator


class StandardGameOverChecker(val moveCalculator: MoveCalculator,val gameHistory: GameHistory) : GameOverChecker()
{
    var gameState : GameState? = null
    var turn : Boolean = false
    override fun gameOver(turn : Boolean) : Boolean
    {
        if (gameState == null) throw IllegalArgumentException("The variable GameState is currently null, set it to a value")

        this.turn = turn;

        //check if the game is over
        val gameOver : Boolean = checkForLegalMoves()
        if (!gameOver) return false;

        //Game is over find the winner
        SetWinner()
        return gameOver
    }

    //Checks for legal moves, if there a no legal moves the game is over
    private fun checkForLegalMoves() : Boolean
    {
        val piecesToCheck = mutableListOf<Int>()
        if (turn) {
            Regex("[A-Z]").findAll(gameState!!.getBoardAsString()).forEach {
                piecesToCheck.add(it.range.first)
            }
        }
        else {
            Regex("[a-z]").findAll(gameState!!.getBoardAsString()).forEach {
                piecesToCheck.add(it.range.first)
            }
        }

        piecesToCheck.forEach{square ->

            val legalMoves = moveCalculator.calculate(gameState!!.getBoard(), square) // Calculate all potentially legal moves
                .filter { gameState!!.checkGame(it) } // Filter away moves that are illegal for game specific reasons
                .filter { gameHistory.checkHistory(it) } // Filter away moves that are illegal for historic reasons

            if (!legalMoves.isEmpty())
                return false;
        }
        return true
    }


    private fun SetWinner() {
        if (!KingInCheck()) {
            endGame(Winner.Draw)
            return
        }
        if (turn) {
            endGame(Winner.Black)
        }
        else {
            endGame(Winner.White)
        }
    }

    private fun KingInCheck(): Boolean
    {
        val kingIndex = gameState!!.getBoard().indexOfFirst { square ->
            if (turn) {
                'K' == square
            } else {
                'k' == square
            }
        }
        // Find all squares the enemy cover, and check if the king is in it.
        return moveCalculator.calculateEnemyCover(gameState!!.getBoard(), turn)
            .any { m -> m.square == kingIndex }
    }
}
