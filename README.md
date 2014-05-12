sbt-dependency-graph-sugar
==========================

At Gilt we’ve come to love [sbt-dependency-graph](https://github.com/jrudolph/sbt-dependency-graph), but have been frustrated with the ascii output, and found the other outputs not straightforward to use.

This plugin provides some “sugar” that makes life a bit nicer when you are running on a Mac with [GraphViz](http://www.graphviz.org/) installed.

It is versioned the same as sbt-dependency-graph (so current version is 0.7.4), and if you include this plugin, you are pulling in sbt-dependency-graph automatically.

In the past at Gilt we had a monolithic build plugin that pulled in a bunch of things by default, including this sugar.  We are now moving away from this approach.  Instead of putting this in your project’s ``project/plugins.sbt`` file, we recommend putting it once in your ``~/.sbt/0.13/plugins/plugins.sbt`` file, so that it is available in all your apps and managed in the “per-machine plane” instead of per application. This way you can customize the command to view the svg to work always on a given machine (which might not be a Mac with Safari, for example).

Many thanks to Johannes Rudolph for the sbt-dependency-graph plugin, and Andrey Kartashov for the original concept and implementation in our internal build plugin.