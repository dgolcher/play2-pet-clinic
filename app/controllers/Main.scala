package controllers

import play.api._
import play.api.mvc._
import java.lang.management.ManagementFactory
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Promise
import play.api.libs.iteratee.{ Enumerator, Iteratee }
import play.api.mvc.{ Action, Controller, WebSocket }
import scala.language.postfixOps
import java.util.concurrent.TimeUnit

object Main extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  def error = Action {
    val a = None
    a.get
    Ok(views.html.index("Your new application is ready."))
  }
  
  def statusPage() = Action { implicit request =>
    Ok(views.html.status(request))
  }
  
  def status() = WebSocket.using[String] { implicit request =>
    def getLoad = {
      "1.2f".format(ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage())
    }
    
    val in = Iteratee.ignore[String]
    val out = Enumerator.repeatM(Promise.timeout(getLoad, 3, TimeUnit.SECONDS))
    
    (in, out)
  }
  
}