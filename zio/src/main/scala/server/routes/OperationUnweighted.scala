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

object OperationUnweighted {

  def routes[N: Tag: JsonDecoder: JsonEncoder, E <: Edge[N]: Tag: JsonDecoder: JsonEncoder] = {
    Routes(

      Method.GET / "unweighted" / "graphViz" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          graphViz <- graph match { //Check if it's a DiGraph or an Undigraph
            case undigraph: Undigraph[N, E] => 
              println("undigraph")
              ZIO.succeed(undigraph.toDot)
            case digraph: DiGraph[N, E] => 
              println("digraph")
              ZIO.succeed(digraph.toDot)
            case _ => ZIO.succeed("Unknown graph type")
          }
        } yield Response.text(graphViz) //Return the graphViz
      }}.sandbox,

      Method.DELETE / "unweighted" / "delete" -> handler { (req: Request) => {        
        for {
          service <- ZIO.service[GraphStateService[N, E]]  // get the service
          _ <- service.clearGraph  // clear the graph
          response <- ZIO.succeed(Response.text("Graph deleted"))  // return the response
        } yield Response.text("Graph deleted")
      }}.sandbox,

      // GET /DFS --> get the Depth First Search of the current graph
      Method.GET / "unweighted" / "DFS" / int("startingNode") -> handler { (startingNode: Int, req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          DFS <- graph match {
            case grph: Graph[N, E] => ZIO.succeed(dfs(grph, startingNode.asInstanceOf[N])) // Proceed DFS
            case null => ZIO.succeed("Unknown graph type")
        }
        } yield Response.text(DFS.toString()) //Return the result
      }}.sandbox,

     // GET /BFS --> get the Breadth First Search of the current graph
     Method.GET / "unweighted" / "BFS" / int("startingNode") -> handler { (startingNode: Int, req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          BFS <- graph match {
            case grph: Graph[N, E] => ZIO.succeed(bfs(grph, startingNode.asInstanceOf[N])) // Proceed BFS
            case null => ZIO.succeed("Unknown graph type")
          }
        } yield Response.text(BFS.toString()) //Return the result
      }}.sandbox,

      // GET /topological --> get the topological order of the current graph
      Method.GET /  "unweighted" / "topological" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          topologicalResult <- graph match {
            case grph: DiGraph[N, E] => ZIO.succeed(topologicalSort(grph)) // Proceed Topological
            case _ => ZIO.succeed("Not allowed graph type")
          }
          response <- topologicalResult match {
            case Left(error) => ZIO.succeed(Response.text(s"Failed to run Topological: $error"))
            case Right(topological) => ZIO.succeed(Response.text(topological.mkString("\n"))) // Return the result
            case _ => ZIO.succeed(Response.text("Not allowed graph type"))
          } 
        } yield response //Return the result
      }}.sandbox,

      //GET /cycle --> get if the current graph has a cycle
      Method.GET / "unweighted" / "cycle" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          cycle <- graph match {
            case grph: DiGraph[N, E] => ZIO.succeed(hasCycle(grph)) // Check if there is a cycle
            case _ => ZIO.succeed("Not allowed graph type")
          }
        } yield Response.text(cycle.toString()) //Return the result
      }}.sandbox,

    )
  }
}
