package tests

import ildl._

object Test {
  
  object ListAsVector extends TransformationDescription {
    // notice: `toRepr` only accepts `List[Long]`:
    def toRepr(list: List[Long]): Vector[Long] @high = list.toVector
    def toHigh[T](vec: Vector[T] @high): List[T] = vec.toList
  }

  def main(args: Array[String]): Unit = {
    adrt(ListAsVector) {
      val l1 = List(1,2,3)
      val l2 = l1
      val l3: Any = l1
    }
    println(l3)
    println("OK")
  }
}
