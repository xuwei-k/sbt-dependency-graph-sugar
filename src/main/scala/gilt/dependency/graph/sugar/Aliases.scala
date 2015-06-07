package gilt.dependency.graph.sugar

import sbt.BuildExtra

object Aliases extends BuildExtra{
  def installAliases: Seq[sbt.Def.Setting[_]] =
      addCommandAlias("dependencySvgView", """;set dependencyGraphOutputFormat in Compile := "svg" ;dependencyGraphView""") ++
      addCommandAlias("dependencyPngView", """;set dependencyGraphOutputFormat in Compile := "png" ;dependencyGraphView""")
}