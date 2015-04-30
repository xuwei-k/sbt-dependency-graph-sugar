import sbt.complete.DefaultParsers._

val servers = token(
  literal("desarrollo") |
  literal("parametrizacion")
)