package io.shaka
import util.parsing.json.JSON

object Json {
  def apply(json: String) = new Json(try {
    JSON.parseFull(json).get
  }
  catch {
    case e: Throwable => throw new BadJsonException(json, e)
  })
}

class Json(content: Any){
  def ~>(key: String):Json = new Json(toMap(key))
  def ~>(key: Symbol):Json = ~>(key.name)
  def toMap[A] = content.asInstanceOf[Map[String,A]]
  def toList[A] = content.asInstanceOf[List[A]]

  override def toString = content.toString
  def toDouble = toString.toDouble
  def toBoolean = toString.toBoolean
}

class BadJsonException(json: String, t: Throwable) extends RuntimeException(s"Bad JSON, can not parse: $json", t)
