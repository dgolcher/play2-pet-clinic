package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Vets
import models.Vet
import repository.SlickVetRepository
import repository.SlickVetRepository
import repository.SlickVisitRepository
import repository.SlickOwnerRepository
import repository.SlickPetRepository

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
        val owners = repo.findByLastName("Dav")
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
    
    "get all spec for vet" in {
      running(FakeApplication()) {
        val repo = SlickVetRepository
        val q = repo.allVets
        q(3).specialization.size must equalTo(2)
      }
    }
    
    "insert vet into db" in {
      running(FakeApplication()) {
        val repo = SlickVetRepository
        val id = repo.save(Vet(None, "John", "Doe"))
        id must equalTo(7)
        repo.allVets.size must equalTo(7)
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