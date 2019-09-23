import Dependencies._

ThisBuild / scalaVersion     := "2.13.0"
ThisBuild / version          := "0.0.1"
ThisBuild / organization     := "dev.profunktor"
ThisBuild / organizationName := "ProfunKtor"

lazy val root = (project in file("."))
  .settings(
    name := "http4s-jwt-auth",
    scalacOptions += "-Ymacro-annotations",
    libraryDependencies ++= Seq(
      compilerPlugin(Libraries.kindProjector),
      compilerPlugin(Libraries.betterMonadicFor),
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.fs2,
      Libraries.http4sDsl,
      Libraries.http4sServer,
      Libraries.jwtCore,
      Libraries.log4cats,
      Libraries.newtype,
      Libraries.scalaTest      % Test,
      Libraries.scalaCheck     % Test
    )
  )
