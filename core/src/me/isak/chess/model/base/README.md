
# Chess base

This folder includes the code for the model part of the application. This code is separated from code found in model/versions/*, because they should not change when creating a new version of chess.

The one exception to this is SimpleGameFactory, which needs to add logic for the creation of a new version.

Here is a diagram showing how all the classes in this folder works together.

```mermaid
---
title: Chess Core Model
---
classDiagram
    class Action {
        +id: String
        +directions: List~String~
        +path: Regex
        +cover: Regex
        +boardCondition: Regex?
        +replacement: String?
    }


    class Piece {
        -List~Action~
        +iterator() Iterator~Action~
    }

    class PieceDirector {
        +rook() PieceBuilder
        +...()
    }

    class PieceBuilder {
        +id(id: String) PieceBuilder
        +...()
    }



    class PieceMap {
        +get(piece: Char) Piece 
    }

    class SimpleMoveCalculator {
        +calculateSimpleMoves(board, startSquare) List~Move~
        +calculatePieceCover(board, startSquare) List~Int~
        +calculateTeamCover(board, team) List~Int~
        +isKingInCheck(board, team) Boolean
        +parseDirection(direction) String
    }

    class GameState {
        +checkState(move: Move) Boolean
        +changeState(move: Move)
    }

    class GameHistory {
        +checkHistory(move: Move) Boolean
        +changeHistory(move: Move)
    }

    class MoveExecutor {
        +execute(legalMoves, square): Array~Char~?
    }

    class MoveCalculator {
        +legalMoves(square: Int): List~Move~
    }

    class GameOverChecker {
        +checkGameOver() Boolean
    }

    class SimpleGameFactory {
        +create(version: String) FactoryResult
    }

    class Game {
        +click(square: Int): List~Move~
    }

    Piece --> Action
    PieceBuilder --> Piece
    PieceDirector --> Piece
    PieceDirector --> PieceBuilder
    PieceMap --> PieceDirector
    PieceMap --> PieceBuilder
    SimpleMoveCalculator --> PieceMap
    GameState --> SimpleMoveCalculator
    MoveExecutor --> SimpleMoveCalculator
    MoveExecutor --> GameState
    MoveExecutor --> GameHistory
    MoveCalculator --> GameHistory
    MoveCalculator --> GameState
    GameOverChecker --> MoveCalculator
    SimpleGameFactory --> MoveExecutor
    SimpleGameFactory --> MoveCalculator
    SimpleGameFactory --> GameOverChecker
    Game --> SimpleGameFactory
```