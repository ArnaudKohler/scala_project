val scala3Version = "3.4.2"
val scalaTestVersion = "3.2.19"

lazy val core = project
  .in(file("core"))
  .settings(
    name := "scala_project",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
    libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % scalaTestVersion % "test"
  )

lazy val zio = (project in file("zio"))
  .settings(
    name := "zio",
    scalaVersion := scala3Version,
    libraryDependencies += "dev.zio" %% "zio" % "1.0.12"
  )