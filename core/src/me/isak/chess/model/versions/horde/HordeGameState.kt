package me.isak.chess.model.versions.horde

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.versions.standard.StandardGameState

val default = "rnbqkbnrpppppppp         PP  PP PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP"
val testttt = "rnbqkbnrpppppppp                       P                        "

class HordeGameState(moveCalculator: SimpleMoveCalculator) : StandardGameState(moveCalculator) {

    init {
        setBoardAsString(testttt)
    }
}