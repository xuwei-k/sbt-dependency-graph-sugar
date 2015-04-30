val foo = {
  val v = "...."
  v.filter({!List('\r', '\n').contains(_)})
}
