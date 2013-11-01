package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import repository.SlickOwnerRepository
import play.api.data.Form
import play.api.data.Forms.{ mapping, text }

case class Search(name: String)

object OwnerController extends Controller {

  def search = Action { implicit request =>
    Ok(views.html.findOwners())
  }

  def list = Action { implicit request =>
    val name = searchForm.bindFromRequest()
    name.fold(
      hasErrors => BadRequest,
      success = { filter =>
        val all = SlickOwnerRepository.findByLastName(filter.name)
        Ok(views.html.owners(all))
      })
  }

  val searchForm = Form(mapping("name" -> text)(Search.apply)(Search.unapply))
}