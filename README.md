json
----
A really simple json parser library implemented in scala with no dependencies

Requirements
------------

* [scala](http://www.scala-lang.org) 2.10.4

Usage
-----
Add the following lines to your build.sbt

    resolvers += "Tim Tennant's repo" at "http://timt.github.com/repo/releases/"

    libraryDependencies += "io.shaka" %% "json" % "0.1"

    import io.shaka.json.Json
    ...
    val json = Json("""{"thing1":{"thing2":"meet thing1"}}""")
    val contentAtThing2 = json ~> 'thing1 ~> 'thing2

For more examples see [JsonSpec.scala](https://github.com/timt/json/blob/master/src/test/scala/io/shaka/json/JsonSpec.scala)


Code license
------------
Apache License 2.0