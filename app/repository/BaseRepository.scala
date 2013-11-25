package repository

import scala.slick.driver.H2Driver.simple._
import Database.threadLocalSession
import com.mchange.v2.c3p0.ComboPooledDataSource

trait BaseRepository {

  private val driver = "org.h2.Driver";
  private val database = "jdbc:h2:mem:play"

  val db = {
    val dataSource = new ComboPooledDataSource
    dataSource.setDriverClass(driver)
    dataSource.setJdbcUrl(database)
    Database.forDataSource(dataSource)
  }
  def executeInTransaction[T](f: => T) = {
    db.withTransaction(f)
  }

}