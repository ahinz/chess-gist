import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.api.test.Helpers._

import models._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class ModelSpec extends Specification {
  val baseMap = Map(
    (0,0) -> Material(false, Rook),
    (0,3) -> Material(false, King),
    (0,4) -> Material(false, Queen),
    (0,7) -> Material(false, Bishop),

    (1,7) -> Material(false, Pawn),

    (3,0) -> Material(false, Pawn),
    (3,1) -> Material(true, Pawn),

    (7,0) -> Material(true, Bishop),
    (7,2) -> Material(true, Queen),
    (7,6) -> Material(true, King))

  "Chess Fen Parser" should {

    "be able to parse a FEN position" in {
      val pos = ChessGist.parseFenPosition("r2kq2b/7p/8/pP6/8/8/8/B1Q3K1")

      pos must equalTo(baseMap)
    }

    "be able to parse a fen castling stanza" in {
      ChessGist.parseFenCastling("kqKQ") must equalTo(Castling(true, true, true, true))
    }

    "be able to parse a full fen string" in {
      val fen = ChessGist.parseFen("r2kq2b/7p/8/pP6/8/8/8/B1Q3K1 w Qk - 0 1")

      fen must equalTo(Position(baseMap, "w", Castling(false, true, true, false), None, 0, 1))
    }
  }
}
