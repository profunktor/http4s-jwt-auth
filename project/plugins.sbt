resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"
resolvers += "OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin("com.github.sbt"    % "sbt-ci-release" % "1.5.12+38-6b30fb12-SNAPSHOT")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype"   % "3.11.0")
addSbtPlugin("org.typelevel"     % "sbt-tpolecat"   % "0.5.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"     % "5.10.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"   % "2.5.2")
addSbtPlugin("com.47deg"         % "sbt-microsites" % "1.4.4")
addSbtPlugin("org.scalameta"     % "sbt-mdoc"       % "2.5.3")
