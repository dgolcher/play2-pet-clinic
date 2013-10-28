package cz.boris.petclinic.models

import scala.slick.driver.H2Driver.simple._
import java.sql.Date

// Entities
case class Vet(id: Option[Int] = None, firstName: String, lastName: String)
case class Owner(id: Option[Int] = None, first: String, last: String, address: String, city: String, phone: String)
case class Pet(id: Option[Int] = None, name: String, birth: Date)
case class Visit(id: Option[Int] = None, date: Date, description: String)
case class Spec(id: Option[Int] = None, name: String)
case class Type(id: Option[Int] = None, name: String)

//Mapping
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
  def birth = column[Date]("BIRTH_DATE")

  def * = id.? ~ name ~ birth <> (Pet, Pet.unapply _)
  def forInsert = name ~ birth <> ({ t => Pet(None, t._1, t._2) }, { (pet: Pet) => Some((pet.name, pet.birth)) })
}

object Visits extends Table[Visit]("VISITS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def date = column[Date]("VISIT_DATE")
  def description = column[String]("DESCRIPTION")

  def * = id.? ~ date ~ description <> (Visit, Visit.unapply _)
  def forInsert = date ~ description <> ({ t => Visit(None, t._1, t._2) }, { (visit: Visit) => Some((visit.date, visit.description)) })
}

object Types extends Table[Type]("TYPES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")

  def * = id.? ~ name <> (Type, Type.unapply _)
}

object Specialties extends Table[Spec]("SPECIALTIES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")

  def * = id.? ~ name <> (Spec, Spec.unapply _)
}

object Owners extends Table[Owner]("OWNERS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def first = column[String]("FIRST_NAME")
  def last = column[String]("LAST_NAME")
  def address = column[String]("ADDRESS")
  def city = column[String]("CITY")
  def phone = column[String]("TELEPHONE")

  def * = id.? ~ first ~ last ~ address ~ city ~ phone <> (Owner, Owner.unapply _)
  def forInsert = first ~ last ~ address ~ city ~ phone <> (
      { t => Owner(None, t._1, t._2, t._3, t._4, t._5) },
      { (owner: Owner) => Some((owner.first, owner.last, owner.address, owner.city, owner.phone)) }
  )
}

// Helpers