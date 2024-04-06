package me.isak.chess.move

import me.isak.chess.move.PieceBuilder

/**
 * Since most versions of chess use the same pieces, 
 * this class will help build them. 
 * Each method returns a piecebuilder with all standard actions completed.
 * The piece is not built, because some chess versions might require
 * small modifications to the piece before building.
 */
class PieceDirector {

    fun rook(): PieceBuilder {
        return PieceBuilder()
            .directions("E", "S", "W", "N").type("ranger").buildAction()
    }

    fun knight(): PieceBuilder {
        return PieceBuilder()
            .directions("NNE", "EEN", "EES", "SSE", "SSW", "WWS", "WWN", "NNW").buildAction()
    }

    fun bishop(): PieceBuilder {
        return PieceBuilder()
            .directions("NE", "SE", "SW", "NW").type("ranger").buildAction()
    }

    fun queen(): PieceBuilder {
        return PieceBuilder()
            .directions("NE", "E",  "SE", "S", "SW", "W", "NW", "N").type("ranger").buildAction()
      }
    
    fun wKing(): PieceBuilder {
        return PieceBuilder()
            .directions("NE", "E", "SE", "S", "SW", "W", "NW", "N").id("wk").buildAction()
            .directions("EE").boardCondition(Regex("I  R$")).replacement(" RK ").coverType("never").id("K").buildAction()
            .directions("WW").boardCondition(Regex("R   I(...)$")).replacement("  KR $1").coverType("never").id("Q").buildAction()
      }
    
    fun bKing(): PieceBuilder {
        return PieceBuilder()
            .directions("NE", "E", "SE", "S", "SW", "W", "NW", "N").id("bk").buildAction()
            .directions("EE").boardCondition(Regex("^(....)I  r")).replacement("$1 rk ").coverType("never").id("k").buildAction()
            .directions("WW").boardCondition(Regex("^r   I")).replacement("  kr ").coverType("never").id("q").buildAction()
      }
    
    fun wPawn(): PieceBuilder {
        return PieceBuilder()
            .directions("NE", "NW").pathType("enemy").id("P").buildAction()
            .directions("N").pathType("unoccupied").coverType("never").id("P").buildAction()
            .directions("NN").pathType("unoccupied").coverType("never").boardCondition(Regex(" .{7}I.{8,15}$")).id("wPawnDoubleForward").buildAction()
            .directions("NE").pathType("unoccupied").coverType("never").boardCondition(Regex(" (.{6})Ip(.{32,38})$")).replacement("P$1  $2").id("enPassant").buildAction()
            .directions("NW").pathType("unoccupied").coverType("never").boardCondition(Regex(" (.{7})pI(.{32,38})$")).replacement("P$1  $2").id("enPassant").buildAction()
        
      }
    
    fun bPawn(): PieceBuilder {
        return PieceBuilder()
            .directions("SE", "SW").pathType("enemy").id("p").buildAction()
            .directions("S").pathType("unoccupied").coverType("never").id("p").buildAction()
            .directions("SS").pathType("unoccupied").coverType("never").boardCondition(Regex("^.{8,15}I.{7} ")).id("bPawnDoubleForward").buildAction()
            .directions("SE").pathType("unoccupied").coverType("never").boardCondition(Regex("^(.{32,38})IP(.{7}) ")).replacement("$1  $2p").id("enPassant").buildAction()
            .directions("NW").pathType("unoccupied").coverType("never").boardCondition(Regex("^(.{32,38})PI(.{6}) ")).replacement("$1  $2p").id("enPassant").buildAction()
      }
    }