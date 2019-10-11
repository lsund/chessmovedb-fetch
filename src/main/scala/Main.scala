package com.github.lsund.gamefetcher

import scalaj.http._
import scala.sys.process._
import java.nio.file.{Paths, Files}, java.nio.charset.StandardCharsets
import com.github.lsund.pgnparser._
import better.files._, better.files.File._

object Main extends App {
  val gameid = "q6NY3GkB"
  val apiToken: String = "pass lichess/api-token".!!.trim
  val authorizationHeaderValue: String = "Bearer " + apiToken
  val request: HttpRequest = Http("https://lichess.org/game/export/" + gameid)
    .header("Authorization", authorizationHeaderValue)
  val response: HttpResponse[String] = request.asString
  val dataDir = "data".toFile.createIfNotExists(true)
  Files.write(
    Paths.get("data/" + gameid + ".pgn"),
    response.body.getBytes(StandardCharsets.UTF_8)
  )
}
