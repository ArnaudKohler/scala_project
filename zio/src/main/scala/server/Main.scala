package server

import zio._
import zio.http._
import server.routes._
import Grph._

object Main extends ZIOAppDefault {

  val emptyGraphUnweighted = DiGraph.empty[Int, UnweightedEdge[Int]]
  val graphLayerUnweighted = GraphStateService.layer[Int, UnweightedEdge[Int]](emptyGraphUnweighted)

  val emptyGraphWeighted = DiGraph.empty[Int, WeightedEdge[Int]]
  val graphLayerWeighted = GraphStateService.layer[Int, WeightedEdge[Int]](emptyGraphWeighted)

  val digraphWeighted = DigraphWeighted.routes[Int, WeightedEdge[Int]]
  val digraphUnweighted = DigraphUnweighted.routes[Int, UnweightedEdge[Int]]
  val undigraphWeighted = UndigraphWeighted.routes[Int, WeightedEdge[Int]]
  val undigraphUnweighted = UndigraphUnweighted.routes[Int, UnweightedEdge[Int]]

  val operationUnweighted = OperationUnweighted.routes[Int, UnweightedEdge[Int]]
  val operationWeighted = OperationWeighted.routes[Int, WeightedEdge[Int]]
  def run =
    Server.serve(digraphWeighted ++ digraphUnweighted ++ undigraphWeighted ++ undigraphUnweighted ++ operationUnweighted ++ operationWeighted)
      .provide(
        Server.default,
        graphLayerUnweighted,
        graphLayerWeighted
      )
}
