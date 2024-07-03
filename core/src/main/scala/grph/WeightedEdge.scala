package Grph

import zio.json._

case class WeightedEdge[N](from: N, to: N, weight: Double) extends Edge[N]

object WeightedEdge {
  implicit def weightedEdgeDecoder[N: JsonDecoder]: JsonDecoder[WeightedEdge[N]] = DeriveJsonDecoder.gen[WeightedEdge[N]]
  implicit def weightedEdgeEncoder[N: JsonEncoder]: JsonEncoder[WeightedEdge[N]] = DeriveJsonEncoder.gen[WeightedEdge[N]]
}
