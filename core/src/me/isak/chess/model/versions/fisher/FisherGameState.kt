package me.isak.chess.model.versions.fisher

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.base.GameOverChecker
import me.isak.chess.model.versions.standard.StandardGameState
import kotlin.random.Random

class FisherGameState(moveCalculator: SimpleMoveCalculator, fen: String) 
    : StandardGameState(moveCalculator, fen) {

    // Overwrite the fen string by creating a random starting position
    init {
        setBoardAsString(initBoard())
    }


    /**
     * Prepare the fisher random (chess960) board. Rules:
     * 1. Pawns are placed normally.
     * 2. Remaining black pieces are placed on the top rank.
     * 3. King is placed between the rooks.
     * 4. Bishops are placed on opposite colours.
     * 5. The black pieces mirror the white.
     */
    private fun initBoard() : String {
        val setup = Array(8) { "" }

        val king = Random.nextInt(1, 7)
        setup[king] = "k"

        val rook1 = Random.nextInt(0, king)
        setup[rook1] = "r"

        val rook2 = Random.nextInt(king + 1, 8)
        setup[rook2] = "r"

        while (true) {
            val evenBishop = Random.nextInt(0, 4) * 2
            if (setup[evenBishop].isNotEmpty()) continue

            setup[evenBishop] = "b"
            break
        }

        while (true) {
            val oddBishop = Random.nextInt(0, 4) * 2 + 1
            if (setup[oddBishop].isNotEmpty()) continue

            setup[oddBishop] = "b"
            break
        }

        val queen = Random.nextInt(0, 3)
        var i = 0
        for ((index, item) in setup.withIndex()) {
            if (item.isNotEmpty()) continue

            setup[index] = if (queen == i) "q" else "n"
            i++
        }

        return setup.joinToString("") + super.board.substring(8, 56) + setup.joinToString("") { it.uppercase() }
    }
}