package operations

import Grph._
import operations.DepthFirstSearch._

object CycleDetection {
  def hasCycle[N, E <: Edge[N]](graph: Graph[N, E]): Boolean = {
    def cycleHelper(node: N, visited: Set[N], recStack: Set[N]): Boolean = {
      if (recStack.contains(node)) {
        true
      } else if (visited.contains(node)) {
        false
      } else {
        val newVisited = visited + node
        val newRecStack = recStack + node

        graph.getNeighbors(node).exists(neighbor => 
          cycleHelper(neighbor, newVisited, newRecStack)
        )
      }
    }

    graph.getAllNodes.exists(node => 
      cycleHelper(node, Set(), Set())
    )
  }
}