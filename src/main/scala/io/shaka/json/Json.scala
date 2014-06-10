package io.shaka.json

import scala.language.dynamics
import scala.util.parsing.json.JSON

object Json {
  def apply(json: String) = new Json(try {
    JSON.parseFull(json).get
  }
  catch {
    case e: Throwable => throw new BadJsonException(json, e)
  })
}

class Json(content: Any) extends Dynamic{
  def selectDynamic(key: String):Json = new Json(toMap(key))
  def toMap[A] = content.asInstanceOf[Map[String,A]]
  def toList[A] = content.asInstanceOf[List[A]]

  override def toString = content.toString
  def toBigDecimal = BigDecimal(toString)
  def toBoolean = toString.toBoolean

}
