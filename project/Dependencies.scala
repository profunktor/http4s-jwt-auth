import sbt._

object Dependencies {

  object V {
    val cats       = "2.5.0"
    val catsEffect = "3.0.1"
    val fs2        = "3.0.1"
    val http4s     = "1.0.0-M20"
    val jwt        = "7.1.3"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.11.3"

    val munit = "0.7.23"
  }

  object Libraries {
    def http4s(artifact: String): ModuleID = "org.http4s" %% artifact % V.http4s

    val cats       = "org.typelevel" %% "cats-core"   % V.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffect
    val fs2        = "co.fs2"        %% "fs2-core"    % V.fs2
    val jwtCore    = "com.github.jwt-scala" %% "jwt-core"    % V.jwt

    val http4sDsl    = http4s("http4s-dsl")
    val http4sServer = http4s("http4s-blaze-server")

    // Test
    val munit =     "org.scalameta"                %% "munit"                          % V.munit
  }

  object CompilerPlugins {
    val betterMonadicFor = compilerPlugin("com.olegpy" %% "better-monadic-for" % V.betterMonadicFor)
    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % V.kindProjector cross CrossVersion.full
    )
  }

}
