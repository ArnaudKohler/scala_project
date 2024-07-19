package operations

import Grph._

object Dijkstra {
  def dijkstra[N, E <: WeightedEdge[N]](graph: Graph[N, E], source: N): Either[String, Array[Double]] = {
    // Check for negative weighted edges
    val hasNegativeWeight = graph.getAllEdges.exists(_.weight < 0)
    if (hasNegativeWeight) {
      Left("Error, graph contains negative value")
    } else {

      val nodes = graph.getAllNodes.toList
      val n = nodes.size
      val distances = Array.fill(n)(Double.PositiveInfinity) // Initialize distances array with infinity
      val visited = Array.fill(n)(false)          // Track visited nodes
      val nodesMap = nodes.zipWithIndex.toMap     // Map nodes to their indices for quick access
  
      // Initialize distance from source node
      val sourceIndex = nodesMap(source)
      distances(sourceIndex) = 0
  
      // Process all nodes
      for (_ <- 0 until n) {
        // Find the node with the minimum distance that hasn't been visited
        var minDist = Double.PositiveInfinity
        var minIndex = -1
        for (i <- 0 until n) {
          if (!visited(i) && distances(i) < minDist) {
            minDist = distances(i)
            minIndex = i
          }
        }
  
        // If no node found, break (graph is disconnected or all reachable nodes are processed)
        if (minIndex == -1) {
          return Right(distances)
        }
  
        // Mark the selected node as visited
        visited(minIndex) = true
  
        // Update distances for all neighbors of the selected node
        val currentNode = nodes(minIndex)
        val neighbors = graph.getNeighbors(currentNode)
        for (neighbor <- neighbors) {
          val neighborIndex = nodesMap(neighbor)
          val edge = graph.getAllEdges.find(e => e.from == currentNode && e.to == neighbor).getOrElse(null)
          val weight = if (edge != null) edge.weight else Double.PositiveInfinity
  
          val newDistance = distances(minIndex) + weight
          if (newDistance < distances(neighborIndex)) {
            distances(neighborIndex) = newDistance
          }
        }
      }
  
      Right(distances.map(_.toDouble))
    }
  }
}
