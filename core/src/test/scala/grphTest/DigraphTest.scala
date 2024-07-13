package grphTest

import Grph.DiGraph
import org.scalatest.flatspec.AnyFlatSpec
import Grph.UnweightedEdge

class DigraphTest extends AnyFlatSpec {
    "A Digraph" should "be able to add an edge" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val edges = Set(e1)
        assert(updatedGraph.getAllEdges == edges)
    }

    it should "be able to remove an edge" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val removedGraph = updatedGraph.removeEdge(e1)
        assert(removedGraph.getAllEdges == Set.empty)
    }

    it should "be able to get all nodes" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        assert(updatedGraph.getAllNodes.size == 2)
    }

    it should "be able to get all neighbors" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        assert(updatedGraph.getNeighbors(n1).size == 1)
    }

    it should "be able to get all edges" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        assert(updatedGraph.getAllEdges.size == 1)
    }
}