package repository

import models.Owner
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Owner
import models.Owners
import models.Owner
import models.Pets
import scala.collection.mutable.ArrayBuffer

object SlickOwnerRepository extends BaseRepository {

  def findOne(id: Int): Owner = executeInTransaction(Query(Owners).filter(_.id === id).first)

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
    owners
  }

  def save(owner: Owner): Int = executeInTransaction(Owners.forInsert returning Owners.id insert owner)

}