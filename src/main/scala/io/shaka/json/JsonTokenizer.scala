package io.shaka.json

import io.shaka.json.Token._

object JsonTokenizer {
  val leftBrace = TokenMatcher("\\{")
  val rightBrace = TokenMatcher("\\}")
  val leftBracket = TokenMatcher("\\[")
  val rightBracket = TokenMatcher("\\]")
  val colon = TokenMatcher(":")
  val comma = TokenMatcher(",")
  val isTrue = TokenMatcher("true")
  val isFalse = TokenMatcher("false")
  val isNull = TokenMatcher("null")
  val string = TokenMatcher("\".*?\"")
  val number = TokenMatcher("[0-9]+([\\,\\.][0-9]+)?")

  class TokenMatcher(partialRegex: String) {
    private val regex = s"^($partialRegex)".r

    def unapply(string: String) = regex.findFirstIn(string)
  }

  object TokenMatcher {
    def apply(partialRegex: String) = new TokenMatcher(partialRegex)
  }

  def tokenize(json: String, tokens: List[Token] = List()): List[Token] = {
    val trimmedJson = json.trim
    def continue(token: Token) = tokenize(trimmedJson.substring(token.value.length), token :: tokens)
    trimmedJson match {
      case "" => tokens.reverse
      case leftBrace(s) => continue(LEFT_BRACE)
      case rightBrace(s) => continue(RIGHT_BRACE)
      case leftBracket(s) => continue(LEFT_BRACKET)
      case rightBracket(s) => continue(RIGHT_BRACKET)
      case colon(s) => continue(COLON)
      case comma(s) => continue(COMMA)
      case isTrue(s) => continue(TRUE)
      case isFalse(s) => continue(FALSE)
      case isNull(s) => continue(NULL)
      case string(s) => continue(StringToken(s))
      case number(s) => continue(NumberToken(s))
      case oops => println(s"oops!!!! seems we didn't deal with --- $oops --- tokens :("); tokens.reverse
    }
  }
}
