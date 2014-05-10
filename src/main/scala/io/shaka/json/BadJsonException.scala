package io.shaka.json

class BadJsonException(json: String, t: Throwable) extends RuntimeException(s"Bad JSON, can not parse: $json", t)
