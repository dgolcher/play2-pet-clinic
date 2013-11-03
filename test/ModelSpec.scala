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
import models.Owner
import models.Pet
import java.sql.Date

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

    "insert owner to database" in {
      running(FakeApplication()) {
        val repo = SlickOwnerRepository
        val owner = Owner(None, "John", "Doe", "Main Street 26", "New York", "125125125")
        val id = repo.save(owner)
        id must equalTo(11)
        val john = repo.findOne(id)
        john.first must equalTo("John")
      }
    }

    "return pets for owner" in {
      running(FakeApplication()) {
        val repo = SlickOwnerRepository
        val owners = repo.findByLastName("Estab")
        owners(0).pets.size must equalTo(2)
      }
    }
  }

  "Pet model" should {

    "return all types" in {
      running(FakeApplication()) {
        val repo = SlickPetRepository
        val types = repo.findTypesByPet
        types.size must equalTo(6)
      }
    }

    "save pet to database" in {
      running(FakeApplication()) {
        val repo = SlickPetRepository
        val pet = Pet(None, "Pinky", Date.valueOf("2012-08-06"), Some(1), Some(1))
        val id = repo.save(pet)
        id must equalTo(14)
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