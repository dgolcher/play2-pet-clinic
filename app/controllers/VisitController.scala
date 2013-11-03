package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import utils.AllForms._
import play.api.mvc.Flash
import repository.SlickPetRepository
import play.api.Logger
import repository.SlickVisitRepository

object VisitController extends Controller {
  
  def newVisitForm(id: Int, pid: Int) = Action { implicit request =>
  	Ok(views.html.visitNew(id, pid, visitForm))
  }
  
  def newVisit(id: Int, pid: Int) = Action { implicit request =>
    val visit = visitForm.bindFromRequest
    visit.fold(
      hasErrors = { data =>
        Redirect(routes.VisitController.newVisitForm(id, pid)).flashing(Flash(data.data) + ("error" -> "Field should not be empty"))
      },
      success = { data => 
        SlickVisitRepository.save(data)
        Redirect(routes.OwnerController.owner(id))
      }
    )
  }

}