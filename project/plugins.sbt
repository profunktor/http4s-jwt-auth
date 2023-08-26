resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.github.sbt"            % "sbt-ci-release" % "1.5.11")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"   % "0.4.4")
addSbtPlugin("de.heikoseeberger"         % "sbt-header"     % "5.9.0")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"   % "2.5.0")
addSbtPlugin("com.47deg"                 % "sbt-microsites" % "1.4.3")
addSbtPlugin("org.scalameta"             % "sbt-mdoc"       % "2.3.7")
