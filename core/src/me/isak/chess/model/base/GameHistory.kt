package me.isak.chess.model.base

/**
 * Interface defining what each version of chess requires to handle game history.
 * We separate between state and history for a game of chess.
 * State is what the board currently looks like. It has no memory of 
 * previous moves or occurances. This is the job of GameHistory.
 * 
 * An example from standard chess is the right to castle. After a king
 * has moved, he loses the right to castle forever. En passant can only 
 * be done if the pawn moved forward in the previous move. 
 */
interface GameHistory {

    /**
     * A method for filtering down generated moves
     * to only allow those that do not break rules based on 
     * history.
     * @param move to check for legality.
     * @return True if no historic rule is broken.
     */
    fun checkHistory(move: Move): Boolean

    /**
     * Change the history values after a move
     * has been executed.
     * @param move that has been executed.
     */
    fun changeHistory(move: Move): Unit

}