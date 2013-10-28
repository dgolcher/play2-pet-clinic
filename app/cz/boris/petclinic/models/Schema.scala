package cz.boris.petclinic.models

import scala.slick.driver.H2Driver.simple._
import java.util.Date

case class Vet(id: Option[Int] = None, firstName: String, lastName: String)
case class Owner(id: Option[Int] = None)
case class Pet(id: Option[Int] = None, name: String)
case class Visit(id: Option[Int] = None)
case class Spec(id: Option[Int] = None)

object Vets extends Table[Vet]("VETS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")

  def * = id.? ~ firstName ~ lastName <> (Vet, Vet.unapply _)
  def forInsert = firstName ~ lastName <> ({ t => Vet(None, t._1, t._2) }, { (vet: Vet) => Some((vet.firstName, vet.lastName)) })
}

object Pets extends Table[Pet]("PETS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def * = id.? ~ name <> (Pet, Pet.unapply _)
}


