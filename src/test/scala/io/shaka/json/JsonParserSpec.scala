package io.shaka.json

import org.scalatest.Spec
import JsonParser.parse
import io.shaka.json.Token.{COLON, StringToken, LEFT_BRACE}

class JsonParserSpec extends Spec {

  def `parse simple json object to map`() {
    assert(parse( """{ "greeting" : "sheep & cheese!" }""") === Map("greeting" -> "sheep & cheese!"))
  }

}

object JsonParser {
  def parse(json: String): Any = parse(JsonTokenizer.tokenize(json))

  private def parse(tokens: List[Token]): Any = tokens match {
    case LEFT_BRACE :: _ => jsonObject(tokens)
    case _ => throw new BadJsonException(toJsonString(tokens))

  }

  private def jsonObject(tokens: List[Token]): Map[String, Any] = {
    //TODO handle malformedness
    //    if(tokens.last != Token{"}"}){
    //      throw new MalformedJsonException("Missing closing }!", toJsonString(tokens))
    //    }
    tokens.tail.init match {
      case (key: StringToken) :: COLON :: theRest => Map(key.toString -> valuePlus(theRest))
      case Nil => Map()
    }

  }

  private def valuePlus(tokens: List[Token]): Any = {
    tokens match {
      case (value: StringToken) :: Nil => value.toString()
    }
  }

  private def toJsonString(tokens: List[Token]) = tokens.map(_.value).mkString

}



