package io.shaka.json

trait Token{def value: String}
object Token{
  case object LEFT_BRACE extends Token{val value = "{"}
  case object RIGHT_BRACE extends Token{val value = "}"}
  case object LEFT_BRACKET extends Token{val value = "["}
  case object RIGHT_BRACKET extends Token{val value = "]"}
  case object COLON extends Token{val value = ":"}
  case object COMMA extends Token{val value = ","}
  case object TRUE extends Token{val value = "true"}
  case object FALSE extends Token{val value = "false"}
  case object NULL extends Token{val value = "null"}
  case class NumberToken(value: String) extends Token
  case class StringToken(value: String) extends Token{
    override def toString: String = value.tail.init
  }
}

