package com.github.lsund.gamefetcher

import java.util.Properties
import scalaj.http._
import scala.sys.process._
import java.nio.file.{Paths, Files}, java.nio.charset.StandardCharsets
import com.github.lsund.pgnparser._
import better.files._, better.files.File._
import org.apache.kafka.clients.producer._

class Producer {
  def writeToKafka(topic: String, key: String, value: String): Unit = {
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
    val producer = new KafkaProducer[String, String](props)
    val record = new ProducerRecord[String, String](topic, key, value)
    try {
      producer.send(record)
    } catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }
    producer.close()
  }
}

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
  val foo = RunParser
  foo.main(Array())
  // val prod = new Producer
  // prod.writeToKafka("test", "key", )
}
