package operations

import Grph._

object Floyd {
  def floyd[N, E <: WeightedEdge[N]](graph: Graph[N, E]): List[List[Double]] = {
    val nodes = graph.getAllNodes.toList
    val n = nodes.size
    val adjMatrix = Array.ofDim[Double](n, n) // Floyd algorithm requires matrix of size n x n

    for (i <- 0 until n) { // Initialize matrix with 0 in diagonal and infinity elsewhere
      for (j <- 0 until n) {
        if (i == j) adjMatrix(i)(j) = 0
        else adjMatrix(i)(j) = -9999
      }
    }

    for (edge <- graph.getAllEdges) { // Go through all edges and populate their wieghts in the matrix
      val sourceIndex = nodes.indexOf(edge.from)
      val destIndex = nodes.indexOf(edge.to)
      adjMatrix(sourceIndex)(destIndex) = edge.weight
    }


    for (k <- 0 until n) { // Implementation of Floyd core algorithm
      for (i <- 0 until n) {
        for (j <- 0 until n) {
          if (adjMatrix(i)(k) != -9999 && adjMatrix(k)(j) != -9999 &&
              adjMatrix(i)(k) + adjMatrix(k)(j) < adjMatrix(i)(j)) {
            adjMatrix(i)(j) = adjMatrix(i)(k) + adjMatrix(k)(j)
          }
        }
      }
    }

    // Convert adjacency matrix to List[List[Double]]
    adjMatrix.map(_.toList).toList
  }
}
