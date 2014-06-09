package io.shaka.json

import org.scalatest.Spec


class TokenizerSpec extends Spec {

  def `tokenize string value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "greeting" : "sheep & cheese!" }""")
    assert(tokens === List(Token("{"), Token("\"greeting\""), Token(":"), Token("\"sheep & cheese!\""), Token("}")))
  }

  def `tokenize number value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "price" : 0.12 }""")
    assert(tokens === List(Token("{"), Token("\"price\""), Token(":"), Token("0.12"), Token("}")))
  }

  def `tokenize object value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "uk" : { "greeting" : "sheep & cheese!" } }""")
    assert(tokens === List(Token("{"), Token("\"uk\""), Token(":"), Token("{"), Token("\"greeting\""), Token(":"), Token("\"sheep & cheese!\""), Token("}"), Token("}")))
  }

  def `tokenize true value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "isASheep" : true }""")
    assert(tokens === List(Token("{"), Token("\"isASheep\""), Token(":"), Token("true"), Token("}")))
  }

  def `tokenize false value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "isCheese" : false }""")
    assert(tokens === List(Token("{"), Token("\"isCheese\""), Token(":"), Token("false"), Token("}")))
  }

  def `tokenize null value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "isSheepCheese" : null }""")
    assert(tokens === List(Token("{"), Token("\"isSheepCheese\""), Token(":"), Token("null"), Token("}")))
  }

  def `tokenize multiple values in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "greeting" : "sheep & cheese!", "isSheepCheese" : null }""")
    assert(tokens === List(Token("{"), Token("\"greeting\""), Token(":"), Token("\"sheep & cheese!\""), Token(","), Token("\"isSheepCheese\""), Token(":"), Token("null"), Token("}")))
  }

  def `tokenize list value in json object`() {
    val tokens = JsonTokenizer.tokenize( """{ "sheepyCheese" : [ "sheep", "cheese", 123.456 ] }""")
    assert(tokens === List(Token("{"), Token("\"sheepyCheese\""), Token(":"), Token("["), Token("\"sheep\""), Token(","), Token("\"cheese\""), Token(","), Token("123.456"), Token("]"), Token("}")))
  }

  def `tokenize object in json list`() {
    val tokens = JsonTokenizer.tokenize( """[ "sheep", "cheese", 123.456,  { "greeting" : "sheep & cheese!" }]""")
    assert(tokens === List(Token("["), Token("\"sheep\""), Token(","), Token("\"cheese\""), Token(","), Token("123.456"), Token(","), Token("{"), Token("\"greeting\""), Token(":"), Token("\"sheep & cheese!\""), Token("}"), Token("]")))
  }



}

case class Token(value: String)

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





