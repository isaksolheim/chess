package me.isak.chess.viewmodels


import me.isak.chess.Chess
import me.isak.chess.model.base.Game
import me.isak.chess.model.base.Move
import me.isak.chess.model.base.GameResult
import me.isak.chess.sound.SoundController


class GameViewModel(private val game: Game, private val app: Chess) {
    private val soundController = SoundController.getInstance()

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

    fun getOnlineStatus(): Boolean {
        if (game.firebaseGameModel !== null) {
            return true
        }
        return false
    }

    fun getGameId(): String {
        if (game.firebaseGameModel != null) {
            return game.firebaseGameModel!!.id
        }
        return game.id
    }

    fun getGameVersion(): String {
        if (game.firebaseGameModel != null) {
            return game.getGameVersion()
        } // Needs review: previously defaulted to game.id (why?)
        return game.getGameVersion()
    }

    fun getLegalMoves(): List<Move> {
        return game.getLegalMoves()
    }

    fun checkGameOver(): GameResult {
        return game.checkGameOver()
    }

    fun getCurrentPlayer(): String {
        return game.getCurrentTurn()
    }

    fun getPlayerColor(): String {
        return game.player
    }

    fun playSounds(clickResult: Game.ClickResult)
    {
        if (clickResult.isKingInCheck)
        {
            soundController.playGameSoundEffect(SoundController.GameSounds.Check)
            return
        }
        if (clickResult.capture)
        {
            soundController.playGameSoundEffect(SoundController.GameSounds.Capture)
            return
        }
        if (clickResult.hasMoved)
        {
            soundController.playGameSoundEffect(SoundController.GameSounds.Move)
            return
        }
        soundController.playGameSoundEffect(SoundController.GameSounds.Click)
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
                val clickResult = game.click(square)

                playSounds(clickResult)
                onBoardChanged?.invoke(game.getBoard())
                selectedSquare = null

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
                    val clickResult = game.click(square)
                    playSounds(clickResult)
                    onLegalMovesChanged?.invoke(game.getLegalMoves())
                }
            }

            onBoardChanged?.invoke(game.getBoard())
        }
    }
}
