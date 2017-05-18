import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.revunit",
      scalaVersion := "2.12.1"
    )),
    name := "HangOut",
    libraryDependencies ++= Seq(scalaTest % Test)
  )
