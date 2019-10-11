import scalaj.http._
import scala.sys.process._
import java.nio.file.{Paths, Files}
import java.nio.charset.StandardCharsets
import com.github.lsund.pgnparser._

object Main extends App {
  val gameid = "q6NY3GkB"
  val apiToken: String = "pass lichess/api-token".!!.trim
  val authorizationHeaderValue: String = "Bearer " + apiToken
  val request: HttpRequest = Http("https://lichess.org/game/export/" + gameid)
    .header("Authorization", authorizationHeaderValue)
  val response: HttpResponse[String] = request.asString
  Files.write(
    Paths.get("data/" + gameid + ".pgn"),
    response.body.getBytes(StandardCharsets.UTF_8)
  )
}
