package Grph

trait Edge[N] {
  def from: N
  def to: N
  def weight: Double
}
