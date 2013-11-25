package facade

import repository.SlickVetRepository
import models.Pet
import models.Visit
import models.Owner

/**
 * Not in use yet.
 */
trait PetClinicFacade {

  def findPetTypes()

  def findOwnerById(id: Int)

  def findPetById(id: Int)

  def savePet(pet: Pet)

  def saveVisit(visit: Visit)

  def findVets()

  def saveOwner(owner: Owner)

  def findOwnerByLastName(name: String)

}