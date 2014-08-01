json    [![Build Status](https://travis-ci.org/timt/naive-json.png?branch=master)](https://travis-ci.org/timt/naive-json) [ ![Download](https://api.bintray.com/packages/timt/repo/naive-json/images/download.png) ](https://bintray.com/timt/repo/naive-json/_latestVersion)
----
A really simple json parser library implemented in scala with no dependencies

Requirements
------------

* [scala](http://www.scala-lang.org) 2.10.4
* [scala](http://www.scala-lang.org) 2.11.2


Usage
-----
Add the following lines to your build.sbt

    resolvers += "Tim Tennant's repo" at "http://dl.bintray.com/timt/repo/"

    libraryDependencies += "io.shaka" %% "naive-json" % "35"

Start hacking

    import io.shaka.json.Json
    ...
    val json = Json("""{"thing1":{"thing2":"meet thing1"}}""")
    val contentAtThing2 = json.thing1.thing2

For more examples see [JsonSpec.scala](https://github.com/timt/json/blob/master/src/test/scala/io/shaka/json/JsonSpec.scala)

Code license
------------
Apache License 2.0
