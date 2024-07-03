package Grph

import zio.json._

case class UnweightedEdge[N](from: N, to: N) extends Edge[N] {
  def weight: Double = 1
}

object UnweightedEdge {
  implicit def unweightedEdgeDecoder[N: JsonDecoder]: JsonDecoder[UnweightedEdge[N]] = DeriveJsonDecoder.gen[UnweightedEdge[N]]
  implicit def unweightedEdgeEncoder[N: JsonEncoder]: JsonEncoder[UnweightedEdge[N]] = DeriveJsonEncoder.gen[UnweightedEdge[N]]
}
