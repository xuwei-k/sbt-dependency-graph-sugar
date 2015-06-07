{
  val pluginVersion = System.getProperty("plugin.version")
  if(pluginVersion == null)
    throw new RuntimeException("""|The system property 'plugin.version' is not defined.
                                 |Specify this property using the scriptedLaunchOpts -D.""")
  else addSbtPlugin("com.gilt" % "sbt-dependency-graph-sugar" % pluginVersion)
}
