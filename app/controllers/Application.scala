package controllers

import models._

import play.api._
import play.api.db._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  val gistForm = Form(
    tuple(
      "label" -> nonEmptyText,
      "fen" -> nonEmptyText))


  def index = Action {
    Redirect(routes.Application.gists)
  }

  def gists = Action {
    Ok(views.html.index(ChessGist.all(), gistForm))
  }

  def gist(id: Long) = TODO

  def newGist = Action { implicit request =>
    gistForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(ChessGist.all(), errors)),
      elements => elements match {
        case (label, fen) => {
          ChessGist.create(label, fen)
          Redirect(routes.Application.gists)
        }
      })
  }

}
