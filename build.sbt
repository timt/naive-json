name := "json"

organization := "io.shaka"

version := "0.1"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.10" % "2.1.4" % "test"
)

publishTo <<= (version) { version: String =>
  val github = "/Users/timt/Projects/timt.github.com/repo/"
  if (version.trim.endsWith("SNAPSHOT")) Some(Resolver.file("file",  new File( github + "snapshots/")))
  else                                   Some(Resolver.file("file",  new File( github + "releases/")))
}