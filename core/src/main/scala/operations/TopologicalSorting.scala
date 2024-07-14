package operations

import Grph._
import operations.DepthFirstSearch._

object TopologicalSorting {
  def topologicalSort[N, E <: Edge[N]](graph: Graph[N, E]): List[N] = {
    val nodes = graph.getAllNodes
    var visited = Set[N]()
    var stack = List[N]()

    def topologicalSortHelper(node: N): Unit = {
      if (!visited.contains(node)) {
        val result = dfs(graph, node) // get all nodes reachable from the current node
        visited ++= result // add all reachable nodes to the visited set
        stack = result.toList ::: stack // add all reachable nodes to the stack
      }
    }

    nodes.foreach(topologicalSortHelper) // call topologicalSortHelper on all nodes in the graph
    stack.reverse
  }
}
