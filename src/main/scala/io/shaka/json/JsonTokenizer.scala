package io.shaka.json

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
  val number = TokenMatcher("[0-9]+[\\,\\.][0-9]+")

  class TokenMatcher(partialRegex: String) {
    private val regex = s"^($partialRegex)".r

    def unapply(string: String) = regex.findFirstIn(string)
  }

  object TokenMatcher {
    def apply(partialRegex: String) = new TokenMatcher(partialRegex)
  }

  def tokenize(json: String, tokens: List[Token] = List()): List[Token] = {
    val trimmedJson = json.trim
    def continue(matched: String) = tokenize(trimmedJson.substring(matched.length), Token(matched) :: tokens)
    trimmedJson match {
      case "" => tokens.reverse
      case leftBrace(s) => continue(s)
      case rightBrace(s) => continue(s)
      case leftBracket(s) => continue(s)
      case rightBracket(s) => continue(s)
      case colon(s) => continue(s)
      case comma(s) => continue(s)
      case isTrue(s) => continue(s)
      case isFalse(s) => continue(s)
      case isNull(s) => continue(s)
      case string(s) => continue(s)
      case number(s) => continue(s)
      case oops => println(s"oops!!!! seems we didn't deal with --- $oops --- tokens :("); tokens.reverse
    }
  }
}
