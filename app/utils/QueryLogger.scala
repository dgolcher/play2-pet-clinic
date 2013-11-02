package utils

import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import play.api.Logger

object QueryLogger {
  
  implicit class QueryUtil(val q: Query[_ <:Any,_ <:Any])  {
    def log = {
      Logger.info(q.selectStatement)
      q
    }
  }

}