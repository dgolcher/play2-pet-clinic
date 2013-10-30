package repository

import models.Vet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Vets
import models.Vet

trait BaseRepository {

  private val driver = "org.h2.Driver";
  private val database = "jdbc:h2:mem:play"

  val db = Database.forURL(database, driver = driver)
  def executeInTransaction[T](f: => T) = {
    db.withTransaction(f)
  }

}