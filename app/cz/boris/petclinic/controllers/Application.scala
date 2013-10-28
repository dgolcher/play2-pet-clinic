package cz.boris.petclinic.controllers

import play.api._
import play.api.mvc._
import cz.boris.petclinic.repository.SlickVetRepository

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
}