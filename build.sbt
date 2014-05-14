crossBuildingSettings

CrossBuilding.crossSbtVersions := Seq("0.12", "0.13")

CrossBuilding.scriptedSettings

libraryDependencies <+= (sbtVersion in sbtPlugin, version) {
  (sbtV, v) =>
    def scalaVersion(sbtV: String) = {
      sbtV match {
        case "0.12" => "2.9.2"
        case "0.13" => "2.10"
      }
   }
   sbt.Defaults.sbtPluginExtra("net.virtual-void" % "sbt-dependency-graph" % v, sbtV, scalaVersion(sbtV))
}

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.5" % "test"

scalacOptions ++= Seq("-deprecation", "-unchecked")

net.virtualvoid.sbt.graph.Plugin.graphSettings
