import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._
import microsites.ExtraMdFileConfig

ThisBuild / organizationName := "ProfunKtor"
ThisBuild / crossScalaVersions := List("2.12.15", "2.13.8", "3.2.1")

// publishing
ThisBuild / name := """http4s-jwt-auth"""
ThisBuild / organization := "dev.profunktor"
ThisBuild / homepage := Some(url("https://http4s-jwt-auth.profunktor.dev/"))
ThisBuild / licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))
ThisBuild / developers := List(
  Developer(
    "gvolpe",
    "Gabriel Volpe",
    "volpegabriel@gmail.com",
    url("https://gvolpe.com")
  )
)

promptTheme := PromptTheme(
  List(
    text("[sbt] ", fg(105)),
    text(_ => "http4s-jwt-auth", fg(15)).padRight(" λ ")
  )
)

def maxClassFileName(v: String) = CrossVersion.partialVersion(v) match {
  case Some((2, 13)) | Some((3, _)) => List.empty[String]
  case _                            => List("-Xmax-classfile-name", "100")
}

def scalacVaryingOptions(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
  case Some((3, _)) => List("-source:future")
  case _            => List("-Xsource:3")
}

def compilerPlugins(scalaVersion: String) = CrossVersion.partialVersion(scalaVersion) match {
  case Some((3, _)) => List()
  case _            => List(CompilerPlugins.kindProjector, CompilerPlugins.betterMonadicFor)
}

val commonSettings = List(
  organizationName := "Opinionated JWT authentication library for Http4s",
  startYear := Some(2019),
  licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt")),
  homepage := Some(url("https://http4s-jwt-auth.profunktor.dev/")),
  headerLicense := Some(HeaderLicense.ALv2("2019", "ProfunKtor")),
  resolvers += "Apache public" at "https://repository.apache.org/content/groups/public/",
  scalacOptions ++= maxClassFileName(scalaVersion.value),
  scalafmtOnCompile := true,
  testFrameworks += new TestFramework("munit.Framework")
)

lazy val noPublish = List(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  publish / skip := true
)

lazy val root = (project in file("."))
  .aggregate(core, microsite)
  .settings(noPublish)

lazy val core = (project in file("core"))
  .settings(
    name := "http4s-jwt-auth",
    scalacOptions ++= scalacVaryingOptions(scalaVersion.value),
    libraryDependencies ++=
        compilerPlugins(scalaVersion.value) :::
            List(
              Libraries.cats,
              Libraries.catsEffect,
              Libraries.fs2,
              Libraries.http4sDsl,
              Libraries.http4sServer,
              Libraries.jwtCore,
              Libraries.munit % Test
            )
  )
  .settings(commonSettings: _*)

lazy val microsite = project
  .in(file("site"))
  .enablePlugins(MicrositesPlugin)
  .settings(commonSettings: _*)
  .settings(noPublish)
  .settings(publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true))
  .settings(
    micrositeName := "Http4s Jwt Auth",
    micrositeDescription := "Opinionated JWT authentication library for Http4s",
    micrositeAuthor := "ProfunKtor",
    micrositeGithubOwner := "profunktor",
    micrositeGithubRepo := "http4s-jwt-auth",
    micrositeBaseUrl := "",
    micrositeExtraMdFiles := Map(
          file("README.md") -> ExtraMdFileConfig(
                "index.md",
                "home",
                Map("title" -> "Home", "position" -> "0")
              ),
          file("CODE_OF_CONDUCT.md") -> ExtraMdFileConfig(
                "CODE_OF_CONDUCT.md",
                "page",
                Map("title" -> "Code of Conduct")
              )
        ),
    micrositeExtraMdFilesOutput := (Compile / resourceManaged).value / "jekyll",
    micrositeGitterChannel := true,
    micrositeGitterChannelUrl := "profunktor-dev/http4s-jwt-auth",
    scalacOptions --= List(
          "-Werror",
          "-Xfatal-warnings",
          "-Ywarn-unused-import",
          "-Ywarn-numeric-widen",
          "-Ywarn-dead-code",
          "-Xlint:-missing-interpolator,_"
        )
  )
  .dependsOn(core)

// CI build
addCommandAlias("fullBuild", ";clean;+test;mdoc")
