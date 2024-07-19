# Scala Project 

### Goal of the project

We had to design and implement a graph data structure library with various operations, integrate it into a ZIO 2
application

## 1. Datastructure

### Graph

The first thing we did was an implemention of a `trait` **Graph** to later use case classes because we wanted to have either a graph or an undigraph:
```scala
trait Graph[N, E <: Edge[N]]:
        def getAllNodes: Set[N]
        def getAllEdges: Set[E]
        def getNeighbors(node: N): Set[N]
        def addEdge(edge: E): Graph[N,E]
        def removeEdge(edge: E): Graph[N,E]
```
Here `Graph[N, E <: Edge[N]]` means that `N` is a generic type that can take any type such as Int, String etc... and `E` can take every type that extends `Edge[N]` (defined later).

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

As for the **Graph**, we create an Object to define **implicit** methods helpful to serialize an Edge:

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

A `Node` is only represented by a generic type N in **Edge** and **Graph**

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

```Scala
def topologicalSort[N, E <: Edge[N]](graph: DiGraph[N, E]): Either[String, List[N]] = {
    val nodes = graph.getAllNodes.toList
    var visited = Set[N]()
    var stack = List[N]()

    if(hasCycle(graph)) {
      return Left("Error, graph contains a cycle")
    }  
}
```
We used Right and left to notice an error.
#### Floyd

Floyd’s algorithm, also known as the Floyd-Warshall algorithm, is used for finding the shortest paths between all pairs
of `Node` in a weighted graph.

#### Dijkstra

Dijkstra’s algorithm is used for finding the shortest path from a single source `Node` to all other `Nodes` in a
weighted graph with non-negative weights.
This operation also return an `Either` because he can't take negative weights so it returns an error.

## 3. ZIO


#### 3.1 Implementation with ZIO

To implement our structure, we had the choice: either use an API or in the terminal. **We decided to use an API** so it is easier to test. As a result, we used `zio-http` library

#### 3.2 State management

Along with the API we provided, we needed to store the state of any graph. We looked on the `ZIO documentation` and found out that using a **ZIO state service** is really helpful to do that.

We need two things: 
  - a state service that describe what operations we want to perform. As for a REST API, we want the **CRUD operations** to be implemented. We implemented a **trait** with basic operations that will help us to do that

  ```Scala
    trait GraphStateService[N, E <: Edge[N]] {
      def getGraph: UIO[Graph[N, E]]
      def setGraph(graph: Graph[N, E]): UIO[Unit]
      def clearGraph: UIO[Unit]
    }
  ```

  - A layer from ZIO called `Zlayer` that will create a Ref and store the state of the graph.

  ```Scala
      object GraphStateService {
        def layer[N: Tag, E <: Edge[N]: Tag](initialGraph: Graph[N, E]): ULayer[GraphStateService[N, E]] = {
          ZLayer.fromZIO {
            for {
              ref <- Ref.make(initialGraph)
            } 
  ```

  With this, we now can create our routes. We only have to provide our server the state service and the routes we want to be available.

   **_NOTE:_** From this point, the server **DOESN'T ACCEPT GENERIC TYPES ANYMORE**, meaning that you have to provide it what types of nodes and edges you want. We decided to implement `Int` routes (See code below). The API **won't work with string**, we could do it only by copying and paste the code and changing the type.

   ```Scala
    val emptyGraphUnweighted = DiGraph.empty[Int, UnweightedEdge[Int]]
  val graphLayerUnweighted = GraphStateService.layer[Int, UnweightedEdge[Int]](emptyGraphUnweighted)

  def run =
    Server.serve(digraphWeighted ++ digraphUnweighted ++ undigraphWeighted ++ undigraphUnweighted ++ operationUnweighted ++ operationWeighted)
      .provide(
        Server.default,
        graphLayerUnweighted,
        graphLayerWeighted
      )
   ```
