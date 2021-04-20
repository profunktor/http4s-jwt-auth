resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.geirsson"              % "sbt-ci-release" % "1.5.7")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"   % "0.1.17")
addSbtPlugin("de.heikoseeberger"         % "sbt-header"     % "5.6.0")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"   % "2.4.2")
addSbtPlugin("com.47deg"                 % "sbt-microsites" % "1.3.3")
addSbtPlugin("org.scalameta"             % "sbt-mdoc"       % "2.2.20")
addSbtPlugin("com.scalapenos"            % "sbt-prompt"     % "1.0.2")
