package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import repository.SlickOwnerRepository
import play.api.mvc.Flash
import play.api.Logger
import java.util.TimeZone
import utils.AllForms._

object OwnerController extends Controller {
  
  def search = Action { implicit request =>
    Ok(views.html.findOwners())
  }

  def owner(id: Int) = Action { implicit request =>
    val owner = SlickOwnerRepository.findOne(id)
    Ok(views.html.owner(owner))
  }

  def list = Action { implicit request =>
    val name = searchForm.bindFromRequest
    name.fold(
      hasErrors => BadRequest,
      success = { filter =>
        val all = SlickOwnerRepository.findByLastName(filter.name)
        Ok(views.html.owners(all))
      })
  }

  def newOwnerForm = Action { implicit request =>
    Ok(views.html.ownerNew(ownerForm))
  }

  def newOwner = Action { implicit request =>
    val owner = ownerForm.bindFromRequest
    owner.fold(
      hasErrors = { data =>
        Redirect(routes.OwnerController.newOwnerForm).flashing(Flash(data.data) + ("error" -> "Field should not be empty"))
      },
      success = { data =>
        val id = SlickOwnerRepository.save(data)
        Redirect(routes.OwnerController.owner(id))
      })
  }

  def editOwner(id: Int) = Action { implicit request =>
    val owner = SlickOwnerRepository.findOne(id)
    Ok(views.html.ownerEdit(owner.id.get, ownerForm.fill(owner)))
  }

  def updateOwner(id: Int) = Action { implicit request =>
    val owner = ownerForm.bindFromRequest
    owner.fold(
      hasErrors = { data =>
        Redirect(routes.OwnerController.editOwner(id)).flashing(Flash(data.data) + ("error" -> "Field should not be empty"))
      },
      success = { data =>
        val result = SlickOwnerRepository.updateOwner(Some(id), data)
        Redirect(routes.OwnerController.owner(result))
      })
  }
}