package io.shaka.json

import org.scalatest.{FunSuite, Spec}
import io.shaka.json.Token._
import io.shaka.json.Token.StringToken


class JsonTokenizerSpec extends FunSuite {

  test("tokenize string value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "greeting" : "sheep & cheese!" }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"greeting\""), COLON, StringToken("\"sheep & cheese!\""), RIGHT_BRACE))
  }

  test("tokenize number value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "price" : 0.12 }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"price\""), COLON, NumberToken("0.12"), RIGHT_BRACE))
  }

  test("tokenize integer number value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "price" : 123 }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"price\""), COLON, NumberToken("123"), RIGHT_BRACE))
  }

  test("tokenize object value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "uk" : { "greeting" : "sheep & cheese!" } }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"uk\""), COLON, LEFT_BRACE, StringToken("\"greeting\""), COLON, StringToken("\"sheep & cheese!\""), RIGHT_BRACE, RIGHT_BRACE))
  }

  test("tokenize true value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "isASheep" : true }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"isASheep\""), COLON, TRUE, RIGHT_BRACE))
  }

  test("tokenize false value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "isCheese" : false }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"isCheese\""), COLON, FALSE, RIGHT_BRACE))
  }

  test("tokenize null value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "isSheepCheese" : null }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"isSheepCheese\""), COLON, NULL, RIGHT_BRACE))
  }

  test("tokenize multiple values in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "greeting" : "sheep & cheese!", "isSheepCheese" : null }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"greeting\""), COLON, StringToken("\"sheep & cheese!\""), COMMA, StringToken("\"isSheepCheese\""), COLON, NULL, RIGHT_BRACE))
  }

  test("tokenize list value in json object") {
    val tokens = JsonTokenizer.tokenize( """{ "sheepyCheese" : [ "sheep", "cheese", 123.456 ] }""")
    assert(tokens === List(LEFT_BRACE, StringToken("\"sheepyCheese\""), COLON, LEFT_BRACKET, StringToken("\"sheep\""), COMMA, StringToken("\"cheese\""), COMMA, NumberToken("123.456"), RIGHT_BRACKET, RIGHT_BRACE))
  }

  test("tokenize object in json list") {
    val tokens = JsonTokenizer.tokenize( """[ "sheep", "cheese", 123.456,  { "greeting" : "sheep & cheese!" }]""")
    assert(tokens === List(LEFT_BRACKET, StringToken("\"sheep\""), COMMA, StringToken("\"cheese\""), COMMA, NumberToken("123.456"), COMMA, LEFT_BRACE, StringToken("\"greeting\""), COLON, StringToken("\"sheep & cheese!\""), RIGHT_BRACE, RIGHT_BRACKET))
  }

  test("tokenize list in json list") {
    val tokens = JsonTokenizer.tokenize( """[ "sheep", "cheese", 123.456,  [ "greeting", "sheep & cheese!" ]]""")
    assert(tokens === List(LEFT_BRACKET, StringToken("\"sheep\""), COMMA, StringToken("\"cheese\""), COMMA, NumberToken("123.456"), COMMA, LEFT_BRACKET, StringToken("\"greeting\""), COMMA, StringToken("\"sheep & cheese!\""), RIGHT_BRACKET, RIGHT_BRACKET))
  }

}









