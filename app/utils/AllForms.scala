package utils

import play.api.data.Form
import play.api.data.Form
import play.api.data.Forms.{ mapping, text, number, ignored, nonEmptyText, optional, sqlDate }
import java.util.TimeZone
import models.Owner
import models.Visit
import models.Pet

case class Search(name: String)

object AllForms {
  
  val datePattern = "yyyy-MM-dd"
  
  val petForm = Form(mapping(
    "id" -> optional(number),
    "name" -> nonEmptyText,
    "birth" -> sqlDate(datePattern, TimeZone.getDefault()),
    "type_id" -> optional(number),
    "owner_id" -> optional(number))(Pet.apply)(Pet.unapply))

  val visitForm = Form(mapping(
    "id" -> optional(number),
    "date" -> sqlDate(datePattern, TimeZone.getDefault()),
    "description" -> nonEmptyText,
    "pet_id" -> optional(number))(Visit.apply)(Visit.unapply))

  val ownerForm = Form(mapping(
    "id" -> optional(number),
    "first" -> nonEmptyText,
    "last" -> nonEmptyText,
    "address" -> nonEmptyText,
    "city" -> text,
    "phone" -> nonEmptyText)(Owner.apply)(Owner.unapply))

  val searchForm = Form(mapping("name" -> text)(Search.apply)(Search.unapply))

}