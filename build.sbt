ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "sensors-stat"
  )

assembly / assemblyJarName := "sensors-stat.jar"

libraryDependencies ++= Seq(
  "com.github.tototoshi" %% "scala-csv" % "1.3.10",
  "org.typelevel" %% "cats-core" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.2.11" % "test"
)
