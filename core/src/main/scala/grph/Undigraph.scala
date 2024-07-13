package Grph

import zio.json._

case class Undigraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N,E] {
  def getAllNodes: Set[N] = nodes
  def getAllEdges: Set[E] = edges
  def getNeighbors(node: N): Set[N] = edges.filter(e => e.from == node || e.to == node).map(e => if e.from == node then e.to else e.from)
  def addEdge(edge: E): Undigraph[N, E] = 
    if (nodes.contains(edge.from) && nodes.contains(edge.to))
      Undigraph(nodes, edges + edge)
    else
      Undigraph(nodes + edge.from + edge.to, edges + edge)
  def removeEdge(edge: E): Undigraph[N,E] = 
    if(edges.contains(edge))
      Undigraph(nodes, edges - edge)
    else
      Undigraph(nodes, edges)
}

object Undigraph {
  implicit def UndigraphDecoder[N: JsonDecoder, E <: Edge[N]: JsonDecoder]: JsonDecoder[Undigraph[N, E]] = DeriveJsonDecoder.gen[Undigraph[N, E]]
  implicit def UndigraphEncoder[N: JsonEncoder, E <: Edge[N]: JsonEncoder]: JsonEncoder[Undigraph[N, E]] = DeriveJsonEncoder.gen[Undigraph[N, E]]
}
