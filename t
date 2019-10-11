[1mdiff --git a/build.sbt b/build.sbt[m
[1mindex cfcba87..3594897 100644[m
[1m--- a/build.sbt[m
[1m+++ b/build.sbt[m
[36m@@ -9,4 +9,4 @@[m [mlibraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"[m
 libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.8.0"[m
 libraryDependencies += "org.apache.kafka" %% "kafka" % "2.3.0"[m
 [m
[31m-libraryDependencies += "com.github.lsund" % "pgnparser" % "1.0.0" from "file:///home/lsund/Documents/git/pgnparser/target/scala-2.12/pgnparser_2.12-1.0.0.jar"[m
[32m+[m[32mlibraryDependencies += "com.github.lsund" % "pgnparser" % "1.0.0" from "file:///home/lsund/Documents/git/pgnparser/target/scala-2.12/pgnparser-assembly-1.0.0.jar"[m
[1mdiff --git a/src/main/scala/Main.scala b/src/main/scala/Main.scala[m
[1mindex 4e534b3..422e2d9 100644[m
[1m--- a/src/main/scala/Main.scala[m
[1m+++ b/src/main/scala/Main.scala[m
[36m@@ -41,12 +41,11 @@[m [mobject Main extends App {[m
     .header("Authorization", authorizationHeaderValue)[m
   val response: HttpResponse[String] = request.asString[m
   val dataDir = "data".toFile.createIfNotExists(true)[m
[32m+[m[32m  val pgnFile = "data/" + gameid + ".pgn"[m
[32m+[m[32m  val outfile = "data/" + gameid + ".json"[m
   Files.write([m
[31m-    Paths.get("data/" + gameid + ".pgn"),[m
[32m+[m[32m    Paths.get(pgnFile),[m
     response.body.getBytes(StandardCharsets.UTF_8)[m
   )[m
[31m-  val foo = RunParser[m
[31m-  foo.main(Array())[m
[31m-  // val prod = new Producer[m
[31m-  // prod.writeToKafka("test", "key", )[m
[32m+[m[32m  RunParser.toJson(pgnFile)[m
 }[m
