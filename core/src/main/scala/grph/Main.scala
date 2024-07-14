import Grph._
import operations.Dijkstra._
import Graphviz._
import operations.TopologicalSorting._

object Main extends App {

    val graph = DiGraph.empty[Int, UnweightedEdge[Int]]
    val no1 = 1
    val no2 = 2
    val no3 = 3
    val no4 = 4
    val no5 = 5
    val e1 = UnweightedEdge(no1, no2)
    val e2 = UnweightedEdge(no2, no4)
    val e3 = UnweightedEdge(no1, no3)
    val e4 = UnweightedEdge(no4, no5)
    val e5 = UnweightedEdge(no3, no4)
    val u = graph.addEdge(e1).addEdge(e2).addEdge(e3).addEdge(e4).addEdge(e5)

    println(u.toDot)
    println(topologicalSort(u))
    val n1 = 1
    val n2 = 2
    val n3 = 3

    val s1 = Set(WeightedEdge(n1, n2, 1), WeightedEdge(n2, n3, 2), WeightedEdge(n3, n1, 3))
    val d1 = DiGraph(Set(n1, n2, n3), s1) 

    val d2 = d1.addEdge(WeightedEdge(n1, n3, 1))
    println(d1.toDot)
    
    // Calcul des distances depuis le nœud source n1

    // Affichage des distances sous forme de chaîne de caractères
    dijkstra(d1, n1) match {
      case Left(errMsg) => println(errMsg)
      case Right(distances) => println(s"Distances from node $n1: [${distances.mkString(", ")}]")
    }
}
