package operations

import Grph._

object DepthFirstSearch {
  def dfs[N, E <: Edge[N]](graph: Graph[N, E], start: N): Set[N] = {
    def dfsHelper(node: N, visited: Set[N]): Set[N] = {
      if (visited.contains(node)) visited // if the node has already been visited, return the visited set
      else {
        val neighbors = graph.getNeighbors(node) // get all neighbors of the node
        neighbors.foldLeft(visited + node)((acc, neighbor) => dfsHelper(neighbor, acc)) // recursively call dfsHelper on all neighbors of the node
      }
    }
    dfsHelper(start, Set.empty)
  }
}

