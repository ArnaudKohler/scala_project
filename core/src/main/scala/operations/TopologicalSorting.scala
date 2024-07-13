package operations

import Grph._

object TopologicalSorting {
  def topologicalSort[N, E <: Edge[N]](graph: Graph[N, E]): List[N] = {
    def topologicalSortHelper(node: N, visited: Set[N], stack: List[N]): List[N] = {
      if (visited.contains(node)) stack
      else {
        val neighbors = graph.getNeighbors(node)
        neighbors.foldLeft(stack)((acc, neighbor) => topologicalSortHelper(neighbor, visited + node, acc))
      }
    }

    val nodes = graph.getAllNodes
    nodes.foldLeft(List.empty[N])((acc, node) => topologicalSortHelper(node, Set.empty, acc))
  }
} 