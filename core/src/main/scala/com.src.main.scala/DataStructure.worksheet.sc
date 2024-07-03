sealed trait Graph[Node]:
    def getAllNodes: Set[Node]
    def getAllEdges: Set[Edge[Node]]
    def getNeighbors(node: Node): Set[Node]
    def addEdge(edge: Edge[Node]): Graph[Node]
    def removeEdge(edge: Edge[Node]): Graph[Node]

trait Edge[Node]:
    def from: Node
    def to: Node
    def weight: Number

case class Node[A](value: A)

case class DiGraph[Node](nodes: Set[Node], edges: Set[Edge[Node]]) extends Graph[Node]:
    def getAllNodes: Set[Node] = nodes
    def getAllEdges: Set[Edge[Node]] = edges
    def getNeighbors(node: Node): Set[Node] = edges.filter(_.from == node).map(_.to)
    def addEdge(edge: Edge[Node]): DiGraph[Node] = DiGraph(nodes, edges + edge)
    def removeEdge(edge: Edge[Node]): DiGraph[Node] = DiGraph(nodes, edges - edge)

case class UndirectedGraph[Node](nodes: Set[Node], edges: Set[Edge[Node]]) extends Graph[Node]:
    def getAllNodes: Set[Node] = nodes
    def getAllEdges: Set[Edge[Node]] = edges
    def getNeighbors(node: Node): Set[Node] = edges.filter(e => e.from == node || e.to == node).map(e => if e.from == node then e.to else e.from)
    def addEdge(edge: Edge[Node]): UndirectedGraph[Node] = UndirectedGraph(nodes, edges + edge)
    def removeEdge(edge: Edge[Node]): UndirectedGraph[Node] = UndirectedGraph(nodes, edges - edge)

    case class WeightGraph[Node](nodes: Set[Node], edges: Set[Edge[Node]]) extends Graph[Node]:
        def getAllNodes: Set[Node] = nodes
        def getAllEdges: Set[Edge[Node]] = edges
        def getNeighbors(node: Node): Set[Node] = edges.filter(_.from == node).map(_.to)
        def addEdge(edge: Edge[Node]): WeightGraph[Node] = WeightGraph(nodes, edges + edge)
        def removeEdge(edge: Edge[Node]): WeightGraph[Node] = WeightGraph(nodes, edges - edge)

    val node1 = Node(1)
    val node2 = Node(2)
    val node3 = Node(3)
    val edge1 = Edge(node1, node2, _)
    val edge2 = Edge(node1, node3, _)
    val edge3 = Edge(node2, node3, _)
    val diGraph = DiGraph(Set(node1, node2, node3), Set(edge1, edge2, edge3))

    println(diGraph.getNeighbors(node1))
