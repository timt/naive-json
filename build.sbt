import scala.util.Try
import bintray.Keys._

name := "naive-json"

organization := "io.shaka"

version := Try(sys.env("LIB_VERSION")).getOrElse("1")

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.10" % "2.1.4" % "test"
)

bintrayPublishSettings

repository in bintray := "repo"

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

bintrayOrganization in bintray := None
