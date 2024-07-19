package operations

import Grph._

import DepthFirstSearch._
import operations.CycleDetection.hasCycle

object TopologicalSorting {

  def topologicalSort[N, E <: Edge[N]](graph: DiGraph[N, E]): Either[String, List[N]] = {
    val nodes = graph.getAllNodes.toList
    var visited = Set[N]()
    var stack = List[N]()

    if(hasCycle(graph)) {
      return Left("Error, graph contains a cycle")
    }  

    def topologicalSortHelper(node: N): Unit = {
      
      if (!visited.contains(node)) {
        visited += node
        val neighbors = dfs(graph, node) // Use DFS to get all neighbors of the node
        neighbors.foreach(n => topologicalSortHelper(n))
        stack = node :: stack // Add the node to the stack at the beginning
      }
    }
    nodes.foreach(topologicalSortHelper) // call topologicalSortHelper on all nodes in the graph
    Right(stack)
  }
}
