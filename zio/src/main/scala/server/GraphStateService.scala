package server

import zio._
import Grph._

trait GraphStateService[N, E <: Edge[N]] {
  def getGraph: UIO[Graph[N, E]]
  def setGraph(graph: Graph[N, E]): UIO[Unit]
  def clearGraph: UIO[Unit]
}

object GraphStateService {
  def layer[N: Tag, E <: Edge[N]: Tag](initialGraph: Graph[N, E]): ULayer[GraphStateService[N, E]] = {
    ZLayer.fromZIO {
      for {
        ref <- Ref.make(initialGraph)
      } yield new GraphStateService[N, E] {
        def getGraph: UIO[Graph[N, E]] = ref.get
        def setGraph(graph: Graph[N, E]): UIO[Unit] = ref.set(graph)

        def clearGraph: UIO[Unit] = ref.set(initialGraph)
      }
    }
  }
}
