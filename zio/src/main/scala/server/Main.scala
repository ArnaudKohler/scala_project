package server

import zio._
import zio.http._
import server.routes._
import Grph._


object Main extends ZIOAppDefault {
  
  val emptyGraph = DiGraph.empty[Int, UnweightedEdge[Int]]
  val graphLayer = GraphStateService.layer(emptyGraph)

  val digraphRoutes = DigraphRoutes.routes[Int, UnweightedEdge[Int]]
  val otherRoutes = OtherRoutes.routes[Int, UnweightedEdge[Int]]
  val undigraphRoutes = UndigraphRoutes.routes[Int, UnweightedEdge[Int]]
  val operationRoutes = OperationRoutes.routes[Int, UnweightedEdge[Int]]
  def run =
    Server.serve(digraphRoutes ++ otherRoutes ++ undigraphRoutes ++ operationRoutes).provide(Server.default, graphLayer)
}
