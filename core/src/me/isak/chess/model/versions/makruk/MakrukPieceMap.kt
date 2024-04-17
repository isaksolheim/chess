package me.isak.chess.model.versions.gounki

import me.isak.chess.model.base.Piece
import me.isak.chess.model.base.PieceBuilder
import me.isak.chess.model.base.PieceDirector

val pb = PieceBuilder()
val pd = PieceDirector()

// The king, rook, and knight move as in orthodox chess, except that castling does not exist in this game.
val king = pb.directions("NE", "E", "SE", "S", "SW", "W", "NW", "N").buildAction().buildPiece()
val rook = pd.rook().buildPiece()
val knight = pd.knight().buildPiece()

// The queen moves one square diagonally.
val queen = pb.directions("NE", "SE", "SW", "NW").buildAction().buildPiece()

// The bishop moves one square straight forward or one square diagonally.
val bishop = pb.directions("NE", "SE", "SW", "NW", "N").buildAction().buildPiece()

// The pawn moves as a normal pawn, but may not make a double step on its first move. When it reaches the sixth row, it promotes to a (Makruk) queen.
val wPawn = pb
  .id("P").directions("N").pathType("unoccupied").buildAction()
  .id("P").directions("NE", "NW").pathType("enemy").buildAction()
.buildPiece()

val bPawn = pb
  .id("p").directions("S").pathType("unoccupied").buildAction()
  .id("p").directions("SE", "SW").pathType("enemy").buildAction()
.buildPiece()

val makrukPieceMap: Map<Char, Piece> = mapOf(
    'r' to pd.rook().buildPiece(),
    'R' to pd.rook().buildPiece(),
    'n' to pd.knight().buildPiece(),
    'N' to pd.knight().buildPiece(),
    'b' to bishop,
    'B' to bishop,
    'q' to queen,
    'Q' to queen,
    'k' to king,
    'K' to king,
    'p' to bPawn,
    'P' to wPawn,
)