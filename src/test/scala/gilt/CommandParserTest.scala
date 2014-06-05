package gilt.dependency.graph.sugar

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers


class CommandParserTest extends FunSuite with ShouldMatchers with CommandParser {

  test("simple tokens") {
    parseAll(cmd, "open safari").get should equal(Seq("open", "safari"))
  }

  test("escaped space") {
    parseAll(cmd, """open Chrome\ App""").get should equal(Seq("open", "Chrome App"))
    parseAll(cmd, """open -a Chrome\ App $1""").get should equal(Seq("open", "-a", "Chrome App", "$1"))
  }

  test("quoted space") {
    parseAll(cmd, """open -a "Chrome App" $1""").get should equal(Seq("open", "-a", "Chrome App", "$1"))
  }
}
