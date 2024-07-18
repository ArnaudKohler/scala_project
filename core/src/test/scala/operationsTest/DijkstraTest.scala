package operationsTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{DiGraph, WeightedEdge}
import operations.Dijkstra

class DijkstraTest extends AnyFlatSpec {

    "A Dijkstra" should "be able to find a result in a digraph with weigted edge" in {
        val graph = DiGraph.empty[Int, WeightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val n4 = 4
        val n5 = 5
        val e1 = WeightedEdge(n1, n2, 2)
        val e2 = WeightedEdge(n2, n4, 3)
        val e3 = WeightedEdge(n1, n3, 1)
        val e4 = WeightedEdge(n4, n5, 4)
        val e5 = WeightedEdge(n3, n4, 1)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val result = Dijkstra.dijkstra(u, n1) match {
            case Left(errMsg) => errMsg
            case Right(distances) => s"Distances from node $n1: [${distances.mkString(", ")}]"
        }
        val expectedResult = "Distances from node 1: [6.0, 0.0, 2.0, 1.0, 2.0]"
        assert(result == expectedResult)
    }
}