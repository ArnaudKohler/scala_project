package operationsTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{DiGraph, UnweightedEdge}
import operations.TopologicalSorting

class TopologicalSortingTest extends AnyFlatSpec {
    "A TopologicalSorting" should "be able to find a result in a graph" in {
        val graph = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val n4 = 4
        val n5 = 5
        val e1 = UnweightedEdge(n1, n2)
        val e2 = UnweightedEdge(n2, n4)
        val e3 = UnweightedEdge(n1, n3)
        val e4 = UnweightedEdge(n4, n5)
        val e5 = UnweightedEdge(n3, n4)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val result = TopologicalSorting.topologicalSort(u)
        val expectedResult = List(n1, n3, n2, n4, n5)
        assert(result == expectedResult)
    }
}