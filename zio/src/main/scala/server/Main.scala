package server

import zio._
import zio.http._
import server.routes._


object Main extends ZIOAppDefault {
  def run =
    Server.serve(GreetingRoutes()).provide(Server.default)
}
