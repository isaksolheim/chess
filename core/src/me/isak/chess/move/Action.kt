package me.isak.chess.move

/**
 * A description of the behaviour of a piece. Each piece may have multiple actions.
 * @property id: used to reference special actions for checks. For example enPassant is an id used to ensure that action is possible at the right time.
 * @property directions: the ways a piece can move. 
 * @property path: regex describing the path a piece is allowed to make.
 * @property cover: regex describing how the piece covers squares.
 * @property boardCondition: regex defining what the board must look like to allow a move. Ex: pawn must be on start rank for double move
 * @property replacement: string defining how the board should update, if more than two squares are involved. Ex: en passant and castle, which moves multiple pieces.
 */
data class Action (
    val id: String,
    val directions: List<String>,
    val path: Regex,
    val cover: Regex,
    val boardCondition: Regex?,
    val replacement: String?,
)