package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class ChessGist(id: Long, label: String, fen: String)

object ChessGist {

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
