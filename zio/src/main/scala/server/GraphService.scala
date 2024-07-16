package server

import zio._
import Grph._
import zio.http._

trait GraphService [N, E <: Edge[N]] {
  def create(graph: Graph [N,E]): Task[Graph [N,E]]
  /* def findById(id: Long): Task[Option[Graph [N,E]]]
  def update(graph: Graph [N,E]): Task[Option[Graph [N,E]]]
  def delete(id: Long): Task[Option[Graph [N,E]]] */
}


class InMemoryGraphService[N, E <: Edge[N]] extends GraphService[N, E] {
  private var graphs: Map[Long, Graph[N, E]] = Map.empty
  private var nextId: Long = 1

  override def create(graph: Graph[N, E]): Task[Graph[N, E]] = {
    val id = nextId
    nextId += 1
    graphs = graphs + (id -> graph) // Update the graphs map
    ZIO.succeed(graph)  
  }
}