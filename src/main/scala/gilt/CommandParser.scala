package gilt.dependency.graph.sugar

import scala.util.parsing.combinator.RegexParsers

trait CommandParser extends RegexParsers {

  override def skipWhitespace = false
  private val simpleTokenPattern = """[^\s"\\]+"""
  private def simpleToken = simpleTokenPattern.r
  private def escapedSpaceToken = repsep(simpleTokenPattern.r, """\\ """.r) ^^ { _.mkString(" ") }
  private def quotedSpaceToken  = """"(.*?)"""".r ^^ { _.tail.init }

  def token = quotedSpaceToken | escapedSpaceToken | simpleToken
  def cmd = rep1sep(token, "\\s+".r)
}