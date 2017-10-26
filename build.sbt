crossBuildingSettings

CrossBuilding.crossSbtVersions := Seq("0.12", "0.13")

CrossBuilding.scriptedSettings

libraryDependencies <+= (sbtVersion in sbtPlugin, version) {
  (sbtV, sugarVersion) =>
    def scalaVersion(sbtV: String) = sbtV match {
      case "0.12" => "2.9.2"
      case "0.13" => "2.10"
    }

    def sbtDependencyGraphVersion(sugarVersion: String) = {
      val semVer = com.gilt.gfc.semver.SemVer(sugarVersion)
      s"${semVer.major}.${semVer.minor}.${semVer.point}"
    }
    sbt.Defaults.sbtPluginExtra("net.virtual-void" % "sbt-dependency-graph" % sbtDependencyGraphVersion(sugarVersion), sbtV, scalaVersion(sbtV))
}

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scriptedLaunchOpts := {
  println(s"Setting scripted version ${version.value}")
  scriptedLaunchOpts.value ++
    Seq("-Xmx1024M", "-XX:MaxPermSize=256M", "-Dplugin.version=" + version.value)
}
