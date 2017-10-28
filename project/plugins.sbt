resolvers += "Coda Hale's Repo" at "http://repo.codahale.com"

resolvers += "giltgroupe-sbt-plugin-releases" at "https://dl.bintray.com/content/giltgroupe/maven"

//libraryDependencies += "com.gilt" %% "gfc-semver" % "0.1.0"

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")

libraryDependencies += "org.scala-sbt" %% "scripted-plugin" % sbtVersion.value
