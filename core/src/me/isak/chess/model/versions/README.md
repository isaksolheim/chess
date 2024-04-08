# How to create a new version of chess

This document will take you through the steps required to make a new version of chess. Summary:

1. Design pieces.
2. Create a mapping between piece symbols and pieces.
3. Make GameHistory, GameState, GameOverChecker classes.
4. Update SimpleGameFactory to include the new version.

## 0. Setup

Create a new folder under /versions/ with a name representing your new version. This is not strictly necessary, but useful to keep things organized.

## 1. Design the behaviour of your pieces

To be able to do this, you first need to understand what an Action is. 

### 1.1 What are actions?

Some pieces have one action, while others have multiple. An action describes how the piece moves, and the conditions that must be met to allow this movement.

A standard rook has one action. It can move horizontally and vertically. The conditions are that it continues in one direction until blocked by an obstacle. If the obstacle is a friend, it blocks further movement. If the obstacle is an enemy, it is included in the movement, but it can go no further.

A standard pawn is a piece with multiple actions, because it has movements with different conditions. A forward move is only allowed if the square is empty, while a diagonal (attack) move is only allowed if there is an enemy occupying the square. These are therefore separate actions.

The properties of an action are as follows:
1. id
2. directions
3. path
4. cover
5. boardCondition (optional)
6. replacement (optional)

#### 1.1.1 Id

The id field can be any string, and is used if the action requires that other parts of the program can access it. For most actions, the id can be just an empty string.  How special ids are used will be explained later. But just as an example, the action "castle" requires an id because the conditions it must satisfy are too complex to be expressed in the action definition.

#### 1.1.2 Directions

Directions is a list of ways a piece can move. These directions are described using cardinal directions: N (north), E, S, and W. These can be combined to create a more complicated direction. Examples of directions: 
- RookDirections = ["N", "E", "S", "W"]
- KnightDirections = ["NNE", "EEN", "EES", "SSE", "SSW", "WWS", "WWN", "NNW"]

It is also possible to describe directions that change over time. If you wanted a move that goes around in a square, you can do something like this:

- ["E3S3W3N2"]

|   	|   	|   	|   	|
|---	|---	|---	|---	|
| -> 	| x 	| x 	| x 	|
| x 	|   	|   	| x 	|
| x 	|   	|   	| x 	|
| x 	| x 	| x 	| x 	|

If you wanted to "jump" to the corners, and not walk on the squares between, you could do this:

- ["EEE1SSS1WWW1"]

|   	|   	|   	|   	|
|---	|---	|---	|---	|
| -> 	|  . 	|  . 	| x 	|
|   	|   	|   	|  . 	|
|   	|   	|   	|  . 	|
| x 	|  . 	|   .	| x 	|


#### 1.1.3 Path

The way a piece walks in a direction depend on rules for the piece, and what the board looks like. A rook takes many steps in one direction until it meets and obstacle, but the knight stops after having walked one step. The Path attribute is used to distinguish these types of walks. When calculating legal moves, the calculator combines all the visited squares in a walk into a string. This string is then tested against the Path attribute to determine if the piece is allowed to move further. 

More precisely, the path attribute is a regex defining legal paths for a piece. Let's look at some examples:

- WhiteKnightPattern = N[a-z ] 

Explanation: the path of a white knight can have a length of 1 (plus the starting square). The end square might have an emeny piece (lower case symbol), or be empty. 

- WhiteRookPattern = R\s*[a-z]?

Explanation: a white rook can visit any number of empty squares (\s*) and possibly include one enemy ([a-z]). After meeting the first enemy, no more characters can be added to the path-string.

This might seem like a very complicated way to do things. Using regex is difficult to design and debug, but it is very powerful compared to other alternatives. With regex based pathing, there are fewer limits on what is possible. This decision was made because we wanted the most flexible/extendable design possible. 

#### 1.1.4 Cover 

This attribute works exactly the same way that the path attribute does. It is needed because the cover of a piece is not the same as where it is allowed to move. The most common example is that pieces can cover friends, but not capture them.  Some moves do not count as cover at all, like a pawn forward move. The reason we need both path and cover, is because some situations require knowledge of what a piece covers. To be allowed to castle for example, the enemy must not cover the squares next to the king.  

#### 1.1.5 Board condition (optional)

