package server.routes

import zio._
import zio.http._
import zio.json._

import Grph._
import operations.CoreFunction
import server.GraphService
import server.InMemoryGraphService

object GreetingRoutes {

  val test = CoreFunction.helloMessage()

  def apply[N: JsonDecoder: JsonEncoder, E <: Edge[N]: JsonDecoder: JsonEncoder](): Routes[Any, Response] = {
    Routes(
      // GET /greet
      Method.GET / "greet" -> handler(Response.text(test)),

      // POST /digraph
      Method.POST / "createDigraph" -> handler((req: Request) => {
        for {
          body <- req.body.asString
          graph <- ZIO.fromEither(body.fromJson[DiGraph[N, E]])
          _ <- GraphService.create(graph)
        } yield Response.text("Graph created")
        
      })
    )
  }
}
