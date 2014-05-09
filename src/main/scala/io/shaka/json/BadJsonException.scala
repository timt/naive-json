package io.shaka.json

/**
 * Created by timt on 09/05/2014.
 */
class BadJsonException(json: String, t: Throwable) extends RuntimeException(s"Bad JSON, can not parse: $json", t)
