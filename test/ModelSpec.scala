package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Vets
import cz.boris.petclinic.models.Vet
import cz.boris.petclinic.repository.SlickVetRepository
import cz.boris.petclinic.repository.SlickVetRepository
import cz.boris.petclinic.repository.SlickVisitRepository
import cz.boris.petclinic.repository.SlickOwnerRepository
import cz.boris.petclinic.repository.SlickPetRepository

class ModelSpec extends Specification {
  
  "Visits model" should {

    "return visit by pet id" in {
      running(FakeApplication()) {
        val repo = SlickVisitRepository
        val visits = repo.findByPetId(8)
        visits.size must equalTo(2)
      }
    }
  }
  
  "Owner model" should {

    "return owners by last name" in {
      running(FakeApplication()) {
        val repo = SlickOwnerRepository
        val owners = repo.findByLastName("Davis")
        owners.size must equalTo(2)
      }
    }
  }
  
  "Pet model" should {

    "return types by pet" in {
      running(FakeApplication()) {
        val repo = SlickPetRepository
        val types = repo.findTypesByPet
        types.size must equalTo(6)
      }
    }
  }

  "Vets model" should {

    "get all vets from database" in {
      running(FakeApplication()) {
        val repo = SlickVetRepository
        val vets = repo.allVets
        vets.size must equalTo(6)
      }
    }
    
    "insert vet into db" in {
      running(FakeApplication()) {
        val repo = SlickVetRepository
        val id = repo.save(Vet(None, "John", "Doe"))
        id must equalTo(7)
      }
    }
    
    "find vet by id" in {
      running(FakeApplication()) {
        val repo = SlickVetRepository
        val vet = repo.findOne(1)
        vet.firstName must equalTo("James")
      }
    }
  }

}