package io.shaka.json

import scala.language.dynamics

object Json {
  def apply(json: String) = new Json(try {
    JsonParser.parse(json)
  }
  catch {
    case e: Throwable => throw new BadJsonException(json, e)
  })
}

class Json(val content: Any) extends Dynamic {
  def selectDynamic(key: String): Json = {
    def getObject: Any = try {
      toMap(key)
    }
    catch {
      case e: NoSuchElementException => throw new JsonObjectNameNotFoundException(key, e)
      case e: Throwable => throw e
    }
    new Json(getObject)
  }

  def toMap[A] = content.asInstanceOf[Map[String, A]]

  def toList[A] = content.asInstanceOf[List[A]]

  override def toString = content.toString

  def toBigDecimal = BigDecimal(toString)

  def toBoolean = toString.toBoolean

  override def equals(obj: Any): Boolean = {
    obj.isInstanceOf[Json] && this.content == obj.asInstanceOf[Json].content
  }
}
