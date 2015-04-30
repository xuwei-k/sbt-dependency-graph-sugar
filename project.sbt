sbtPlugin := true

name := "sbt-dependency-graph-sugar"

organization := "com.gilt"

version in ThisBuild := "git describe --tags --always --dirty".!!.trim.replaceFirst("^v", "")

homepage := Some(url("http://github.com/gilt/sbt-dependency-graph-sugar"))

licenses in GlobalScope += "Apache License 2.0" -> url("https://github.com/gilt/sbt-dependency-graph-sugar/raw/master/LICENSE")

