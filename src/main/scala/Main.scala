package com.github.lsund.chessmovedb_fetch

import java.net.SocketTimeoutException
import java.util.Properties
import scalaj.http._
import scala.sys.process._
import java.nio.file.{Paths, Files}, java.nio.charset.StandardCharsets
import io.circe._, io.circe.generic.auto._
import io.circe.parser._, io.circe.syntax._
import better.files._, better.files.File._
import org.apache.kafka.clients.producer._
import com.typesafe.scalalogging._

object Main extends App {

  val logger = Logger("chessmovedb-fetch")

  def makeKafkaProducer(): KafkaProducer[String, String] = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put(
      "key.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    props.put(
      "value.serializer",
      "org.apache.kafka.common.serialization.StringSerializer"
    )
    return new KafkaProducer[String, String](props)
  }

  def produceMessage(
      producer: KafkaProducer[String, String],
      topic: String,
      key: String,
      value: String
  ): Unit = {
    val record =
      new ProducerRecord[String, String](topic, key, value)
    try {
      producer.send(record)
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
    producer.close()
  }

  def getGameId(apiToken: String): String = {
    val response = Http("https://lichess.org/tv/channels")
      .header("Authorization", "Bearer " + apiToken)
      .header("Accept", "application/json")
      .asString
      .body
    parse(response)
      .getOrElse(Json.Null)
      .hcursor
      .downField("Top Rated")
      .downField("gameId")
      .as[String] match {
      case Right(x) => return x
      case Left(e)  => throw new Exception(e)
    }
  }

  def getGame(gameid: String) {
    var finished = false
    while (!finished) {
      val response = Http("https://lichess.org/game/export/" + gameid)
        .header("Authorization", "Bearer " + apiToken)
        .header("Accept", "application/json")
        .asString
      if (response.code == 200) {
        val producer = makeKafkaProducer()
        produceMessage(producer, "game", gameid, response.body)
        finished = true
      }
      logger.info("waiting...")
      Thread.sleep(30000)
    }
  }

  val apiToken = "pass lichess/api-token".!!.trim

  while (true) {
    val gameid = getGameId(apiToken)
    logger.info(
      "Waiting to download game with URL: https://lichess.org/" + gameid
    )
    try {
      getGame(gameid)
      logger.info("Game finished and downloaded")
    } catch {
      case e: SocketTimeoutException => {
        logger.error("Socket timed out, trying again...")
      }
    }
    Thread.sleep(10000)
  }
}
