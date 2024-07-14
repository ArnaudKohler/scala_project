package operations
import Grph._

object BreadthFirstSearch {
  def bfs[N, E <: Edge[N]](graph: Graph[N, E], start: N): Set[N] = {
    def bfsHelper(queue: List[N], visited: Set[N]): Set[N] = {
      if (queue.isEmpty) visited // no left to vist: return all visited nodes
      else {
        val node = queue.head // get the next node to visit
        val neighbors = graph.getNeighbors(node) // get all neighbors of the node
        val newQueue = queue.tail ++ neighbors.filterNot(visited.contains) // add all neighbors to the queue that have not been visited
        bfsHelper(newQueue, visited + node) // recursively call bfsHelper with the new queue and the node added to the visited set
      }
    }

    bfsHelper(List(start), Set.empty) // start the bfsHelper with the start node and an empty visited set
  }
}