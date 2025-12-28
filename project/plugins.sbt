resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository" at "https://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.github.sbt" % "sbt-ci-release"  % "1.11.2")
addSbtPlugin("org.typelevel"  % "sbt-tpolecat"    % "0.5.2")
addSbtPlugin("com.github.sbt" % "sbt-header"      % "5.11.0")
addSbtPlugin("org.scalameta"  % "sbt-scalafmt"    % "2.5.5")
addSbtPlugin("com.47deg"      % "sbt-microsites"  % "1.4.4")
addSbtPlugin("org.scalameta"  % "sbt-mdoc"        % "2.8.2")
addSbtPlugin("com.typesafe"   % "sbt-mima-plugin" % "1.1.4")
