package grphTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{DiGraph, UnweightedEdge}

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

    it should "be able to remove an edge and get set empty" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val removedGraph = updatedGraph.removeEdge(e1)
        assert(removedGraph.getAllEdges == Set.empty)
    }

    it should "be able to remove an edge and get the set with other edges" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val e1 = UnweightedEdge(n1, n2)
        val e2 = UnweightedEdge(n2, n3)
        val updatedGraph = digraph.addEdge(e1)
        val updatedGraph2 = updatedGraph.addEdge(e2)
        val removedGraph = updatedGraph2.removeEdge(e1)
        assert(removedGraph.getAllEdges == Set(e2))
    }

    it should "be bale to handle the remove of a non existing edge" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val n4 = 4
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val e2 = UnweightedEdge(n3, n4)
        val removedGraph = updatedGraph.removeEdge(e2)
        assert(removedGraph.getAllEdges == Set(e1))
    }

    it should "be bale to handle the remove of a semi non existing edge" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val e2 = UnweightedEdge(n2, n3)
        val removedGraph = updatedGraph.removeEdge(e2)
        assert(removedGraph.getAllEdges == Set(e1))
    }

    it should "be able to get all nodes" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        val nodes = Set(n1, n2)
        assert(updatedGraph.getAllNodes == nodes)
    }

    it should "be able to get all neighbors" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val e1 = UnweightedEdge(n1, n2)
        val e2 = UnweightedEdge(n1, n3)
        val updatedGraph = digraph.addEdge(e1)
        val updatedGraph2 = updatedGraph.addEdge(e2)
        assert(updatedGraph2.getNeighbors(n1) == Set(n2, n3))
    }

    it should "be able to get all edges" in {
        val digraph: DiGraph[Int, UnweightedEdge[Int]] = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val e1 = UnweightedEdge(n1, n2)
        val updatedGraph = digraph.addEdge(e1)
        assert(updatedGraph.getAllEdges == Set(e1)) 
    }
}