import sbt._

object Dependencies {

  object Versions {
    val cats       = "2.0.0"
    val catsEffect = "2.0.0"
    val fs2        = "2.0.1"
    val http4s     = "0.21.0-M5"
    val jwt        = "4.2.0"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.10.3"

    val scalaTest = "3.0.8"
  }

  object Libraries {
    def http4s(artifact: String): ModuleID = "org.http4s" %% artifact % Versions.http4s

    lazy val cats       = "org.typelevel" %% "cats-core"   % Versions.cats
    lazy val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect
    lazy val fs2        = "co.fs2"        %% "fs2-core"    % Versions.fs2
    lazy val jwtCore    = "com.pauldijou" %% "jwt-core"    % Versions.jwt

    lazy val http4sDsl    = http4s("http4s-dsl")
    lazy val http4sServer = http4s("http4s-blaze-server")

    // Compiler plugins
    lazy val betterMonadicFor = "com.olegpy"    %% "better-monadic-for" % Versions.betterMonadicFor
    lazy val kindProjector    = "org.typelevel" %% "kind-projector"     % Versions.kindProjector

    // Test
    lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest
  }

}
