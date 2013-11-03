package repository

import models.Pets
import models.Pet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Types
import models.Type

object SlickPetRepository extends BaseRepository {

  /**
   * Create new pet.
   */
  def save(pet: Pet): Int = executeInTransaction(Pets.forInsert returning Pets.id insert pet)

  /**
   * Find pet by id.
   */
  def findOne(id: Int): Pet = executeInTransaction(Query(Pets).filter(_.id === id).first)

  /**
   * Find all pet types.
   */
  def findTypesByPet(): Vector[Type] = executeInTransaction(Query(Types).to[Vector])

}