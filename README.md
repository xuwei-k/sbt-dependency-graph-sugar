sbt-dependency-graph-sugar
==========================

At Gilt we’ve come to love [sbt-dependency-graph](https://github.com/jrudolph/sbt-dependency-graph), but have been frustrated with the ascii output, and found the other outputs not straightforward to use.

This plugin provides some “sugar” that makes life a bit nicer when you are running on a machine with [GraphViz](http://www.graphviz.org/) installed.

Scripted tests status [![Circle CI](https://circleci.com/gh/gilt/sbt-dependency-graph-sugar/tree/master.svg?style=svg)](https://circleci.com/gh/gilt/sbt-dependency-graph-sugar/tree/master)

### Installation

Its version is taken from sbt-dependency-graph (current version is 0.8.2), and if you include this plugin, you are pulling in sbt-dependency-graph automatically.

Enable the plugin with the following configuration:
```scala
addSbtPlugin("com.gilt" % "sbt-dependency-graph-sugar" % "0.8.2")
```

In the past at Gilt we had a monolithic build plugin that pulled in a bunch of things by default, including this sugar.  We are now moving away from this approach.  Instead of putting this in your project’s ``project/plugins.sbt`` file, we recommend putting it once in your ``~/.sbt/0.13/plugins/sbt-dependency-graph-sugar.sbt`` file, so that it is available in all your apps and managed in the “per-machine plane” instead of per application. This way you can customize the command to view the svg to work always on a given machine.

### Usage

`dependencySvg` creates a svg file into the target directory of the project

`dependencySvgView` views the svg file in a browser

By default, the svg file is opened using the command ``open -a Safari [path-to-svg]``. This should work well on a default Mac. You can change this behaviour by creating a file, ``~/.sbt/gilt/sbt-dependency-graph-sugar-cmd`` that holds a replacement command. The plugin will try to find the first line of the file that it can parse as a command, and it should have the token ``$1`` where the path to the svg file should go.  Example:

	$ cat ~/.sbt/gilt/sbt-dependency-graph-sugar-cmd
	open -a Google\ Chrome $1

The output format can be changed by setting the `dependencyGraphOutputFormat` key:

```scala
  dependencyGraphOutputFormat in Compile := "png"
```

This must include a configuration. There is a helper to modify the keys for all configs:

`gilt.DependencyGraph.inConfigs(dependencyGraphOutputFormat := "png")`

You can obviously put whatever command works on your particular OS, and as long as the `scala.sys.process._` machinery can run it, it should work fine.

### Credits

Many thanks to Johannes Rudolph for the sbt-dependency-graph plugin, and Andrey Kartashov for the original concept and implementation in our internal build plugin.
