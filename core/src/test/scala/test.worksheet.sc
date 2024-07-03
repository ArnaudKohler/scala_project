import Grph._

val n1 = 1
val n2 = 2
val n3 = 3

val s1 = Set(WeightedEdge(n1, n2, 1), WeightedEdge(n2, n3, 2), WeightedEdge(n3, n1, 3))

val d1 = DiGraph(Set(n1, n2, n3), s1)

d1.edges
d1.nodes

d1.getAllNodes
d1.getNeighbors(n1) 
d1.toDot

val d2 = d1.addEdge(WeightedEdge(n1, n3, 1)) 
d2

val d3 = d2.addEdge(WeightedEdge(n1, 4, 1)) 
d2.toDot
import zio.json._
d1.toJson

"""{"nodes":[1,2,3],"edges":[{"from":1,"to":2,"weight":1.0},{"from":2,"to":3,"weight":2.0},"from":3,"to":1,"weight":3.0}]}""".fromJson[DiGraph[Int, WeightedEdge[Int]]]

d3.toDot