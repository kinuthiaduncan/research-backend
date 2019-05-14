import spray.revolver.RevolverPlugin._

name := """ria_data"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += evolutions

libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.1" % Test

libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
libraryDependencies +=  "mysql" % "mysql-connector-java" % "5.1.37"
libraryDependencies +=  "com.pauldijou" %% "jwt-play" % "2.1.0"
libraryDependencies +=  "com.github.t3hnar" %% "scala-bcrypt" % "4.0"
//libraryDependencies += "org.postgresql" % "postgresql" % "42.2.1"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-Xfatal-warnings"
)

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
  "Typesafe simple" at "http://repo.typesafe.com/typesafe/simple/maven-releases/"
)

Revolver.settings