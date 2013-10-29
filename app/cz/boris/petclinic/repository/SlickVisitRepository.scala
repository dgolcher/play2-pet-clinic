package cz.boris.petclinic.repository

import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Visits
import cz.boris.petclinic.models.Visit
import cz.boris.petclinic.models.Pets

object SlickVisitRepository extends BaseRepository {

  def save(visit: Visit): Int = executeInTransaction(Visits.forInsert returning Visits.id insert visit)

  def findByPetId(id: Int): Vector[Visit] = executeInTransaction(Query(Visits).to[Vector].filter(_.pet_id == id))

}