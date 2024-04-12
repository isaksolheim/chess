package me.isak.chess.viewmodels


import me.isak.chess.Chess
import me.isak.chess.game.Game
import me.isak.chess.move.Move
import me.isak.chess.sound.SoundController


class GameViewModel(private val game: Game, private val app: Chess) {
    val soundController = SoundController.getInstance()

    var onBoardChanged: ((Array<Char>) -> Unit)? = null
    var onLegalMovesChanged: ((List<Move>) -> Unit)? = null
    private var selectedSquare: Int? = null

    init {
        onBoardChanged?.invoke(game.getBoard())
        onLegalMovesChanged?.invoke(game.getLegalMoves())
    }

    fun getBoard(): Array<Char> {
        return game.getBoard()
    }

    fun getGameId(): String {
        if (game.firebaseGameModel != null) {
            return game.firebaseGameModel!!.id
        }
        return game.id
    }

    fun getLegalMoves(): List<Move> {
        return game.getLegalMoves()
    }

    fun checkGameOver(): Boolean {
        return game.checkGameOver()
    }

    /**
     * Processes a user's move attempt by either executing the move or selecting a piece.
     *
     * For a selected square, it attempts to move the previously selected piece to this new location.
     * If the game is online, updates including the move are pushed to Firebase, given the move is valid.
     *
     * If no piece is selected (selectedSquare is null), it sets the current square as selected if the move is valid.
     * Valid moves depend on the game state (online/offline) and, for online games, whether the piece belongs to the player
     * and matches the player's turn.
     *
     * @param square The index of the board square selected by the user, ranging from 0 to 63.
     */
    fun onUserMove(square: Int) {
        if (!game.isOnline || (game.getCurrentTurn() == game.player)) {
            selectedSquare?.let {
                game.click(square)
                onBoardChanged?.invoke(game.getBoard())
                selectedSquare = null
                soundController.playGameSoundEffect(SoundController.GameSounds.Click)

                // TODO: Only call this if move was actually done
                if (game.isOnline) {
                    val boardAsString = game.getBoardAsString()
                    game.firebaseGameModel?.board = boardAsString
                    game.firebaseGameModel?.currentTurn = game.getCurrentTurn()
                    app.firebase.pushValue(game.id, game.firebaseGameModel!!)
                }
            } ?: run {
                if (!game.isOnline || game.getPieceColorAtSquare(square) == game.player) {
                    selectedSquare = square
                    game.click(square)
                    onLegalMovesChanged?.invoke(game.getLegalMoves())
                }
            }

            onBoardChanged?.invoke(game.getBoard())
        }
    }
}
