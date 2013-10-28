package cz.boris.petclinic.repository

import cz.boris.petclinic.models.Vet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Vets
import cz.boris.petclinic.models.Vet

object SlickVetRepository extends BaseRepository {

  def allVets(): List[Vet] = executeInTransaction(Query(Vets).list)

  def findOne(id: Int): Vet = executeInTransaction(Query(Vets).filter(_.id === id).first)

  def save(vet: Vet): Int = executeInTransaction(Vets.forInsert returning Vets.id insert vet)
}