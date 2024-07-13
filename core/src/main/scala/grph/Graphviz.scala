package Grph

import Grph.DiGraph
import Grph.Undigraph

object Graphviz {
  extension [N, E <: Edge[N]](graph: DiGraph[N, E])
    def toDot: String = {
        val nodeString: String = graph.getAllNodes.map{ node => s"\t${node};" }.mkString("\n")
        val edgeString: String = graph.getAllEdges.map { edge =>
        val from = edge.from.toString
        val to = edge.to.toString
        val label = edge match {
            case weighted: WeightedEdge[N] => s"[label = ${weighted.weight}]"
            case _ => ""
        }
        s"\t$from -> $to$label"
        }.mkString("\n")

        s"digraph G {\n$nodeString\n$edgeString\n}"
    }

  extension [N, E <: Edge[N]](graph: Undigraph[N, E])
    def toDot: String = {
        val nodeString: String = graph.getAllNodes.map{ node => s"\t${node};" }.mkString("\n")
        val edgeString: String = graph.getAllEdges.map { edge =>
        val from = edge.from.toString
        val to = edge.to.toString
        val label = edge match {
            case weighted: WeightedEdge[N] => s"[label = ${weighted.weight}]"
            case _ => ""
        }
        s"\t$from -- $to$label"
        }.mkString("\n")

        s"graph G {\n$nodeString\n$edgeString\n}"
    }
}
