import Grph._
import zio.json._
import Grph.Graphviz.toDot
import operations.BreadthFirstSearch._
import operations.DepthFirstSearch._
import operations.TopologicalSorting._
import operations.CycleDetection._
import operations.Floyd._

object Main extends App {

    println("Hello, World!")
    
    val n1 = 1
    val n2 = 2
    val n3 = 3

    val s1 = Set(WeightedEdge(n1, n2, 1), WeightedEdge(n2, n3, 2), WeightedEdge(n3, n1, 3))

    val d1 = DiGraph(Set(n1, n2, n3), s1)

    println(d1.getNeighbors(n1))

    val d2 = d1.addEdge(WeightedEdge(n1, n3, 1))
    println(d2.toDot)
}
