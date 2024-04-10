package me.isak.chess.model.versions.standard

import me.isak.chess.model.base.Piece
import me.isak.chess.model.base.PieceDirector

val pd = PieceDirector()

val standardPieceMap: Map<Char, Piece> = mapOf(
    'r' to pd.rook().buildPiece(),
    'R' to pd.rook().buildPiece(),
    'n' to pd.knight().buildPiece(),
    'N' to pd.knight().buildPiece(),
    'b' to pd.bishop().buildPiece(),
    'B' to pd.bishop().buildPiece(),
    'q' to pd.queen().buildPiece(),
    'Q' to pd.queen().buildPiece(),
    'k' to pd.bKing().buildPiece(),
    'K' to pd.wKing().buildPiece(),
    'P' to pd.wPawn().buildPiece(),
    'p' to pd.bPawn().buildPiece()
)

