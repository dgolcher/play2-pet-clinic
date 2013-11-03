package controllers

import play.api.mvc.Controller
import play.api.mvc.Action
import utils.AllForms._
import repository.SlickPetRepository
import play.api.mvc.Flash
import play.api.Logger

object PetController extends Controller {
  
  def newPetForm(id: Int) = Action { implicit request =>
    val types = SlickPetRepository.findTypesByPet
    Ok(views.html.petNew(id, petForm, types))
  }
  
  def newPet(id: Int) = Action { implicit request =>
    val pet = petForm.bindFromRequest
    pet.fold(
      hasErrors = { data =>
        Redirect(routes.PetController.newPetForm(id)).flashing(Flash(data.data) + ("error" -> "Field should not be empty"))
      },
      success = { data => 
        SlickPetRepository.save(data)
        Redirect(routes.OwnerController.owner(id))
      }
    )
  }
  
  def editPet(id: Int, pid: Int) = Action { implicit request =>
    val pet = SlickPetRepository.findOne(pid)
    val types = SlickPetRepository.findTypesByPet
    Ok(views.html.petEdit(id, pid, petForm.fill(pet), types))
  }
  
  def updatePet(id: Int, pid: Int) = Action { implicit request =>
  	val pet = petForm.bindFromRequest
    pet.fold(
      hasErrors = { data =>
        Redirect(routes.PetController.editPet(id, pid)).flashing(Flash(data.data) + ("error" -> "Field should not be empty"))
      },
      success = { data => 
        val result = SlickPetRepository.updatePet(Some(pid), data)
        Redirect(routes.OwnerController.owner(id))
      }
    )
  }

}