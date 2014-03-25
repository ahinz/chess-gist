package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

sealed trait MaterialType { val symbol: String }
case object Rook extends MaterialType { val symbol = "r" }
case object Knight extends MaterialType { val symbol = "n" }
case object Bishop extends MaterialType { val symbol = "b" }
case object King extends MaterialType { val symbol = "k" }
case object Queen extends MaterialType { val symbol = "q" }
case object Pawn extends MaterialType { val symbol = "p" }

case class Castling(
  whiteQueenside: Boolean, whiteKingside: Boolean,
  blackQueenside: Boolean, blackKingside: Boolean)

case class Material(white: Boolean, mtype: MaterialType)
case class Position(
  location: Map[(Int, Int), Material], activeColor: String, castling: Castling,
  enpassantLoc: Option[(Int, Int)], halfmove: Int, fullmove: Int)

case class ChessGist(id: Long, label: String, fen: String) {
  lazy val position: Position = ChessGist.parseFen(fen)
}

object ChessGist {

  def parseFen(fen: String): Position = {
    fen.split(" ").toList match {
      case List(pos, active, castling, enpassant, halfmove, fullmove) =>
        Position(parseFenPosition(pos), active, parseFenCastling(castling),
          None, Integer.parseInt(halfmove), Integer.parseInt(fullmove))
      case _ => throw new Exception("Invalid FEN")
    }
  }

  def parseFenCastling(castling: String) =
    Castling(
      castling.contains("q"), castling.contains("k"),
      castling.contains("Q"), castling.contains("K"))

  val symbolToMaterial = Map(
    'r' -> Material(false, Rook),
    'n' -> Material(false, Knight),
    'b' -> Material(false, Bishop),
    'k' -> Material(false, King),
    'q' -> Material(false, Queen),
    'p' -> Material(false, Pawn),
    'R' -> Material(true, Rook),
    'N' -> Material(true, Knight),
    'B' -> Material(true, Bishop),
    'K' -> Material(true, King),
    'Q' -> Material(true, Queen),
    'P' -> Material(true, Pawn))

  def parseFenPosition(fenPos: String): Map[(Int,Int), Material] =
    fenPos.foldLeft((Map[(Int,Int),Material](), 0, 0)){ (context, char) => context match {
      case (map, rank, file) =>
        symbolToMaterial.get(char) match {
          case Some(material) => (map + ((rank, file) -> material), rank, file + 1)
          case _ if char == '/' => (map, rank + 1, 0)
          case _ => (map, rank, file + Integer.parseInt(char + ""))
        }
    }}._1

  val chessGist = {
    get[Long]("id") ~
    get[String]("label") ~
    get[String]("fen") map {
      case id~label~fen => ChessGist(id, label, fen)
    }
  }

  def all(): List[ChessGist] = DB.withConnection { implicit c =>
    SQL("select * from chessgist").as(chessGist *)
  }

  def create(label: String, fen: String) {
    DB.withConnection { implicit c =>
      SQL("insert into chessgist (label, fen) values ({label}, {fen})").on(
        'label -> label,
        'fen -> fen
      ).executeUpdate()
    }
  }

  def delete(id: Long) {}
}
