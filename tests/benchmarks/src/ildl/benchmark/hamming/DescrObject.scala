package ildl
package benchmark
package hamming

import collection.mutable.Queue

object BigIntAsLong extends TransformationDescription {
  def toRepr(high: BigInt): Long @high = {
    assert(high.isValidLong)
    high.longValue()
  }
  def toHigh(repr: Long @high): BigInt = BigInt(repr)

  def extension_*(recv: Long @high, other: Long @high): Long @high =
    // note: Math.multiplyExact requires Java 8
    // java.lang.Math.multiplyExact(recv, other)
    recv * other

  def extension_+(recv: Long @high, other: Long @high): Long @high =
    // note: Math.multiplyExact requires Java 8
    // java.lang.Math.addExact(recv, other)
    recv + other

  def extension_==(recv: Long @high, other: Long @high): Boolean =
    // note: Math.multiplyExact requires Java 8
    // java.lang.Math.addExact(recv, other)
    recv == other

    def extension_min(recv: Long @high, other: Long @high): Long @high =
    if (recv < other)
      recv
    else
      other
}


object QueueOfLongAsFunnyQueue extends TransformationDescription {

  def toRepr(in: Queue[BigInt]): FunnyQueue @high = {
    assert(in.isEmpty, "Cannot start from a non-empty queue!")
    new FunnyQueue
  }

  def toHigh(q: FunnyQueue @high): Queue[BigInt] = {
    assert(false, "We shouldn't need this!")
    ???
  }

  def implicit_QueueWithEnqueue1_enqueue1(q: FunnyQueue @high)(bi: Long @high(BigIntAsLong)): Unit = {
    q.enqueue(bi)
  }

  def extension_enqueue(q: FunnyQueue @high, bis: BigInt*): Unit = {
    // we don't support more than one element :)
    assert(bis.size == 1)
    val bi = bis.apply(0)
    assert(bi.isValidLong)
    q.enqueue(bi.longValue())
  }

  def extension_dequeue(q: FunnyQueue @high): Long @high(BigIntAsLong) = q.dequeue()

  def extension_head(q: FunnyQueue @high): Long @high(BigIntAsLong) = q.head()
}