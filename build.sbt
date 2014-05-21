import scala.util.Try

name := "naive-json"

organization := "io.shaka"

version := Try(sys.env("LIB_VERSION")).getOrElse("1")

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.10" % "2.1.4" % "test"
)

publishTo <<= version { version: String =>
  val github = "./publish/"
  if (version.trim.endsWith("SNAPSHOT")) Some(Resolver.file("file",  new File( github + "snapshots/")))
  else                                   Some(Resolver.file("file",  new File( github + "releases/")))
}