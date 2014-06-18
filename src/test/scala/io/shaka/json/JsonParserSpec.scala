package io.shaka.json

import org.scalatest.FunSuite
import JsonParser.parse

class JsonParserSpec extends FunSuite {

  test("parse simple json object to map") {
    assert(parse( """{ "greeting" : "sheep & cheese!" }""") === Map("greeting" -> "sheep & cheese!"))
  }

  test("parse number value in json object") {
    assert(parse( """{ "price" : 0.12 }""") === Map("price" -> BigDecimal(0.12)))
  }

  test("parse object value in json object") {
    assert(parse( """{ "uk" : { "greeting" : "sheep & cheese!" } }""") === Map("uk" -> Map("greeting" -> "sheep & cheese!")))
  }

  test("parse true value in json object") {
    assert(parse( """{ "isASheep" : true }""") === Map("isASheep" -> true))
  }

  test("parse false value in json object") {
    assert(parse( """{ "isCheese" : false }""") === Map("isCheese" -> false))
  }

  test("parse null value in json object") {
    assert(parse( """{ "isSheepCheese" : null }""") === Map("isSheepCheese" -> null))
  }

  test("parse list value in json object") {
    assert(parse( """{ "sheepyCheese" : [ "sheep", "cheese", 123.456 ] }""") === Map("sheepyCheese" -> List("sheep", "cheese", BigDecimal(123.456))))
  }

  test("parse multiple values in json object") {
    assert(parse( """{ "greeting" : "sheep & cheese!", "isSheepCheese" : null }""") === Map("greeting" -> "sheep & cheese!", "isSheepCheese" -> null))
  }

  test("parse object in json list") {
    assert(parse( """[ "sheep", "cheese", 123.456,  { "greeting" : "sheep & cheese!" }, true]""") === List("sheep", "cheese", BigDecimal(123.456), Map("greeting" -> "sheep & cheese!"), true))
  }

  test("parse list in json list") {
    assert(parse( """[ "sheep", "cheese", [ "greeting", "sheep & cheese!" ], 123.456]""") === List("sheep", "cheese", List("greeting", "sheep & cheese!"), BigDecimal(123.456)))
  }

  test("parse nested objects") {
    assert(parse( """{ "greeting" : "sheep & cheese!", "france" : { "greeting" : {"french": "mouton et fromage!"} } ,"isSheepCheese" : null }""") === Map("greeting" -> "sheep & cheese!", "france" -> Map("greeting" -> Map("french" -> "mouton et fromage!")), "isSheepCheese" -> null))
  }

  test("parse object nested in list nested in object") {
    assert(parse( """{ "greeting" : "sheep & cheese!", "france" : [ "mouton", {"greeting": "mouton et fromage!"}, "fromage" ] ,"isSheepCheese" : null }""") === Map("greeting" -> "sheep & cheese!", "france" -> List("mouton", Map("greeting" -> "mouton et fromage!"), "fromage"), "isSheepCheese" -> null))
  }

  test("Get nice error message when object not correctly defined") {
    val exception = intercept[MalformedJsonException] {
      parse( """{ "greeting":"sheep & cheese!" """)
    }
    assert(exception.getMessage === """Missing closing }! - can not parse: {"greeting":"sheep & cheese!"""")
  }

  test("Get nice error message when array not correctly defined") {
    val exception = intercept[MalformedJsonException] {
      parse( """[ "sheep", "cheese!" """)
    }
    assert(exception.getMessage === """Missing closing ]! - can not parse: ["sheep","cheese!"""")
  }

  test("Will parse some big example"){
    val bigJobby = parse(bigSample).asInstanceOf[Map[String, Any]]
    val firstResult = bigJobby("results").asInstanceOf[List[Any]](0).asInstanceOf[Map[String, Any]]
    val metadata = firstResult("metadata").asInstanceOf[Map[String, Any]]
    assert(firstResult("id") === 1478555574)
    assert(firstResult("iso_language_code") === "nl")
    assert(bigJobby("max_id") === 1480307926)
    assert(metadata("result_type") === "popular")
  }

  val bigSample =
    """
      |{
      |    "results": [
      |        {
      |            "text": "@twitterapi  http://tinyurl.com/ctrefg",
      |            "to_user_id": 396524,
      |            "to_user": "TwitterAPI",
      |            "from_user": "jkoum",
      |            "metadata": {
      |                "result_type": "popular",
      |                "recent_retweets": 109
      |            },
      |            "id": 1478555574,
      |            "from_user_id": 1833773,
      |            "iso_language_code": "nl",
      |            "source": "<a href='http: //twitter.com/'>twitter< /a>",
      |            "profile_image_url": "http://s3.amazonaws.com/twitter_production/profile_images/118412707/2522215727_a5f07da155_b_normal.jpg",
      |            "created_at": "Wed, 08 Apr 2009 19:22:10 +0000"
      |        },
      |        "...truncated..."
      |    ],
      |    "since_id": 0,
      |    "max_id": 1480307926,
      |    "refresh_url": "?since_id=1480307926&amp;q=%40twitterapi",
      |    "results_per_page": 15,
      |    "next_page": "?page=2&amp;max_id=1480307926&amp;q=%40twitterapi",
      |    "completed_in": 0.031704,
      |    "page": 1,
      |    "query": "%40twitterapi"
      |}
    """.stripMargin


}