Most actions do not have a board condition. For those that do, define the board condition by making a regex describing what the board must look like, in order for the action to be legal. For example, the action allowing a white pawn to move two squares forward is only allowed when the pawn is on the second rank. En passant requires the white pawn to be on the fifth rank, with a black pawn next to it. Castle is allowed when king and rook is on the home square, with no piece between.

#### 1.1.6 replacement (optional)

Most actions describe a piece moving from one square to another. Only two squares are affected. These types of actions do not require the replacement attribute. But some actions describe a change that affect more than two squares. En passant for example, change 3 squares, and castle change 4.

The replacement attribute is therefore meant to describe multiple changed squares. It works together with the board condition attribute to figure out how the board updates. Here is an example of how white castle kingside is checked and updated:

- boardCondition = "I  R$"
- replacement = " RK "

Explanation: "I" symbolizes the selected piece (which is the king in this case). The board condition is passed if the king has two emtpy squares next to it, followed by a rook. The replacement takes the condition, and replaces it with " RK ", completing the castle action.

Board condition and replacement is by far the most complicated thing to design. Sorry about that. But luckily it is almost never needed.

### 1.2 Piece builders and other tools

Clearly, designing actions from scratch is too much work, especially since most of them are very similar. For that reason, the PieceBuilder class exist. This class follows the builder pattern, which allows efficient construction of a piece (and its actions).

#### 1.2.1 PieceBuilder

Using the PieceBuilder is done by first building all its actions, and then building the piece. Here is an example:

```kotlin
val pieceBuilder = PieceBuilder()

val customPiece = piecebuilder()
    .id("action1").directions("E", "S", "NNE").type("leaper").type.buildAction()
    .id("action2").directions("W").type("ranger").buildAction()
  .buildPiece()
```

The .type(...) is used to create pieces with common path and cover attributes.
Leaper is a piece jumping directly to one square, like a knight or king. Rangers are pieces that move like rooks, bishops and queens. Using this will reduce the ammount of regex you need to write. The movement of the custom piece (p) looks like this:

|   	|   	|   	|   	|   	|   	|   	|
|---	|---	|---	|---	|---	|---	|---	|
|   	|   	|   	|  .	|   x	|   	|   	|
|   	|   	|   	|   .	|   	|   	|   	|
|   x	|  x 	|   x	| p 	|   x	|   	|   	|
|   	|   	|   	|   x	|   	|   	|   	|
|   	|   	|   	|   	|   	|   	|   	|


#### 1.2.2 PieceDirector

The piece builder is a great help, but we can do even better. Since most versions of chess use the same pieces as standard chess, there is no need to design them every time. The PieceDirector has predefined methods for all the standard pieces. Note that it does not build the piece, but instead returns a piece builder with all the actions of the chosen piece. This makes it very easy to extend the standard pieces! 

```kotlin
val pieceDirector = PieceDirector() 

val customRook = pieceDirector.rook()
    .directions("NE").type("ranger").buildAction()
  .buildPiece()
```

The example shows how to make a rook that can also move diagonally in the top right direction.

## 2. Create mapping between symbols and pieces

The chessboard is represented as an array of characters. The empty char (' ') represents an emtpy square, uppercase characters are white pieces and lowercase characters are black pieces. Each version of chess requires a PieceMap, that tells the game engine how to find the behaviour of a piece based on the piece character. 

```kotlin
val customPieceMap: Map<Char, Piece> = mapOf(
    'r' to customRook,
    ...
)
```

it is possible to use any letter of the alphabet, (except special character 'I'), as long as they correspond to the board string. 

## 3. Make GameHistory, GameState and GameOverChecker

GameHistory and GameState are needed to keep track of information about the game, and to enforce special rules. More to come here soon.

## 4. Update SimpleGameFactory to include the new version

To allow your new version to be played, the game factory must be able to 
create the objects. Add a new entry in the SimpleGameFactory init switch statement. It will look something like this: 

```kotlin
"custom" -> {
    pieceMap = PieceMap(customPieceMap)
    simpleMoveCalculator = SimpleMoveCalculator(pieceMap)
    gameState = CustomGameState(simpleMoveCalculator)
    gameHistory = CustomGameHistory()
    moveExecutor = MoveExecutor(gameState, gameHistory)
    moveCalculator = MoveCalculator(simpleMoveCalculator, gameState, gameHistory)
    gameOverChecker = CustomGameOverChecker(moveCalculator, gameState)
}

```