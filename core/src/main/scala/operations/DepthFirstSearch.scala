package operations

import Grph._

object DepthFirstSearch {
  def dfs[N, E <: Edge[N]](graph: Graph[N, E], start: N): Set[N] = {
    def dfsHelper(node: N, visited: Set[N]): Set[N] = {
      if (visited.contains(node)) visited
      else {
        val neighbors = graph.getNeighbors(node)
        neighbors.foldLeft(visited + node)((acc, neighbor) => dfsHelper(neighbor, acc))
      }
    }

    dfsHelper(start, Set.empty)
  }
}

