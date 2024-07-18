package operationsTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{DiGraph, WeightedEdge, Undigraph}
import operations.Floyd

class FloydTest extends AnyFlatSpec {
    val infinity = Double.PositiveInfinity

    "A Floyd" should "be able to find a result in a digraph" in {
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
        val result = Floyd.floyd(u)
        val expectedResult = List(List(0.0, infinity, infinity, infinity, infinity), List(6.0, 0.0, 2.0, 1.0, 2.0), List(7.0, infinity, 0.0, infinity, 3.0), List(5.0, infinity, infinity, 0.0, 1.0), List(4.0, infinity, infinity, infinity, 0.0))
        assert(result == expectedResult)
    }

    it should "be able to find a result in a undigraph" in {
        val graph = Undigraph.empty[Int, WeightedEdge[Int]]
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
        val result = Floyd.floyd(u)
        val expectedResult = List(List(0.0, infinity, infinity, infinity, infinity), List(6.0, 0.0, 2.0, 1.0, 2.0), List(7.0, infinity, 0.0, infinity, 3.0), List(5.0, infinity, infinity, 0.0, 1.0), List(4.0, infinity, infinity, infinity, 0.0))
        assert(result == expectedResult)
    }
}