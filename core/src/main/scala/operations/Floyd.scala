package operations

import Grph._

object Floyd {
  def floyd[N, E <: WeightedEdge[N]](graph: Graph[N, E]): List[List[Double]] = {
    val nodes = graph.getAllNodes.toList
    val n = nodes.size
    val infinity = Double.PositiveInfinity

    // Initialize the adjacency matrix with infinity and zeroes on the diagonal
    val adjMatrix = Array.fill(n, n)(infinity)
    for (i <- 0 until n) adjMatrix(i)(i) = 0.0

    // Populate the adjacency matrix with edge weights
    for (edge <- graph.getAllEdges) {
      val sourceIndex = nodes.indexOf(edge.from)
      val destIndex = nodes.indexOf(edge.to)
      adjMatrix(sourceIndex)(destIndex) = edge.weight
    }

    // Implementation of Floyd-Warshall core algorithm
    for (k <- 0 until n) {
      for (i <- 0 until n) {
        for (j <- 0 until n) {
          if (adjMatrix(i)(k) + adjMatrix(k)(j) < adjMatrix(i)(j)) {
            adjMatrix(i)(j) = adjMatrix(i)(k) + adjMatrix(k)(j)
          }
        }
      }
    }

    // Convert adjacency matrix to List[List[Double]]
    adjMatrix.map(_.toList).toList
  }
}