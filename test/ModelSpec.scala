package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Vets
import cz.boris.petclinic.models.Vet

class ModelSpec extends Specification {

  "Vets model" should {

    "get data from db" in {
      running(FakeApplication()) {
        Database.forURL("jdbc:h2:mem:play", driver="org.h2.Driver") withSession {
          val q = Query(Vets)
          q.first.id.get must equalTo(1)
        }
      }
    }
    
    "insert vet into db" in {
      running(FakeApplication()) {
        Database.forURL("jdbc:h2:mem:play", driver="org.h2.Driver") withSession {
          val id = Vets.forInsert returning Vets.id insert Vet(None, "John", "Doe")
          id must equalTo(7)
        }
      }
    }
  }

}