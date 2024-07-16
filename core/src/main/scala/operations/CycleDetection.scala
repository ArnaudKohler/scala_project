package operations

import Grph._
import operations.DepthFirstSearch._

object CycleDetection {
  def hasCycle[N, E <: Edge[N]](graph: Graph[N, E]): Boolean = {
    var visited = Set[N]()
    var recStack = Set[N]()

    def cycleHelper(node: N): Boolean = { 
      if (!visited.contains(node))  // If the node has not been visited yet, visit it
        visited += node // Mark node as visited and add to the stack
        recStack += node


        val neighbors = graph.getNeighbors(node) // Get all neighbors of the node and iterate over them
        for (neighbor <- neighbors) 
          if (!visited.contains(neighbor) && cycleHelper(neighbor)) true // If the neighbor has not been visited yet, visit it
          else if (recStack.contains(neighbor)) true //else if it is in the stack, we have a cycle
        
      

      recStack -= node // Mark node as done and remove from the stack
      false
    }

    val nodes = graph.getAllNodes
    for (node <- nodes) 
      if (cycleHelper(node)) true
    false
  }
}