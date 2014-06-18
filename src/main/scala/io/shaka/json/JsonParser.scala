package io.shaka.json

import io.shaka.json.Token._
import io.shaka.json.Token.StringToken
import scala.Some
import io.shaka.json.Token.NumberToken

object JsonParser {

  def parse(json: String): Any = parse(JsonTokenizer.tokenize(json))

  private def parse(tokens: List[Token]): Any = tokens match {
    case LEFT_BRACE :: _ => jsonObject(tokens)
    case LEFT_BRACKET :: _ => jsonArray(tokens)
    case _ => throw new BadJsonException(toJsonString(tokens))

  }

  private def jsonObject(tokens: List[Token]): Map[String, Any] = {
    if (tokens.last != RIGHT_BRACE) {
      throw new MalformedJsonException("Missing closing }!", toJsonString(tokens))
    }
    def objectContent(tokens: List[Token]): Map[String, Any] = {
      tokens match {
        case (key: StringToken) :: COLON :: aValue :: Nil => Map(key.toString -> value(aValue))
        case (key: StringToken) :: COLON :: aValue :: COMMA :: more => Map(key.toString -> value(aValue)) ++ objectContent(more)
        case (key: StringToken) :: COLON :: LEFT_BRACE :: more =>
          val (objectTokens, theRest) = takeJsonObjectFromHead(LEFT_BRACE :: more)
          Map(key.toString -> value(objectTokens)) ++ objectContent(theRest)
        case (key: StringToken) :: COLON :: LEFT_BRACKET :: more =>
          val (arrayTokens, theRest) = takeJsonArrayFromHead(LEFT_BRACKET :: more)
          Map(key.toString -> value(arrayTokens)) ++ objectContent(theRest)
        case Nil => Map()
        case _ => throw new MalformedJsonException("Doh!", toJsonString(tokens))
      }
    }
    objectContent(tokens.tail.init)
  }

  private def jsonArray(tokens: List[Token]): List[Any] = {
    if (tokens.last != RIGHT_BRACKET) {
      throw new MalformedJsonException("Missing closing ]!", toJsonString(tokens))
    }
    def arrayContents(tokens: List[Token]): List[Any] = {
      tokens match {
        case aValue :: COMMA :: theRest => value(aValue) :: arrayContents(theRest)
        case aValue :: Nil => value(aValue) :: Nil
        case LEFT_BRACE :: _ =>
          val (objectTokens, theRest) = takeJsonObjectFromHead(tokens)
          value(objectTokens) :: arrayContents(theRest)
        case LEFT_BRACKET :: _ =>
          val (arrayTokens, theRest) = takeJsonArrayFromHead(tokens)
          value(arrayTokens) :: arrayContents(theRest)
        case Nil => List()
        case _ => throw new MalformedJsonException("Doh!", toJsonString(tokens))
      }

    }
    arrayContents(tokens.tail.init)
  }

  private def takeJsonArrayFromHead(tokens: List[Token]): (List[Token], List[Token]) = {
    splitAtMatchingTokenPair((LEFT_BRACKET, RIGHT_BRACKET), tokens.indexOf(RIGHT_BRACKET), tokens)
  }

  private def takeJsonObjectFromHead(tokens: List[Token]): (List[Token], List[Token]) = {
    splitAtMatchingTokenPair((LEFT_BRACE, RIGHT_BRACE), tokens.indexOf(RIGHT_BRACE), tokens)
  }

  private def splitAtMatchingTokenPair(tokenPair: (Token, Token), indexOfNextClosingToken: Int, tokens: List[Token]): (List[Token], List[Token]) = {
    val (possibleObject, theRest) = tokens.splitAt(indexOfNextClosingToken + 1)
    if (possibleObject.count(_ == tokenPair._1) != possibleObject.count(_ == tokenPair._2)) {
      splitAtMatchingTokenPair(tokenPair, tokens.indexOf(tokenPair._2, indexOfNextClosingToken + 1), tokens)
    } else {
      (possibleObject, if (theRest.headOption == Some(COMMA)) theRest.tail else theRest)
    }

  }


  private def value(token: Token): Any = value(List(token))

  private def value(tokens: List[Token]): Any = {
    tokens match {
      case (value: StringToken) :: Nil => value.toString()
      case NumberToken(number) :: Nil => BigDecimal(number)
      case LEFT_BRACE :: _ => jsonObject(tokens)
      case LEFT_BRACKET :: _ => jsonArray(tokens)
      case TRUE :: Nil => true
      case FALSE :: Nil => false
      case NULL :: Nil => null
      case _ => throw new MalformedJsonException("Doh!", toJsonString(tokens))
    }
  }

  private def toJsonString(tokens: List[Token]) = tokens.map(_.value).mkString

}
