package server.routes

import zio._
import zio.http._
import zio.json._
import Grph._
import Graphviz._
import server.GraphStateService
import server.Main.emptyGraph

object OtherRoutes{
    def routes[N: Tag: JsonDecoder: JsonEncoder, E <: Edge[N]: Tag: JsonDecoder: JsonEncoder] = {
    Routes(
      //GET /graphViz --> get the toDot of the current graph to put it on a graphviz online
      Method.GET / "graphViz" -> handler { (req: Request) => {
        for {
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // getActual graph
          graphViz <- graph match { //Check if it's a DiGraph or an Undigraph
            case digraph: DiGraph[N, E] => ZIO.succeed(digraph.toDot)
            case undigraph: Undigraph[N, E] => ZIO.succeed(undigraph.toDot)
            case _ => ZIO.succeed("Unknown graph type")
          }
        } yield Response.text(graphViz) //Return the graphViz
      }}.sandbox,

      //PUT /delete --> set empty graph
      Method.DELETE / "delete" -> handler { (req: Request) => {        
        for {
          emptyGraph <- ZIO.succeed(DiGraph.empty[N, E])  // Créer un graphe vide
          _ <- ZIO.serviceWith[GraphStateService[N, E]](_.setGraph(emptyGraph))  // Mettre à jour l'état du graphe
          graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)
          test <- graph match {
            case digraph: DiGraph[N, E] => ZIO.succeed(digraph.toDot)
            case undigraph: Undigraph[N, E] => ZIO.succeed(undigraph.toDot)
            case _ => ZIO.succeed("Unknown graph type")
          }
        } yield Response.text("Graph deleted" + test)
      }}.sandbox
    )
  }
}