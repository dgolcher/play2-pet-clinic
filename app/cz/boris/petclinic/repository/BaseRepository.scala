package cz.boris.petclinic.repository

import cz.boris.petclinic.models.Vet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Vets
import cz.boris.petclinic.models.Vet

trait BaseRepository {
  
  private val driver = "org.h2.Driver";
  private val database = "jdbc:h2:mem:play"

  val db = Database.forURL(database, driver = driver)
  def executeInTransaction[T](f: => T) = {
    db.withTransaction(f)
  }

}