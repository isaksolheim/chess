package me.isak.chess.versions.standard

import me.isak.chess.game.GameState
import me.isak.chess.move.MoveCalculator
import me.isak.chess.move.Move


class StandardGameState(moveCalculator: MoveCalculator): GameState(moveCalculator) {
    override fun executeMove(move: Move): Array<Char> {
        turn = !turn
        board = move.result
    
        val boardList = move.result.toCharArray().toMutableList()
    
        // Promote pawn should it reach the final rank
        val square = move.square
        val tag = move.tag
        if (tag == "P" && square / 8 == 0) {
            boardList[square] = 'Q'
        }
    
        if (tag == "p" && square / 8 == 7) {
            boardList[square] = 'q'
        }
    
        /* NEED TO ALSO CHECK FOR GAME OVER AFTER EXECUTING A MOVE !!! */
        if (GameOver(true)) {
        //WE DID THE CHECKMATE
        }


        return boardList.toTypedArray()
    }


    fun GameOver(WhitesTurn : Boolean) : Boolean
    {
        val piecesToCheck = mutableListOf<Int>()


        if (WhitesTurn) {
            Regex("[a-z]").findAll(getBoard().toString()).forEach {
                piecesToCheck.add(it.range.first)
            }
        }
        else {
            Regex("[A-Z]").findAll(getBoard().toString()).forEach {
                piecesToCheck.add(it.range.first)
            }
        }
        piecesToCheck.forEach{square ->

            val legalMoves = moveCalculator.calculate(getBoard(), square) // Calculate all potentially legal moves
            .filter { checkGame(it) } // Filter away moves that are illegal for game specific reasons
            //.filter { gameHistory.checkHistory(it) } // Filter away moves that are illegal for historic reasons

            if (legalMoves.isEmpty())
                return false;
            }
        //Ingen lovlige trekk funnet
        return true;
    }


    override fun checkGame(move: Move): Boolean {
        if (isKingInCheck(move)) return false
    
        if (illegalCastle(move)) return false
    
        return true
    }

    override fun toString(): String {
        return if (turn) "w" else "b"
    }

}