package operationsTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{DiGraph, UnweightedEdge}
import operations.DepthFirstSearch

class DepthFirstSearchTest extends AnyFlatSpec {
    "A DepthFirstSearch" should "be able to find a result in a graph" in {
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
        val result = DepthFirstSearch.dfs(u, n1)
        val expectedResult = Set(n5, n1, n2, n3, n4)
        assert(result == expectedResult)
    }

    it should "be able to find a result in a graph with a cycle" in {
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
        val e6 = UnweightedEdge(n4, n2)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5).addEdge(e6)
        val result = DepthFirstSearch.dfs(u, n1)
        val expectedResult = Set(n5, n1, n2, n3, n4)
        assert(result == expectedResult)
    }

    it should "be able to find a result in a graph with multiple paths" in {
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
        val e6 = UnweightedEdge(n3, n5)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5).addEdge(e6)
        val result = DepthFirstSearch.dfs(u, n1)
        val expectedResult = Set(n5, n1, n2, n3, n4)
        assert(result == expectedResult)
    }
}