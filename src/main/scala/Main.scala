import scalaj.http._

object Main extends App {
  val request: HttpRequest = Http("http://example.com")
  val response: HttpResponse[String] =
    request.asString
  println(response);
}
