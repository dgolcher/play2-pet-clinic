package repository

import models.Owner
import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import models.Owner
import models.Owners
import models.Owner

object SlickOwnerRepository extends BaseRepository {

  def findOne(id: Int): Owner = executeInTransaction(Query(Owners).filter(_.id === id).first)

  def findByLastName(name: String): Vector[Owner] = executeInTransaction(Query(Owners).filter(_.last.toLowerCase like name.toLowerCase + '%').to[Vector])

  def save(owner: Owner): Int = executeInTransaction(Owners.forInsert returning Owners.id insert owner)

}