
resolvers += "Coda Hale's Repo" at "http://repo.codahale.com"

addSbtPlugin("net.virtual-void" % "sbt-cross-building" % "0.8.1")

resolvers += "giltgroupe-sbt-plugin-releases" at "https://dl.bintray.com/content/giltgroupe/maven"

libraryDependencies += "com.gilt" %% "gfc-semver" % "0.1.0"
