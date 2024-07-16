val scala3Version = "3.4.2"
val scalaTestVersion = "3.2.19"

lazy val root = (project in file("."))
  .aggregate(core, zio)

lazy val core = (project in file("core"))
  .settings(
    name := "core",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-json" % "0.3.0",
      "org.scalameta" %% "munit" % "1.0.0" % Test,
      "org.scalatest" %% "scalatest-flatspec" % scalaTestVersion % Test
    )
  )

lazy val zio = (project in file("zio"))
  .dependsOn(core)
  .settings(
    name := "zio",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "1.0.12",
      "dev.zio" %% "zio-http" % "3.0.0-RC9"
    ),
  )
