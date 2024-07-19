import Grph._
import operations.Dijkstra._
import Graphviz._
import operations.TopologicalSorting._
import operations.CycleDetection._
import operations.Floyd._
import operations.TopologicalSorting
import operations.DepthFirstSearch._
import zio.json._

object Main extends App {

        val graph = DiGraph.empty[Int, WeightedEdge[Int]]
        val n1 = 1
        val n2 = 2
        val n3 = 3
        val n4 = 4
        val n5 = 5
        val e1 = WeightedEdge(n1, n2,1)
        val e2 = WeightedEdge(n2, n4,2)
        val e3 = WeightedEdge(n1, n3,0)
        val e4 = WeightedEdge(n4, n5,1)
        val e5 = WeightedEdge(n3, n4,3)
        val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)
        val result = TopologicalSorting.topologicalSort(u)
        val dfsResult = dfs(u, n1)
        print(floyd(u))
        val json = u.toJson
        print(json)
    }
  