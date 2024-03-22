package me.isak.chess.move

interface MovesetMap {
    
    fun getMoveset(piece: Char): Array<Moveset>

}