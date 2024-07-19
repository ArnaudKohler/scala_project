# Scala Project 

### Goal of the project

We had to design and implement a graph data structure library with various operations, integrate it into a ZIO 2
application

## 1. Datastructure

### Graph

The first thing we did was a implement a `trait` **Graph**:
```scala
trait Graph[N, E <: Edge[N]]:
        def getAllNodes: Set[N]
        def getAllEdges: Set[E]
        def getNeighbors(node: N): Set[N]
        def addEdge(edge: E): Graph[N,E]
        def removeEdge(edge: E): Graph[N,E]
```
Here `Graph[N, E <: Edge[N]]` means that `N` is a generic type and can take any type and `E` can take every type that extends `Edge[N]` where `N` is also a generic type

We have two different type of **Graph**:

- Digraph (for directed graph)
- Undigraph (for undirected graph)

We implemented two `case class`, one for each type of **Graph**:
```Scala
case class Undigraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N,E] {
  def getAllNodes: Set[N] = nodes
  def getAllEdges: Set[E] = edges
  def getNeighbors(node: N): Set[N] = edges.filter(e => e.from == node || e.to == node).map(e => if e.from == node then e.to else e.from)
  def addEdge(edge: E): Undigraph[N, E] = 
    if (nodes.contains(edge.from) && nodes.contains(edge.to))
      Undigraph(nodes, edges + edge)
    else
      Undigraph(nodes + edge.from + edge.to, edges + edge)
  def removeEdge(edge: E): Undigraph[N,E] = 
    if(edges.contains(edge))
      Undigraph(nodes, edges - edge)
    else
      Undigraph(nodes, edges)
}
```

```Scala
case class DiGraph[N, E <: Edge[N]](nodes: Set[N], edges: Set[E]) extends Graph[N, E] {
    def getAllNodes: Set[N] = nodes
    def getAllEdges: Set[E] = edges
    def getNeighbors(node: N): Set[N] = edges.filter(_.from == node).map(_.to)
    def addEdge(edge: E): DiGraph[N, E] = 
      if (nodes.contains(edge.from) && nodes.contains(edge.to))
        DiGraph(nodes, edges + edge)
      else
        DiGraph(nodes + edge.from + edge.to, edges + edge)
    def removeEdge(edge: E): DiGraph[N, E] = 
      if (edges.contains(edge))
        DiGraph(nodes, edges - edge)
      else
        DiGraph(nodes, edges)
}
```

These case class inherit five methods from **Graph**:

- **getAllNodes**, that get all nodes in the graph
- **getAllEdges**, that get all edges in the graph
- **addEdge**, that add an edge to the graph (creating nodes if they don't exist)
- **removeEdge**, that remove an edge from the graph
- **getNeighbours**, that retrieve all the neighbours nodes of a node

We also have implement an **Object** with **implicit** methodes surch as:

- **encoder**, that allow to serialize the graph into a JSON object
- **decoder**, that allow to deserialize the graph from a JSON object

And useful methods surch as: 
- **empty**, that allow to create a empty graph

```Scala
object DiGraph {
    implicit def diGraphDecoder[N: JsonDecoder, E <: Edge[N]: JsonDecoder]: JsonDecoder[DiGraph[N, E]] = DeriveJsonDecoder.gen[DiGraph[N, E]]
    implicit def diGraphEncoder[N: JsonEncoder, E <: Edge[N]: JsonEncoder]: JsonEncoder[DiGraph[N, E]] = DeriveJsonEncoder.gen[DiGraph[N, E]]
    def empty[N, E <: Edge[N]]: DiGraph[N, E] = DiGraph(Set.empty[N], Set.empty[E])
}
```

```Scala
object Undigraph {
  implicit def UndigraphDecoder[N: JsonDecoder, E <: Edge[N]: JsonDecoder]: JsonDecoder[Undigraph[N, E]] = DeriveJsonDecoder.gen[Undigraph[N, E]]
  implicit def UndigraphEncoder[N: JsonEncoder, E <: Edge[N]: JsonEncoder]: JsonEncoder[Undigraph[N, E]] = DeriveJsonEncoder.gen[Undigraph[N, E]]
  def empty[N, E <: Edge[N]]: Undigraph[N, E] = Undigraph(Set.empty[N], Set.empty[E])
}
```

Obviously, a graph is composed of nodes that a related one an other by edges

### Edge

As for the **Graph**, we created a **Edge** `trait` 
```Scala
trait Edge[N] {
  def from: N
  def to: N
  def weight: Double
}
```

There are two types of edges:

- **UnweightedEdge**, that hasn't any weight attached
- **WeightedEdge**, that has a weight

As for the **Graph** we created two `case class`:
```Scala
case class UnweightedEdge[N](from: N, to: N) extends Edge[N] {
  def weight: Double = 1
}
```
We set at 1 the **weight** for `UnweightedEdge` because we don't care of the **weight** for this type of `Edge`
```Scala
case class WeightedEdge[N](from: N, to: N, weight: Double) extends Edge[N]
```

As for the **Graph**, we create an Object to define **implicit** methods:

```Scala
object WeightedEdge {
  implicit def weightedEdgeDecoder[N: JsonDecoder]: JsonDecoder[WeightedEdge[N]] = DeriveJsonDecoder.gen[WeightedEdge[N]]
  implicit def weightedEdgeEncoder[N: JsonEncoder]: JsonEncoder[WeightedEdge[N]] = DeriveJsonEncoder.gen[WeightedEdge[N]]
}
```
```Scala
object UnweightedEdge {
  implicit def unweightedEdgeDecoder[N: JsonDecoder]: JsonDecoder[UnweightedEdge[N]] = DeriveJsonDecoder.gen[UnweightedEdge[N]]
  implicit def unweightedEdgeEncoder[N: JsonEncoder]: JsonEncoder[UnweightedEdge[N]] = DeriveJsonEncoder.gen[UnweightedEdge[N]]
}
```
### Node

A `Node` is just a generic type in **Edge** and **Graph**, it doesn't have a class because it's only a basic type like int, String etc...

## 2. Operations

We implemented six type of operations that you can do on **Graphs**

#### Breadth First Search (BFS)

DFS is an algorithm for traversing or searching tree or graph data structures. It starts at a source `Node` and explores
as far as possible along each branch before backtracking.

#### Depth First Search (DFS)

BFS is an algorithm for traversing or searching tree or graph data structures. It starts at a source `Node` and explores
all the neighbor `Nodes` at the present depth before moving on to `Nodes` at the next depth level.

#### Cycle detection

Cycle detection in a graph is the process of finding a loop in the graph where a `Node` is reachable from itself.
It uses DFS.

#### Topological Sorting

Topological sorting is the linear ordering of `Nodes` in a directed graph where for every directed edge from `Node` u
to `Node` v, u comes before v in the ordering.
**Graph** can't contain a cycle that's why this operation return a `Either[String, List[N]]`, either a String with an error message if the **Graph** has a cycle or the **List** created by the algorithm if there isn't any cycle

#### Floyd

Floyd’s algorithm, also known as the Floyd-Warshall algorithm, is used for finding the shortest paths between all pairs
of `Node` in a weighted graph.

#### Dijkstra

Dijkstra’s algorithm is used for finding the shortest path from a single source `Node` to all other `Nodes` in a
weighted graph with non-negative weights.
This operation also return an `Either` because he can't take negative weights so it returns an error.

## 3. ZIO


### Usage

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

To start our project go to the sbt terminal using `sbt` and type `project core` or `project zio` and then run it