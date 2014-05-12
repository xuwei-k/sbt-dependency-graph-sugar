package gilt

import sbt._
import net.virtualvoid.sbt.graph

object DependencyGraph extends Plugin {

  override val projectSettings =
    graph.Plugin.graphSettings ++
      Seq(Compile, Test, Runtime, Provided, Optional).flatMap(addDependencySvgSettings)

  lazy val dependencySvg = TaskKey[File](
    "dependency-svg",
    "Creates an SVG file containing the dependency-graph for a project (based on dependency-dot, requires graphviz tools)"
  )

  lazy val dependencySvgView = TaskKey[Unit](
    "dependency-svg-view",
    "Displays jar dependencies in a browser, based on dependency-dot, requires graphviz tools)"
  )

  lazy val dependencyGraphOpenCommand = TaskKey[Seq[String]](
    "dependency-graph-open-command",
    """command to be run to open the svg; default is Seq("open", "-a", "Safari", <dependencySvgFile>)""")

  private def addDependencySvgSettings(config: Configuration) =
    inConfig(config) {
      Seq(
        dependencySvg <<= graph.Plugin.dependencyDot map {
          (dotFile: File) =>
            val targetSvgFileName: File = dotFile.getParentFile / (dotFile.base + ".svg")
            Seq("dot", "-o" + targetSvgFileName.absolutePath, "-Tsvg", dotFile.absolutePath).!
            targetSvgFileName
        },
        dependencySvgView <<= dependencySvg map {
          (svgFile: File) =>
            Seq("open", "-a", "Safari", svgFile.absolutePath).!
            ()
        },
        dependencyGraphOpenCommand <<= dependencySvg map {
          svgFile => Seq("open", "-a", "Safari", svgFile.getAbsolutePath)
        }
      )
    }
}
