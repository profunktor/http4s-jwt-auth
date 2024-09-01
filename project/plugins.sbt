resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.github.sbt"    % "sbt-ci-release" % "1.6.1")
addSbtPlugin("org.typelevel"     % "sbt-tpolecat"   % "0.5.2")
addSbtPlugin("de.heikoseeberger" % "sbt-header"     % "5.10.0")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"   % "2.5.2")
addSbtPlugin("com.47deg"         % "sbt-microsites" % "1.4.4")
addSbtPlugin("org.scalameta"     % "sbt-mdoc"       % "2.5.4")
