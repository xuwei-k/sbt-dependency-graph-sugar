libraryDependencies += {
  def sbtDependencyGraphVersion(sugarVersion: String) = {
//    val semVer = com.gilt.gfc.semver.SemVer(sugarVersion)
//    s"${semVer.major}.${semVer.minor}.${semVer.point}"
    "0.9.0"
  }
  sbt.Defaults.sbtPluginExtra("net.virtual-void" % "sbt-dependency-graph" % sbtDependencyGraphVersion(version.value), (sbtBinaryVersion in pluginCrossBuild).value, (scalaBinaryVersion in pluginCrossBuild).value)
}

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

scalacOptions ++= Seq("-deprecation", "-unchecked")

enablePlugins(ScriptedPlugin)

scriptedLaunchOpts := {
  println(s"Setting scripted version ${version.value}")
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}
