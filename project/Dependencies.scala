import sbt._

object Dependencies {

  object V {
    val cats       = "2.10.0"
    val catsEffect = "3.5.1"
    val fs2        = "3.9.1"
    val http4s     = "0.23.18"
    val jwt        = "9.2.0"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.13.2"

    val munit = "0.7.29"
  }

  object Libraries {
    def http4s(artifact: String): ModuleID = "org.http4s" %% artifact % V.http4s

    val cats       = "org.typelevel"        %% "cats-core"   % V.cats
    val catsEffect = "org.typelevel"        %% "cats-effect" % V.catsEffect
    val fs2        = "co.fs2"               %% "fs2-core"    % V.fs2
    val jwtCore    = "com.github.jwt-scala" %% "jwt-core"    % V.jwt

    val http4sDsl    = http4s("http4s-dsl")
    val http4sServer = http4s("http4s-server")

    // Test
    val munit = "org.scalameta" %% "munit" % V.munit
  }

  object CompilerPlugins {
    val betterMonadicFor = compilerPlugin("com.olegpy" %% "better-monadic-for" % V.betterMonadicFor)
    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % V.kindProjector cross CrossVersion.full
    )
  }

}
