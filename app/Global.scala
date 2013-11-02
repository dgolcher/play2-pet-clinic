import play.api._
import play.api.mvc._
import play.api.mvc.Results._

object Global extends GlobalSettings {
  
  override def onError(request: RequestHeader, ex: Throwable) = {
    InternalServerError(views.html.error())
  }

}