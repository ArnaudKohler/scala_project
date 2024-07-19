package server.routes

import zio._
import zio.http._
import zio.json._
import Grph._
import Graphviz._
import server.GraphStateService

object UndigraphUnweighted {

  def routes[N: Tag: JsonDecoder: JsonEncoder, E <: Edge[N]: Tag: JsonDecoder: JsonEncoder] = {
    Routes(

    Method.POST / "undigraphUnweighted" -> handler { (req: Request) => {
        for {
          body <- req.body.asString  // Read Json body
          response <- body.fromJson[Undigraph[N, E]] match { //Try to parse it
            case Left(error) => ZIO.succeed(Response.text(s"Failed to parse graph: $error"))
            case Right(graph) =>
              for {
                _ <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.setGraph(graph))  // Update graph state
                currentGraph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  //For the confirmation message
              } yield Response.text(s"Updated graph: $currentGraph")
          }
        } yield response
      }}.sandbox,
      
      // PUT / addWeighted
     Method.PUT / "undigraphUnweighted" / "add" -> handler { (req: Request) => {
        for {
          body <- req.body.asString  // Read body json
          response <- body.fromJson[UnweightedEdge[N]] match { // parse the edge
            case Left(error) => ZIO.succeed(Response.text(s"Failed to parse edge: $error"))
            case Right(weightedEdge) =>
              for {
                graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // get actual graph
                updatedGraph <- (graph match { //Check its type
                  case digraph: Undigraph[N, UnweightedEdge[N]] => ZIO.succeed(digraph.addEdge(weightedEdge).asInstanceOf[Graph[N, E]]) // add edge
                  case _ => ZIO.fail(new Exception("Incompatible graph type"))
                })
                _ <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.setGraph(updatedGraph))  // Update graph state
              } yield Response.text(s"Updated graph: $updatedGraph")
          }
        } yield response
      }}.sandbox,

       Method.PUT / "undigraphUnweighted" / "remove" -> handler { (req: Request) => {
        for {
          body <- req.body.asString  // Read body json
          response <- body.fromJson[UnweightedEdge[N]] match { // parse the edge
            case Left(error) => ZIO.succeed(Response.text(s"Failed to parse edge: $error"))
            case Right(weightedEdge) =>
              for {
                graph <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.getGraph)  // get actual graph
                updatedGraph <- (graph match { //Check its type
                  case digraph: Undigraph[N, UnweightedEdge[N]] => 
                    val newGraph = digraph.removeEdge(weightedEdge) // remove edge in a new because immutable
                    ZIO.succeed(newGraph.asInstanceOf[Graph[N, E]]) 
                  case _ => ZIO.fail(new Exception("Incompatible graph type"))
                })
                _ <- ZIO.serviceWithZIO[GraphStateService[N, E]](_.setGraph(updatedGraph))  // Update graph state
              } yield Response.text(s"Updated graph: $updatedGraph")
          }
        } yield response
      }}.sandbox,

    )
  }
}
