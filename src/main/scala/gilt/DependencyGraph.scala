package gilt

import sbt._
import sbt.Keys.streams
import net.virtualvoid.sbt.graph
import gilt.dependency.graph.sugar.{Aliases, CommandParser}

//TODO: limit stringly-typed format to valid types
object DependencyGraph extends Plugin {

  private[this] lazy val parser = CommandParser

  val defaultFormat = "svg"

  override val projectSettings =
    graph.DependencyGraphSettings.graphSettings ++ dependencyViewSettings ++ Aliases.installAliases

  lazy val dependencyGraphOutputFormat = SettingKey[String](
    "dependency-graph-output-format", 
    "the format to pass to dot when generating the dependency graph"
  )

  lazy val dependencyGraphRender = TaskKey[File](
    "dependency-graph-render",
    "Creates a file containing the dependency-graph for a project (based on dependency-dot, requires graphviz tools)"
  )

  lazy val dependencyGraphView = TaskKey[Unit](
    "dependency-graph-view",
    "Displays jar dependencies in a browser, based on dependency-dot, requires graphviz tools)"
  )

  lazy val dependencyGraphOpenCommand = TaskKey[Seq[String]](
    "dependency-graph-open-command",
    """command to be run to open the rendered graph; default is Seq("open", "-a", "Safari", <dependencyGraphFile>)""")

  // helper that can be used to define a setting like 'dependencyGraphOutputFormat' for all relevant configurations
  //   gilt.DependencyGraph.inConfigs(dependencyGraphOutputFormat := "png")
  def inConfigs[T](keys: Seq[Setting[_]]) = {
    configs.flatMap(inConfig(_)(keys))
  }

  private[this] lazy val configs = Seq(Compile, Test, Runtime, Provided, Optional)

  private[this] lazy val DefaultCommand = Seq("open", "-a", "Safari", "$1")

  private[this] def openFileCommand() = {
    // Try to open the file and pull out the first command we can actually parse,
    // falling back to the DefaultCommand if we can't do so successfully
    try {
      val cmdFile = file(sys.props("user.home")) / ".sbt" / "gilt" / "sbt-dependency-graph-sugar-cmd"
      (io.Source.fromFile(cmdFile).getLines() flatMap {
        line =>
          parser.parse(line)
      }).toSeq.headOption.getOrElse(DefaultCommand)
    } catch {
      case _: Exception => DefaultCommand
    }
  }

  private[this] lazy val dependencyViewSettings = inConfigs {
    Seq(
      dependencyGraphOutputFormat := defaultFormat,
      dependencyGraphRender := {
        val dotFile = graph.DependencyGraphKeys.dependencyDot.value
        val format = dependencyGraphOutputFormat.value
        val targetFileName: File = dotFile.getParentFile / (dotFile.base + "." + format)
        Seq("dot", "-o" + targetFileName.absolutePath, "-T" + format, dotFile.absolutePath).!
        targetFileName
      },
      dependencyGraphView := {
        val cmd = dependencyGraphOpenCommand.value
        try {
          cmd.!
        } catch {
          case ignore: Exception =>
            streams.value.log.error("Could not run [" + cmd.mkString(" ") + "]: " + ignore)
        }
        ()
      },
      dependencyGraphOpenCommand := {
        openFileCommand().map { token =>
          if (token == "$1") dependencyGraphRender.value.getAbsolutePath
          else token
        }
      }
    )
  }

}
