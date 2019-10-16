scalaVersion := "2.12.10"
name := "chessmovedb-fetch"
organization := "com.github.lsund"
version := "1.0.0"

// Dependencies
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.8.0"
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"

val circeVersion = "0.12.2"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
