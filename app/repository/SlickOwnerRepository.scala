package repository

import scala.collection.mutable.ArrayBuffer
import scala.slick.driver.H2Driver.simple.Database.threadLocalSession
import scala.slick.driver.H2Driver.simple.Query
import scala.slick.driver.H2Driver.simple.columnBaseToInsertInvoker
import scala.slick.driver.H2Driver.simple.columnExtensionMethods
import scala.slick.driver.H2Driver.simple.productQueryToUpdateInvoker
import scala.slick.driver.H2Driver.simple.queryToQueryInvoker
import scala.slick.driver.H2Driver.simple.stringColumnExtensionMethods
import scala.slick.driver.H2Driver.simple.tableToQuery
import scala.slick.driver.H2Driver.simple.valueToConstColumn

import models.Owner
import models.Owners
import models.Pets
import models.Types
import play.api.Logger
import utils.QueryLogger.QueryUtil

object SlickOwnerRepository extends BaseRepository {

  /**
   * Find Owner by id.
   */
  def findOne(id: Int): Owner = executeInTransaction {
    val owner = Query(Owners).filter(_.id === id).first
    val pets = Query(Pets).to[ArrayBuffer]
    val types = Query(Types).to[ArrayBuffer]
    for {
      pet <- pets
      typ <- types 
      if pet.owner_id == owner.id && typ.id == pet.type_id
    } yield {
      pet.petType = typ.name
      owner.pets += pet
    }
    for {
      pet <- owner.pets
    } yield {
    	val visits = SlickVisitRepository.findByPetId(pet.id.get)
    	val result = for {
    	  v <- visits if v.pet_id.get == pet.id.get
    	} yield {
    	  v
    	}
    	pet.visits ++= result
    }
    owner
  }

  /**
   * Find owner by last name.
   */
  def findByLastName(name: String): ArrayBuffer[Owner] = executeInTransaction {
    val owners = Query(Owners).filter(_.last.toLowerCase like name.toLowerCase + '%').to[ArrayBuffer]
    val pets = Query(Pets).to[Vector]
    for {
      o <- owners
      p <- pets
      if o.id == p.owner_id
    } yield {
      o.pets += p
    }
    owners.sortBy(_.first)
  }

  /**
   * Create new owner.
   */
  def save(owner: Owner): Int = executeInTransaction(Owners.forInsert returning Owners.id insert owner)

  /**
   * Update existing owner.
   */
  def updateOwner(id: Option[Int], owner: Owner): Int = {
    executeInTransaction {
      val result = for {
        o <- Owners if o.id === id
      } yield o
      result.log
      result.update(owner)
      id.get
    }
  }

}