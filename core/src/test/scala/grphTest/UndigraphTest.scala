package grphTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{UnweightedEdge, Undigraph}

class UndigraphTest extends AnyFlatSpec {
    "An Undigraph" should "be able to add an edge" in {
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = undigraph.addEdge(e1)
        val edges = Set(e1)
        assert(updatedGraph.getAllEdges == edges)
    }

    it should "be able to remove an edge" in {
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = undigraph.addEdge(e1)
        val removedGraph = updatedGraph.removeEdge(e1)
        assert(removedGraph.getAllEdges == Set.empty)
    }

    it should "be able to get all nodes" in {
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = undigraph.addEdge(e1)
        val nodes = Set(n1, n2)
        assert(updatedGraph.getAllNodes == nodes)
    }

    it should "be able to get all neighbors" in {
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val e1 = UnweightedEdge(n1, n2)
        val e2 = UnweightedEdge(n1, n3)
        val updatedGraph = undigraph.addEdge(e1)
        val updatedGraph2 = updatedGraph.addEdge(e2)
        assert(updatedGraph2.getNeighbors(n1) == Set(n2, n3))
    }

    it should "be able to get all edges" in {
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = undigraph.addEdge(e1)
        assert(updatedGraph.getAllEdges == Set(e1))
    }
}