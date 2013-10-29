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