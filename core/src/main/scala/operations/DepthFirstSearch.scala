package operations

import Grph._

object DepthFirstSearch {
  def dfs[N, E <: Edge[N]](graph: Graph[N, E], start: N): Set[N] = {
    println("DFS")
    def dfsHelper(node: N, visited: Set[N]): Set[N] = {
      if (visited.contains(node))  // Si le nœud a déjà été visité, retourne l'ensemble visité
        println("Already visited: " + node)
        visited
      else {
        println("Not visited: " + node)
        val neighbors = graph.getNeighbors(node) // Récupère tous les voisins du nœud
        println("Neighbors: " + neighbors)
        neighbors.foldLeft(visited + node)((acc, neighbor) => dfsHelper(neighbor, acc)) // Appelle récursivement dfsHelper sur tous les voisins du nœud
      }
    }

    dfsHelper(start, Set.empty)
  }
}
