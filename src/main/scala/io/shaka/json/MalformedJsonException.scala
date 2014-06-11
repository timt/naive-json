package io.shaka.json

class MalformedJsonException(malformation: String, json: String) extends RuntimeException(s"$malformation - can not parse: $json")
