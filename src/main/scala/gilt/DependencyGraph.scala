package gilt

import sbt._
import sbt.Keys.streams
import net.virtualvoid.sbt.graph
import gilt.dependency.graph.sugar.CommandParser

object DependencyGraph extends Plugin with CommandParser {

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

  private val DefaultCommand = Seq("open", "-a", "Safari", "$1")

  private def openSvgCommand() = {
    // Try to open the file and pull out the first command we can actually parse,
    // falling back to the DefaultCommand if we can't do so successfully
    try {
      val cmdFile = file(sys.props("user.home")) / ".sbt" / "gilt" / "sbt-dependency-graph-sugar-cmd"
      (io.Source.fromFile(cmdFile).getLines() flatMap {
        line =>
          parseAll(cmd, line) match {
            case Success(result, _) => Some(result)
            case _ => None
          }
      }).toSeq.headOption.getOrElse(DefaultCommand)
    } catch {
      case _: Exception => DefaultCommand
    }
  }

  private def addDependencySvgSettings(config: Configuration) =
    inConfig(config) {
      Seq(
        dependencySvg <<= graph.Plugin.dependencyDot map {
          (dotFile: File) =>
            val targetSvgFileName: File = dotFile.getParentFile / (dotFile.base + ".svg")
            Seq("dot", "-o" + targetSvgFileName.absolutePath, "-Tsvg", dotFile.absolutePath).!
            targetSvgFileName
        },
        dependencySvgView <<= (dependencyGraphOpenCommand, streams) map {
          (cmd: Seq[String], streams) =>
            try {
              cmd.!
            } catch {
              case ignore: Exception =>
                streams.log.error("Could not run [" + cmd.mkString(" ") + "]: " + ignore)
            }
            ()
        },
        dependencyGraphOpenCommand <<= dependencySvg map {
          svgFile => openSvgCommand().map {
            token =>
              if (token == "$1") svgFile.getAbsolutePath
              else token
          }
        }
      )
    }


}
