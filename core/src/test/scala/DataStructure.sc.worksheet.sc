object Grph {
    
    sealed trait Graph[N, E <: Edge[N]]:
        def getAllNodes: Set[N]
        def getAllEdges: Set[E]
        def getNeighbors(node: N): Set[N]
        def addEdge(edge: E): Graph[N,E]
        def removeEdge(edge: E): Graph[N,E]

    sealed trait Edge[N]:
        def from: N
        def to: N
        def weight: Double

    //case class Node[A](value: A)

    case class DiGraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N, E]:
        def getAllNodes: Set[N] = nodes
        def getAllEdges: Set[E] = edges
        def getNeighbors(node: N): Set[N] = edges.filter(_.from == node).map(_.to)
        def addEdge(edge: E): DiGraph[N, E] = {
        if (nodes.contains(edge.from) && nodes.contains(edge.to))
            DiGraph(nodes, edges + edge)
        else
            DiGraph(nodes + edge.from + edge.to, edges + edge)
    }
        def removeEdge(edge: E): DiGraph[N,E] = {
            if(edges.contains(edge))
                DiGraph(nodes, edges - edge)
            else
                DiGraph(nodes, edges)
        }

    case class UndirectedGraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N,E]:
        def getAllNodes: Set[N] = nodes
        def getAllEdges: Set[E] = edges
        def getNeighbors(node: N): Set[N] = edges.filter(e => e.from == node || e.to == node).map(e => if e.from == node then e.to else e.from)
        def addEdge(edge: E): DiGraph[N, E] = {
        if (nodes.contains(edge.from) && nodes.contains(edge.to))
            DiGraph(nodes, edges + edge)
        else
            DiGraph(nodes + edge.from + edge.to, edges + edge)
    }
        def removeEdge(edge: E): DiGraph[N,E] = {
            if(edges.contains(edge))
                DiGraph(nodes, edges - edge)
            else
                DiGraph(nodes, edges)
        }

    
    case class WeightedEdge[N](from: N, to: N, weight: Double) extends Edge[N]
    case class UnweightedEdge[N](from: N, to: N) extends Edge[N]:
        def weight: Double = 1
}

import Grph._

val n1 = 1
val n2 = 2
val n3 = 3

val s1 = Set(WeightedEdge(n1, n2, 1), WeightedEdge(n2, n3, 2), WeightedEdge(n3, n1, 3))

val d1 = DiGraph(Set(n1, n2, n3), s1)

println(d1.getNeighbors(n1)) 

val d2 = d1.addEdge(WeightedEdge(n1, n3, 1)) 
d2

val d3 = d2.addEdge(WeightedEdge(n1, 4, 1)) 
d3.getNeighbors()