scalaVersion := "2.13.1"
name := "hello-world"
organization := "ch.epfl.scala"
version := "1.0"

// Dependencies
libraryDependencies += "org.typelevel" %% "cats-core" % "2.0.0"
libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.8.0"

libraryDependencies += "com.github.lsund" % "pgnparser" % "1.0.0" from "file:///home/lsund/Documents/git/pgnparser/target/scala-2.13/pgnparser_2.13-1.0.0.jar"
