import Grph._
import zio.json._
import Grph.Graphviz._
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

    val d2 = d1.addEdge(WeightedEdge(5,7,2))
    
    println("test" + d2)
    val test = d2.toJson
    
    val jsonString = """{"nodes":[1,2,3],"edges":[{"from":1,"to":2,"weight":1.0},{"from":2,"to":3,"weight":2.0},{"from":3,"to":1,"weight":3.0},{"from":1,"to":3,"weight":1.0}]}"""
    val d3 = jsonString.fromJson[DiGraph[Int, WeightedEdge[Int]]]

    /* dfs(d2,1).foreach(println)
    bfs(d2,1).foreach(println) */
    println(topologicalSort(d2))
    val s2 = Set(WeightedEdge(n1, n2, 1), WeightedEdge(n2, n1, -21), WeightedEdge(n2, n3, 1))
    val d4 = DiGraph(Set(n1, n2,n3), s2)
    println(hasCycle(d4))

    println(d4.toDot)
    println(floyd(d4))
    


}
