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
      sugarVersion.replaceFirst("-SNAPSHOT$", "")
    }
    sbt.Defaults.sbtPluginExtra("net.virtual-void" % "sbt-dependency-graph" % sbtDependencyGraphVersion(sugarVersion), sbtV, scalaVersion(sbtV))
}

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.2" % "test"

scalacOptions ++= Seq("-deprecation", "-unchecked")