#### 3.3 Routes

  First, let's take a look at what a route looks like:

  ```Scala
  object DigraphUnweighted {

  def routes[N: Tag: JsonDecoder: JsonEncoder, E <: Edge[N]: Tag: JsonDecoder: JsonEncoder] = {
    Routes(

      // POST /digraph
      Method.POST / "digraphUnweighted" -> handler { (req: Request) => {
        for {
          body <- req.body.asString  // Read Json body
          response <- body.fromJson[DiGraph[N, E]] match { //Try to parse it
            case Left(error) => ZIO.succeed(Response.text(s"Failed to parse graph: $error"))
            case Right(graph) =>
              for {
                _ <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.setGraph(graph))  // Update graph state
                currentGraph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  //For the confirmation message
              } yield Response.text(s"Updated graph: $currentGraph")
          }
        } yield response
      }}.sandbox,
  ```
  It uses the `Routes` object from ZIO's documentation. Above is an exemple of a POST method to store a digraph

  Below is a **list of routes** you can use and what they are used for:
  
  - /digraphUnweighted --> **POST**: set the json (body) directed graph unweighted to the state (See example below) 

  - /digraphWeighted --> **POST**: set the json (body) directed graph weighted to the state

  - /undigraphUnweighted --> **POST**: set the json (body) undirected graph unweighted to the state

  - /undigraphUnweighted --> **POST**: set the json (body) undirected graph unweighted to the state

  You **can add** to any of ``the above routes`` the following paths:

  - /add --> **PUT**: Add the json (body) edge sent to the current graph
  - /remove --> **PUT**:  remove the json (body) edge sent to the current graph

  **EXAMPLE**

  Fetch 
  ```
  http://127.0.0.1:8080/undigraphWeighted 
  ```
  With body:
  ```
  {"nodes":[1,2,3,4],"edges":[{"from":1,"to":2,"weight":1.0},{"from":2,"to":3,"weight":1.0},{"from":1,"to":3,"weight":5.0},{"from":3,"to":4,"weight":9.0}]}

  ```
  To store this graph. Then you can fetch:
  ```
  http://127.0.0.1:8080/undigraphWeighted/add
  ```
  With body:
  ```
  {"from":4,"to":1,"weight":-2.0}
  ```
  To add an edge from node 4 to 1 with weight -2. You can then remove it with the exact same body and /remove instead of /add. For ``unweighted edges``, **remove the "weight" key** in the json for each node

  **_NOTE:_** Adding an edge between one or two unexistant nodes **will create them**. Removing an edge between two existant nodes won't delete a node.
  
  To perform operations on graphs or visualize them, use depending on the type of edges of your graph:
  
  - /weighted/graphViz --> **GET**: Dispaly the dot of the weighted graph

  - /unweighted/graphViz --> **GET**: Dispaly the dot of the unweighted graph

  **_NOTE:_** Use "graphViz" and not "graphviz"

  - /weighted/DFS/{node number} --> **GET**: Display the DFS of the weighted graph for the specified node

  - /unweighted/DFS/{node number} --> **GET**: Display the DFS of the unweighted graph for the specified node

  - /weighted/BFS/{node number} --> **GET**: Display the BFS of the weighted graph for the specified node
  
  - /unweighted/BFS/{node number} --> **GET**: Display the BFS of the unweighted graph for the specified node

  - /weighted/cycle --> **GET**: check if the weighted graph has cycle
  
  - /unweighted/cycle --> **GET**: check if the unweighted graph has cycle

  - /weighted/topological --> **GET**: Return a topological order for the weighted graph
  
  - /unweighted/topological --> **GET**: Return a topological order for the unweighted graph

  EXAMPLE

  Fetch 
  ```
  http://127.0.0.1:8080/weighted/graphViz 
  ```
  With the previous stored graph to get
  ```
  digraph G {
	1;
	2;
	3;
	4;
	1 -> 2[label = 1.0]
	2 -> 3[label = 1.0]
	1 -> 3[label = 5.0]
	3 -> 4[label = 9.0]
}
  ```

  **_IMPORTANT NOTE:_** You have to either put ``weighted`` or ``unweighted`` depending on the edge type of the graph you previously stored, otherwise you won't have the result.

  Also, remember to put the ``correct verbs`` ( POST, PUT, GET, DELETE or it won't work). Some of the above path will intentionally return an error if you use the incorrect graph type (Example: **no topological orde**r in an``undirected graph``, as well as cycles for undirected graph)

  Finally we have the last routes:

  - /weighted/floyd --> **GET**: Compute and return the floyd n*n matrix of shortest paths

  - /weighted/dijkstra/{node number} --> **GET**: Compute and return the shortest path to reach this node from any node. 

  - /weighted/delete--> **DELETE**: Remove the stored graph from the state

  - /unweighted/delete--> **DELETE**: Remove the stored graph from the state

  **_NOTE:_** There are no unweighted path for floyd or dijkstra due to the nature of the algorithm that only takes weighted graphs. See below how to read the results

  EXAMPLE

  Fetch 
  ```
  http://127.0.0.1:8080/weighted/floyd
  ```
  With the previous example to get
  ```
  List(List(0.0, 1.0, 2.0, 11.0), List(Infinity, 0.0, 1.0, 10.0), List(Infinity, Infinity, 0.0, 9.0), List(Infinity, Infinity, Infinity, 0.0))
  ```

  You have to read ``List[i][j]`` to get the sortest path from node i to node j. For example, shortest path from 1 to 4 cost 11. Shortest path from 2 to 2 cost 0 (same node). Shortest path from node 3 to 1 doesn't exist so it **is displayed "Infinity"**
  
  Fetch 
  ```
  http://127.0.0.1:8080/weighted/dijkstra/2
  ```
  With the previous example to get
  ```
  Infinity
0.0
1.0
10.0
```

 This will display the shortest path ``from the selected`` node to ``each node of the graph``. For example here, 2 to 1 is Infinity because unreachable, 2 to 2 is 0, 2 to 3 is 1, 2 to 4 is 10.


## 4. Build/Run/Test

This is a normal sbt project. You can compile code with `sbt compile`, run it with `sbt run`, and `sbt console` will start a Scala 3 REPL.

#### Build

To start our project simply run `sbt core/run` first, so it compiles everything. **Feel free to add anything you want** in the Main.scala located at `core/src/main/scala/grph/Main.scala` to test our functions.

#### API
To run the API, simple run `sbt zio/run` after compiling the core and the server will start on **127.0.0.1:8080**. You can then fully use the API shown before with ``Postman / curl`` or whatever tool you want

#### Test
To run the tests launch the sbt terminal by writing `sbt` then write in the **sbt terminal** `project core` and finally write `test` in the sbt terminal

#### Test coverage
The tests cover all the case that we could think of, there could be some oversight. We tried to use a librairy to do the test coverage but unfortunatly it supports only Scala 3.2.x and out project is in 3.4.x