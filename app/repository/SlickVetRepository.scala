package repository

import models.Vet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Vets
import models.Vet
import models.Spec
import models.Spec
import models.Specialties
import models.VetToSpecs
import play.api.Logger
import scala.collection.mutable.ArrayBuffer

object SlickVetRepository extends BaseRepository {

  /**
   * Find all veterinarians.
   */
  def allVets(): ArrayBuffer[Vet] = executeInTransaction {
    val vets = Query(Vets).to[ArrayBuffer]
    val specs = Query(Specialties).to[Vector]
    val ids = Query(VetToSpecs).to[Vector]
    val result = for {
      v <- vets
      s <- specs
      i <- ids if i.vet_id == v.id && i.specialty_id == s.id 
    } yield {
      v.specialization += s.name
    }
    vets.sortBy(_.firstName)
  }

  /**
   * Find vet by id.
   */
  def findOne(id: Int): Vet = executeInTransaction(Query(Vets).filter(_.id === id).first)

  def save(vet: Vet): Int = executeInTransaction(Vets.forInsert returning Vets.id insert vet)

}