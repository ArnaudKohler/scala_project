import Grph._
import operations.Dijkstra._
import Graphviz._
import operations.TopologicalSorting._
import operations.CycleDetection._
import operations.Floyd._
import operations.TopologicalSorting
import operations.DepthFirstSearch._

object Main extends App {

        val graph = DiGraph.empty[Int, UnweightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val n4 = 4
        val n5 = 5
        val e1 = UnweightedEdge(n1, n2)
        val e2 = UnweightedEdge(n2, n4)
        val e3 = UnweightedEdge(n1, n3)
        val e4 = UnweightedEdge(n4, n5)
        val e5 = UnweightedEdge(n3, n4)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val result = TopologicalSorting.topologicalSort(u)
        val dfsResult = dfs(u, n1)
        //print(dfsResult)
    }
