package me.isak.chess.model.versions.horde

import me.isak.chess.model.base.SimpleMoveCalculator
import me.isak.chess.model.versions.standard.StandardGameState

class HordeGameState(moveCalculator: SimpleMoveCalculator) : StandardGameState(moveCalculator) {

    init {
        setBoardAsString("rnbqkbnrpppppppp         PP  PP PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP")
    }
}