package io.shaka.json

class JsonObjectNameNotFoundException(jsonObjectName: String, t: Throwable) extends RuntimeException(s"Could not find json object with name $jsonObjectName", t)
