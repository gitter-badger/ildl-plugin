package ildl
package benchmark
package gcd
package step2

/**
 *  A simpler transformation, which uses java.lang.Long instead of scala.Long
 *  as the transformation target.
 *  @see the comment in [[ildl.benchmark.gcd.GreatestCommonDivisor]] for more information
 */
object IntPairAsGaussianInteger extends TransformationDescription {

  // Here we're un-importing scala.Long and importing java.lang.Long
  // so we can keep the same code
  import scala.{ Long => _ }
  import java.lang.Long

  // coercions:
  def toRepr(pair: (Int, Int)): Long @high = pack(pair._1, pair._2)
  def toHigh(l: Long @high): (Int, Int) = (re(l), im(l))

  // constructor:
  def ctor_Tuple2(_1: Int, _2: Int): Long @high = pack(_1, _2)

  // interface: (no need to expose everything)
  def implicit_IntPairAsGaussianIntegerImplicit_%(n1: Long @high, n2: Long @high): Long @high = %(n1, n2)
  def implicit_IntPairAsGaussianIntegerImplicit_norm(n: Long @high): Int = norm(n)

  // extension methods:
  def extension_==(receiver: Long @high, other: Long @high): Boolean = receiver == other
  def extension_toString(receiver: Long @high): String = toHigh(receiver).toString

  // implementation:
  private def pack(re: Int, im: Int): Long = Long.valueOf((re << 32l) | (im & 0xFFFFFFFFl))
  private def re(l: Long @high): Int = (l.longValue() >>> 32).toInt
  private def im(l: Long @high): Int = (l.longValue() & 0xFFFFFFFF).toInt
  private def norm(l: Long @high): Int = re(l)^2 + im(l)^2
  private def c(l: Long @high): Long @high = pack(re(l), -im(l))
  private def +(n1: Long @high, n2: Long @high): Long @high = pack(re(n1) + re(n2), im(n1) + im(n2))
  private def -(n1: Long @high, n2: Long @high): Long @high = pack(re(n1) - re(n2), im(n1) - im(n2))
  private def *(n1: Long @high, n2: Long @high): Long @high = pack(re(n1) * re(n2) - im(n1) * im(n2), re(n1) * im(n2) + im(n1) * re(n2))
  private def *(n1: Long @high, n2: Int): Long @high = pack(re(n1) * n2, im(n1) * n2)
  private def /(n1: Long @high, n2: Long @high): Long @high = {
    val denom = *(n2, c(n2))
    val numer = *(n1, c(n2))
    assert(im(denom) == 0)
    pack(math.round(re(numer).toFloat / re(denom)), math.round(im(numer).toFloat / re(denom)))
  }
  private def %(n1: Long @high, n2: Long @high): Long @high = pos(this.-(n1,*(/(n1, n2), n2)))
  private def pos(n1: Long @high): Long @high = if (re(n1) < 0) pack(-re(n1), -im(n1)) else n1
  private def bits(n1: Long @high): String = f"$n1%016x"
}