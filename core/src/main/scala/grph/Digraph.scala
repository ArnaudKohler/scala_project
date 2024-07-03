package Grph

import zio.json._

case class DiGraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N, E] {
  def getAllNodes: Set[N] = nodes
  def getAllEdges: Set[E] = edges
  def getNeighbors(node: N): Set[N] = edges.filter(_.from == node).map(_.to)
  def addEdge(edge: E): DiGraph[N, E] = 
    if (nodes.contains(edge.from) && nodes.contains(edge.to))
      DiGraph(nodes, edges + edge)
    else
      DiGraph(nodes + edge.from + edge.to, edges + edge)
  def removeEdge(edge: E): DiGraph[N,E] = 
    if(edges.contains(edge))
      DiGraph(nodes, edges - edge)
    else
      DiGraph(nodes, edges)
  
  def toDot: String = {
    val nodeString: String = getAllNodes.map{node => s"\t${node};"}.mkString("\n")
    val edgeString: String = getAllEdges.map{ edge =>
      val from = edge.from.toString
      val to = edge.to.toString
      val label = edge match 
        case weighted: WeightedEdge[N] => s"[label = ${weighted.weight}]"
        case _ => ""
      s"\t$from -> $to$label"
    }.mkString("\n")

    s"digraph G {\n$nodeString\n$edgeString\n}"
  }
}

object DiGraph {
  implicit def diGraphDecoder[N: JsonDecoder, E <: Edge[N]: JsonDecoder]: JsonDecoder[DiGraph[N, E]] = DeriveJsonDecoder.gen[DiGraph[N, E]]
  implicit def diGraphEncoder[N: JsonEncoder, E <: Edge[N]: JsonEncoder]: JsonEncoder[DiGraph[N, E]] = DeriveJsonEncoder.gen[DiGraph[N, E]]
}
