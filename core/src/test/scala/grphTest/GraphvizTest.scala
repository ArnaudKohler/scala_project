package grphTest

import org.scalatest.flatspec.AnyFlatSpec
import Grph.{Undigraph, UnweightedEdge, WeightedEdge}
import Grph.Graphviz.toDot


class GraphvizTest extends AnyFlatSpec {
    "A Graphiz" should "be able to tranform a undigraph with weighted edges to a dot file" in {
        val b1 = 1
        val b2 = 2
        val b3 = 3
        val b4 = 4
        val b5 = 5
        val undigraph: Undigraph[Int, WeightedEdge[Int]] = Undigraph.empty[Int, WeightedEdge[Int]]
        val e1 = WeightedEdge(b1, b2, 1)
        val e2 = WeightedEdge(b2, b4, 3)
        val e3 = WeightedEdge(b1, b3, 2)
        val e4 = WeightedEdge(b4, b5, 6)
        val e5 = WeightedEdge(b3, b4, 4)
        val u = undigraph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val dot = u.toDot
        val expectedDot = "graph G {\n\t5;\n\t2;\n\t4;\n\t1;\n\t3;\n\t3 -- 4[label = 4.0]\n\t4 -- 5[label = 6.0]\n\t1 -- 2[label = 1.0]\n\t2 -- 4[label = 3.0]\n\t1 -- 3[label = 2.0]\n}"
        assert(dot == expectedDot)
    }

    it should "be able to tranform a digraph with weighted edges to a dot file" in {
        val b1 = 1
        val b2 = 2
        val b3 = 3
        val b4 = 4
        val b5 = 5
        val digraph = Grph.DiGraph.empty[Int, WeightedEdge[Int]]
        val e1 = WeightedEdge(b1, b2, 1)
        val e2 = WeightedEdge(b2, b4, 3)
        val e3 = WeightedEdge(b1, b3, 2)
        val e4 = WeightedEdge(b4, b5, 6)
        val e5 = WeightedEdge(b3, b4, 4)
        val d = digraph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val dot = d.toDot
        val expectedDot = "digraph G {\n\t5;\n\t2;\n\t4;\n\t1;\n\t3;\n\t4 -> 5[label = 6.0]\n\t2 -> 4[label = 3.0]\n\t3 -> 4[label = 4.0]\n\t1 -> 3[label = 2.0]\n\t1 -> 2[label = 1.0]\n}"
        assert(dot == expectedDot)
    }

    it should "be able to tranform a undigraph with unweighted edges to a dot file" in {
        val b1 = 1
        val b2 = 2
        val b3 = 3
        val b4 = 4
        val b5 = 5
        val undigraph: Undigraph[Int, UnweightedEdge[Int]] = Undigraph.empty[Int, UnweightedEdge[Int]]
        val e1 = UnweightedEdge(b1, b2)
        val e2 = UnweightedEdge(b2, b4)
        val e3 = UnweightedEdge(b1, b3)
        val e4 = UnweightedEdge(b4, b5)
        val e5 = UnweightedEdge(b3, b4)
        val u = undigraph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val dot = u.toDot
        val expectedDot = "graph G {\n\t5;\n\t2;\n\t4;\n\t1;\n\t3;\n\t1 -- 2\n\t3 -- 4\n\t4 -- 5\n\t1 -- 3\n\t2 -- 4\n}"
        assert(dot == expectedDot)
    }

    it should "be able to tranform a digraph with unweighted edges to a dot file" in {
        val b1 = 1
        val b2 = 2
        val b3 = 3
        val b4 = 4
        val b5 = 5
        val digraph = Grph.DiGraph.empty[Int, UnweightedEdge[Int]]
        val e1 = UnweightedEdge(b1, b2)
        val e2 = UnweightedEdge(b2, b4)
        val e3 = UnweightedEdge(b1, b3)
        val e4 = UnweightedEdge(b4, b5)
        val e5 = UnweightedEdge(b3, b4)
        val d = digraph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val dot = d.toDot
        val expectedDot = "digraph G {\n\t5;\n\t2;\n\t4;\n\t1;\n\t3;\n\t1 -> 3\n\t4 -> 5\n\t1 -> 2\n\t3 -> 4\n\t2 -> 4\n}"
        assert(dot == expectedDot)
    }
}