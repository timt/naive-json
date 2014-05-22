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

bintrayOrganization in bintray := None

publishMavenStyle := true

publishArtifact in Test := false

homepage := Some(url("https://github.com/timt/naive-json"))

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

pomExtra := (
    <scm>
      <url>git@github.com:timt/naive-json.git</url>
      <connection>scm:git:git@github.com:timt/naive-json.git</connection>
    </scm>
    <developers>
      <developer>
        <id>timt</id>
      </developer>
    </developers>
  )
