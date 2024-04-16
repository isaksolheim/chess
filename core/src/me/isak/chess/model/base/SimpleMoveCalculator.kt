package me.isak.chess.model.base

enum class CalculationType { PATH, COVER }

/**
 * Responsible for calculation information for the current board.
 * It is "simple", because it does not take into account the game state or game history.
 * Responsible for calculating two things: what a piece can "see" (read: cover) and where a piece can move.
 * These two things are the same for most pieces, but there are exceptions. A pawn covers the squares diagonally in front
 * but cannot necessarily move there. It can move forward, but does not cover these squares. This separation is made
 * because some moves require a cover condition to be met. Castle for example cannot be done if the enemy covers certain squares.
*/
class SimpleMoveCalculator(private val pieceMap: PieceMap, private val version: String) {

  private val offsetMap = mapOf(
    'E' to 1,
    'S' to 8,
    'W' to -1,
    'N' to -8,
  )

  /**
   * The main workhorse of the whole application.
   * This generates all moves that a piece can possibly make.
   * For each move, it also creates what the board will look like,
   * should the move be executed.
   * @param board to analyse
   * @param startSquare of the piece to find moves for
   * @returns a list of moves that the piece can make
   */
  fun calculateSimpleMoves(board: Array<Char>, startSquare: Int): List<Move> {

    val pieceSymbol = board[startSquare]
    val piece = pieceMap.getPiece(pieceSymbol)

    return piece
      .filter { boardCheck(it, board, startSquare) }
      .flatMap { action ->
        action.directions.flatMap { direction ->
          generateSquares(action, board, startSquare, direction, CalculationType.PATH)
            .map { square ->
              createMove(action, board, startSquare, square)
            }
        }
      }
  }

  /**
   * Calculates all the squares the pieces can see. This is very similar
   * to calculateSimpleMoves, but with a few key differences.
   * 1. Uses the cover regex to validate if a square should be included.
   * 3. No move generation is needed.
   * @param board
   * @param startSquare
   */
  fun calculatePieceCover(board: Array<Char>, startSquare: Int): List<Int> {

    val pieceSymbol = board[startSquare]
    val piece = pieceMap.getPiece(pieceSymbol)

    return piece
      .filter { action -> boardCheck(action, board, startSquare) }
      .flatMap { action ->
        action.directions.flatMap { direction ->
          generateSquares(action, board, startSquare, direction, CalculationType.COVER)
        }
      }
  }

  /**
   * Finds every single square that the white or black pieces cover.
   * @param board to analyse
   * @param teamWhite boolean to specify which team to find cover for
   * @returns an array of indexes for all the covered squares
   */
  fun calculateTeamCover(board: Array<Char>, teamWhite: Boolean): List<Int> {
    return board.indices
      .filter { board[it] != ' ' } // remove empty squares
      .filter { board[it].isUpperCase() == teamWhite } // remove pieces of the opposite team
      .flatMap { calculatePieceCover(board, it) }
  }

  /**
   * Figure out if the king is in check.
   * @param board to analyse.
   * @param boolean true to see if white is in check, false to look for 
   * black king.
   */
  fun isKingInCheck(board: Array<Char>, whiteKing: Boolean): Boolean {
    return when (version) {
      "standard" -> standardIsKingInCheck(board, whiteKing)
      else -> generalIsKingInCheck(board, whiteKing)
    }
  }

  /**
   * General method for figuring out if the king is in check. If we
   * do not know in advance how all the pieces move, we have to figure it out here.
   * Figure out all the squares covered by the enemy (expensive calculation). 
   * Then return true if king is standing on one of them.
   */
  private fun generalIsKingInCheck(board: Array<Char>, whiteKing: Boolean): Boolean {

    // Find where the king is, or return false (not in check if he does not exist).
    val kingSymbol = if (whiteKing) 'K' else 'k'
    val kingSquare = board.indices
      .find{ board[it] == kingSymbol }
      ?: return false

    // return true if the king square is covered
    return calculateTeamCover(board, !whiteKing)
      .any { coveredSquare -> coveredSquare == kingSquare }
  }


  /**
   * This is a helper "piece" used to determine if the king is in check
   * Can only be used with standard pieces (important!), because then we know all the
   * ways our king can be attacked. This "piece" is a reverse engineered version of 
   * these attack vectors.
   */
  private val kingCoverPiece = PieceBuilder()
    .directions("NE", "NW").cover(Regex("Kp")).buildAction() // white king checked by black pawn
    .directions("SE", "SW").cover(Regex("kP")).buildAction() // black king checked by white pawn
    .directions("NNE", "EEN", "EES", "SSE", "SSW", "WWS", "WWN", "NNW").cover(Regex("kN|Kn")).buildAction() // king checked by enemy knight
    .directions("E", "W", "S", "N").cover(Regex("^(K\\s*[qr]?|k\\s*[QR]?)$")).buildAction() // king checked on horizontal/verical by queen or rook
    .directions("NE", "SE", "SW", "NW").cover(Regex("^(K\\s*[qb]?|k\\s*[QB]?)$")).buildAction() // king checked on diagonal by queen or bishop
  .buildPiece()

