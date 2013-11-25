import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName = "pet-clinic"
  val appVersion = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    "com.typesafe.slick" %% "slick" % "1.0.0",
    "com.h2database" % "h2" % "1.3.166",
    "com.mchange" % "c3p0" % "0.9.2.1")

  val main = play.Project(appName, appVersion, appDependencies).settings( // Add your own project settings here      
  )

}
