package me.isak.chess.game

import me.isak.chess.move.MoveCalculator

abstract class GameOverChecker()
{
    public enum class Winner{White,Black,Draw}

    abstract fun gameOver(turn: Boolean) : Boolean
    protected fun endGame(winner : Winner)
    {
        //The game has come to an end goto end game screen
        if (winner == Winner.Draw)
        {
            println("Draw!")
            return
        }
        if (winner == Winner.Black)
        {
            println("Black Won!")
            return
        }

        println("White Won!")


    }
}


