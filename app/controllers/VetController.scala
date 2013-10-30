package controllers
import play.api.mvc.Action
import play.api.mvc.Controller
import repository.SlickVetRepository
import models.Spec

object VetController extends Controller {
  
  def showVetList = Action { implicit request =>
    val vets = SlickVetRepository.allVets
    Ok(views.html.vets(vets))
  }

}