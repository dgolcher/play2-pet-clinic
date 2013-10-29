package cz.boris.petclinic.repository

import cz.boris.petclinic.models.Pets
import cz.boris.petclinic.models.Pet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import cz.boris.petclinic.models.Types
import cz.boris.petclinic.models.Type

object SlickPetRepository extends BaseRepository {

  def save(pet: Pet): Int = executeInTransaction(Pets.forInsert returning Pets.id insert pet)

  def findOne(id: Int): Pet = executeInTransaction(Query(Pets).filter(_.id === id).first)

  def findTypesByPet(): Vector[Type] = executeInTransaction(Query(Types).to[Vector])

}