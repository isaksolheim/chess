package me.isak.chess

import me.isak.chess.game.Game


fun main() {
    
    val game = Game("standard")

    game.click(51)
    game.click(35)

    game.click(1)
    game.click(16)

    game.click(60)
    game.click(51)

}
