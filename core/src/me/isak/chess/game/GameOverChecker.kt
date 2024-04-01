package me.isak.chess.game

import me.isak.chess.move.MoveCalculator

abstract class GameOverChecker()
{
    public enum class Winner{White,Black,Draw}

    var winner : Winner? = null
    abstract fun gameOver(turn: Boolean) : Boolean
    protected fun Endgame()
    {
        //The game has come to an end goto end game screen

        if (winner == null) throw IllegalArgumentException("Winner is set to null")

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


