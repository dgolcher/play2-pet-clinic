package repository

import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Visits
import models.Visit
import models.Pets
import scala.collection.mutable.ArrayBuffer

object SlickVisitRepository extends BaseRepository {

  /**
   * Create new visit.
   */
  def save(visit: Visit): Int = executeInTransaction(Visits.forInsert returning Visits.id insert visit)

  /**
   * Find visits by pet id.
   */
  def findByPetId(id: Int): ArrayBuffer[Visit] = executeInTransaction(Query(Visits).to[ArrayBuffer].filter(_.pet_id.get == id))

}