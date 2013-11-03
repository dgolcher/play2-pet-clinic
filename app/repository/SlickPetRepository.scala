package repository

import models.Pets
import models.Pet
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Types
import models.Type
import utils.QueryLogger.QueryUtil
import play.api.Logger

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
  
  def updatePet(id: Option[Int], pet: Pet) = executeInTransaction {
    Logger.info("DATA " + pet.id)
    Logger.info("DATA " + pet.name)
    Logger.info("DATA " + pet.birth)
    Logger.info("DATA " + pet.owner_id)
    Logger.info("DATA " + pet.type_id)
    val result = for {
      p <- Pets if p.id === id
    } yield p
    result.log
    result.update(pet)
    id.get
  }

}