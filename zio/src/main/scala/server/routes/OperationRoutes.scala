package server.routes

import zio._
import zio.http._
import zio.json._
import Grph._
import Graphviz._
import server.GraphStateService
import operations.DepthFirstSearch._
import operations.BreadthFirstSearch._
import operations.TopologicalSorting._
import operations.CycleDetection._
import operations.Floyd._
import operations.Dijkstra._

object OperationRoutes {

  def routes[N: Tag: JsonDecoder: JsonEncoder, E <: Edge[N]: Tag: JsonDecoder: JsonEncoder] = {
    Routes(

      // GET /DFS --> get the Depth First Search of the current graph
      Method.GET / "DFS" / string("startingNode") -> handler { (startingNode: String, req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          DFS <- graph match {
            case grph: Graph[N, E] => ZIO.succeed(dfs(grph, startingNode.asInstanceOf[N])) // Proceed DFS
            case null => ZIO.succeed("Unknown graph type")
        }
        } yield Response.text(DFS.toString()) //Return the result
      }}.sandbox,

     // GET /BFS --> get the Breadth First Search of the current graph
     Method.GET / "BFS" / string("startingNode") -> handler { (startingNode: String, req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          BFS <- graph match {
            case grph: Graph[N, E] => ZIO.succeed(bfs(grph, startingNode.asInstanceOf[N])) // Proceed BFS
            case null => ZIO.succeed("Unknown graph type")
          }
        } yield Response.text(BFS.toString()) //Return the result
      }}.sandbox,

      // GET /topological --> get the topological order of the current graph
      Method.GET / "topological" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          topological <- graph match {
            case grph: DiGraph[N, E] => ZIO.succeed(topologicalSort(grph)) // Proceed topological order
            case _ => ZIO.succeed("Not allowed graph type")
          }
        } yield Response.text(topological.toString()) //Return the result
      }}.sandbox,

      //GET /cycle --> get if the current graph has a cycle
      Method.GET / "cycle" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          cycle <- graph match {
            case grph: DiGraph[N, E] => ZIO.succeed(hasCycle(grph)) // Check if there is a cycle
            case _ => ZIO.succeed("Not allowed graph type")
          }
        } yield Response.text(cycle.toString()) //Return the result
      }}.sandbox,

        //GET /floyd --> get the shortest path between all nodes of the current graph
        Method.GET / "floyd" -> handler { (req: Request) => {
            for {
            graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
            floyd <- graph match {
                case digraph: DiGraph[N, WeightedEdge[N]] => 
                    println("TEST DIGRPAGH")
                    println("floyd" + floyd(digraph))
                    ZIO.succeed(floyd(digraph)) // Proceed Floyd
                case undigraph: Undigraph[N, WeightedEdge[N]] => 
                    println("TEST UNDIGRPAGH")
                    println("floyd" + floyd(undigraph))
                    ZIO.succeed(floyd(undigraph)) // Proceed Floyd
                case _ => ZIO.succeed("Not allowed graph type")
            }
            } yield Response.text(floyd.toString()) //Return the result
        }}.sandbox,
     
        //GET /dijkstra --> get the shortest path between two nodes of the current graph
        Method.GET / "dijkstra" -> handler { (req: Request) => {
            for {
            graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
            dijkstra <- graph match {
                case grph: Graph[N, WeightedEdge[N]] => 
                    println("dijkstra")
                    ZIO.succeed(dijkstra(grph, grph.getAllNodes.head)) // Proceed Dijkstra
                case _ => ZIO.succeed("Not allowed graph type")
            }
            } yield Response.text(dijkstra.toString()) //Return the result
        }}.sandbox


    )
  }
}
