package operations
import Grph._

object BreadthFirstSearch {
  def bfs[N, E <: Edge[N]](graph: Graph[N, E], start: N): Set[N] = {
    def bfsHelper(queue: List[N], visited: Set[N]): Set[N] = {
      if (queue.isEmpty) visited
      else {
        val node = queue.head
        val neighbors = graph.getNeighbors(node)
        val newQueue = queue.tail ++ neighbors.filterNot(visited.contains)
        bfsHelper(newQueue, visited + node)
      }
    }

    bfsHelper(List(start), Set.empty)
  }
}