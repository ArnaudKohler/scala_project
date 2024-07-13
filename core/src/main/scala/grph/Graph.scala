package Grph

trait Graph[N, E <: Edge[N]]:
        def getAllNodes: Set[N]
        def getAllEdges: Set[E]
        def getNeighbors(node: N): Set[N]
        def addEdge(edge: E): Graph[N,E]
        def removeEdge(edge: E): Graph[N,E]