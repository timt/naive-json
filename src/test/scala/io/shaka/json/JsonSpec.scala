package io.shaka.json

import org.scalatest.Spec
import org.scalatest.Matchers._
import scala.util.Try


class JsonSpec extends Spec {
  val json = Json(
    """
      |{"widget": {
      |    "debug": "on",
      |    "window": {
      |        "title": "Sample Konfabulator Widget",
      |        "name": "main_window",
      |        "width": 500,
      |        "height": 500
      |    },
      |    "image": {
      |        "src": "Images/Sun.png",
      |        "name": "sun1",
      |        "hOffset": 250,
      |        "vOffset": 250,
      |        "center": "true"
      |    },
      |    "text": {
      |        "data": "Click Here",
      |        "style": "bold"
      |    },
      |    "extras": ["foo", "bar"]
      |}}
    """.stripMargin)

  def `can get string value e.g. widget ~> window ~> title`() {
    val string: Json = json ~> 'widget ~> 'window ~> 'title
    assert(string.toString === "Sample Konfabulator Widget")
  }

  def `can get number value e.g. widget ~> window ~> width`() {
    val number: Json = json ~> 'widget ~> 'window ~> 'width
    assert(number.toDouble === 500)
  }

  def `can get boolean value e.g. widget ~> image ~> center`() {
    val boolean: Json = json ~> 'widget ~> 'image ~> 'center
    assert(boolean.toBoolean === true)
  }

  def `can get map value e.g. widget ~> text`() {
    val aMap:Map[String,String] = (json ~> 'widget ~> 'text).toMap
    assert(aMap === Map("data"->"Click Here", "style" -> "bold"))
  }

  def `can get list value e.g. widget ~> extras`() {
    val aList:List[String] = (json ~> 'widget ~> 'extras).toList
    assert(aList === List("foo", "bar"))
  }

  def `barf when can't parse json string`(){
    a [BadJsonException] should be thrownBy {Json("""{"notwellformed":"json"},""")}
  }

}
  