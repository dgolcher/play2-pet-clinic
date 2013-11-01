package models

import scala.slick.driver.H2Driver.simple._
import java.sql.Date
import scala.collection.mutable.ArrayBuffer

// Entities
case class Vet(id: Option[Int] = None, firstName: String, lastName: String) {
  var specialization = ArrayBuffer[String]()
}
case class Owner(id: Option[Int] = None, first: String, last: String, address: String, city: String, phone: String) {
  var pets = ArrayBuffer[Pet]()
}
case class Pet(id: Option[Int] = None, name: String, birth: Date, type_id: Option[Int], owner_id: Option[Int])
case class Visit(id: Option[Int] = None, date: Date, description: String, pet_id: Int)
case class Spec(id: Option[Int] = None, name: String)
case class Type(id: Option[Int] = None, name: String)
case class Specialty(vet_id: Option[Int], specialty_id: Option[Int])

//Mapping
object Vets extends Table[Vet]("VETS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def firstName = column[String]("FIRST_NAME")
  def lastName = column[String]("LAST_NAME")

  def * = id.? ~ firstName ~ lastName <> (Vet, Vet.unapply _)
  def forInsert = firstName ~ lastName <> ({ t => Vet(None, t._1, t._2) }, { (vet: Vet) => Some((vet.firstName, vet.lastName)) })
  def specs = VetToSpecs.filter(_.spec_id === id).flatMap(_.specFK)
}

object Pets extends Table[Pet]("PETS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  def birth = column[Date]("BIRTH_DATE")
  def type_id = column[Option[Int]]("TYPE_ID")
  def owner_id = column[Option[Int]]("OWNER_ID")
  
  def ownerFK = foreignKey("fk_pets_owners", owner_id, Owners)(_.id)
  def typeFK = foreignKey("fk_pets_types", type_id, Types)(_.id)

  def * = id.? ~ name ~ birth ~ type_id ~ owner_id <> (Pet, Pet.unapply _)
  def forInsert = name ~ birth ~ type_id ~ owner_id <> ({ t => Pet(None, t._1, t._2, t._3, t._4) }, { (pet: Pet) => Some((pet.name, pet.birth, pet.type_id, pet.owner_id)) })
}

object VetToSpecs extends Table[Specialty]("VET_SPECIALTIES") {
  def vet_id = column[Option[Int]]("VET_ID")
  def spec_id = column[Option[Int]]("SPECIALTY_ID")
  def * = vet_id ~ spec_id <> (Specialty, Specialty.unapply _)
  def vetFK = foreignKey("fk_vet_specialties_vets", vet_id, Vets)(vet => vet.id)
  def specFK = foreignKey("fk_vet_specialties_specialties", spec_id, Specialties)(spec => spec.id)
}

object Visits extends Table[Visit]("VISITS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def date = column[Date]("VISIT_DATE")
  def description = column[String]("DESCRIPTION")
  def pet_id = column[Int]("PET_ID")
  
  def petFK = foreignKey("fk_visits_pets", pet_id, Pets)(_.id)

  def * = id.? ~ date ~ description ~ pet_id <> (Visit, Visit.unapply _)
  def forInsert = date ~ description ~ pet_id <> ({ t => Visit(None, t._1, t._2, t._3) }, { (visit: Visit) => Some((visit.date, visit.description, visit.pet_id)) })
}

object Types extends Table[Type]("TYPES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")

  def * = id.? ~ name <> (Type, Type.unapply _)
}

object Specialties extends Table[Spec]("SPECIALTIES") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("NAME")
  
  def vets = VetToSpecs.filter(_.vet_id === id).flatMap(_.vetFK)

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
    { (owner: Owner) => Some((owner.first, owner.last, owner.address, owner.city, owner.phone)) })
}

// Helpers