  /**
   * This method can only be used in a game using the standard pieces. 
   * When we know the behaviour of all the pieces, we can make some shortcuts when it comes
   * to finding out if the king is in check. The kingCoverPiece is used to find all the relevant squares
   * of enemies. In this function, we are trying to find a square with a piece on it.
   * If we are able to do so, it means that this piece is attacking the king!
   */
  private fun standardIsKingInCheck(board: Array<Char>, whiteKing: Boolean): Boolean {

    // Find where the king is, or return false (not in check if he does not exist).
    val kingSymbol = if (whiteKing) 'K' else 'k'
    val kingIndex = board.indices
      .find{ board[it] == kingSymbol }
      ?: return false

    // Return true if we are able to discover a piece that is 
    // covering the king
    return kingCoverPiece
      .flatMap{action ->
        action.directions.flatMap{direction ->
          generateSquares(action, board, kingIndex, direction, CalculationType.COVER)
        }
      }
      .any{ board[it] != ' '}
  }


  /**
   * Calculate the absolute offset as described by a direction string.
   */
  fun parseDirection(direction: String): Int {
    return direction.sumOf { offsetMap[it] ?: 0 }
  }

  /**
   * Generate either moves or cover that can be reached from startSquare.
   *
   * A path is described using east, south, west, north. A number represents how
   * many squares to walk in that direction. Multiple directions can make up a single path.
   *
   * Examples of how paths are processed:
   * E -> [1]
   * EE -> [2]
   * E1E1 -> [1, 2]
   * E* -> [1, 2, 3, 4, 5, 6, 7]
   *
   * NNE -> [-15]
   * NNE* -> [-15, -30, ...]
   *
   * E3S3W3 -> [1, 2, 3, -5, -13, -21, -22, -23, -24]
   *
   * These numbers are absolute offsets, which are added to the starting square.
   * This allows us to make sure the path does not walk off the board.
   * After having been constructed, the class is iterable over the result (queue).
   */
  private fun generateSquares(
    action: Action,
    board: Array<Char>,
    startSquare: Int,
    path: String,
    type: CalculationType
  ): List<Int> {
    val squares: MutableList<Int> = mutableListOf()
    val directionRegex = Regex("([ESWN]+)([\\d\\*]*)")

    var currentSquare = startSquare
    var currentPath = board[startSquare].toString()

    for (match in directionRegex.findAll(path)) {
      val (directionString, repetitionString) = match.destructured

      val offset = parseDirection(directionString)
      val repetitions = parseRepetitions(repetitionString)

      repeat(repetitions) {
        if (!isInBounds(currentSquare, directionString)) return squares

        currentSquare += offset
        currentPath += board[currentSquare]

        if (
          (type == CalculationType.PATH && !action.path.matches(currentPath)) ||
          (type == CalculationType.COVER && !action.cover.matches(currentPath))
        ) return squares
        squares.add(currentSquare)
      }
    }
    return squares
  }

  /**
   * Finds out how many times we are going in one direction.
   */
  private fun parseRepetitions(repetitions: String): Int {
    if (repetitions == "") return 7
    return repetitions.toIntOrNull() ?: 0
  }

  /**
   * To find out if a move is going out of bounds, we have to be careful.
   * It is not possible to use absolute offset, because the same offset might
   * be the result of different directions. This is the result of storing the board
   * as an array. For example, the offset of WWWWWWW (-7) has the same absolute offset as
   * NE, but they will never both be legal.
   *
   * For this reason, we have to break the direction into components, looking at
   * the movement in y and x direction. This will allow us to see if the move
   * takes us out of bounds.
   */
  private fun isInBounds(startSquare: Int, direction: String): Boolean {
    var x = startSquare % 8
    var y = startSquare / 8
    var dx = direction.count { it == 'E' } - direction.count { it == 'W' }
    var dy = direction.count { it == 'S' } - direction.count { it == 'N' }

    return x + dx in 0..7 && y + dy in 0..7
  }

  /**
   * Verify that the action follows condition specified
   * by the boardCondition.
   */
  private fun boardCheck(action: Action, board: Array<Char>, startSquare: Int): Boolean {
    if (action.boardCondition == null) return true

    val boardCopy = board.clone()
    boardCopy[startSquare] = 'I'
    val boardString = boardCopy.joinToString("")

    return action.boardCondition.find(boardString) != null
  }

  /**
   * When a move has been confirmed to be legal, package it in
   * the Move dataclass. This includes the landing square,
   * the resulting board should the move be played, and the id inherited
   * from the action.
   */
  private fun createMove(
    action: Action,
    board: Array<Char>,
    startSquare: Int,
    endSquare: Int
  ): Move {

    // Generate resulting board
    var result: String
    val boardCopy = board.clone()

    if (action.replacement == null || action.boardCondition == null) {
      // without specialized replacement, just place the piece on the new square
      boardCopy[endSquare] = board[startSquare];
      boardCopy[startSquare] = ' ';
      result = boardCopy.joinToString("");
    } else {
      // Specialized replacement need to use the character I instead of the piece, otherwise matching will not work.
      boardCopy[startSquare] = 'I';

      // make the specialized replacement (like castle or en passant)
      result = boardCopy.joinToString("").replace(action.boardCondition, action.replacement);
    }

    return Move(endSquare, result, action.id)
  }
}