import cats.Monoid

case class Stat(min: Option[Int], avg: Option[Double], max: Option[Int]) {
  override def toString: String = {
    val strMin = min.map(_.toString).getOrElse("NaN")
    val strAvg = avg.map(_.toString).getOrElse("NaN")
    val strMax = max.map(_.toString).getOrElse("NaN")
    s"$strMin,$strAvg,$strMax"
  }
}

object Stat {
  def empty: Stat = Stat(None, None, None)

  private def combineOpt[A](x: Option[A], y: Option[A])(combine: (A, A) => A): Option[A] = {
    (x, y) match {
      case (Some(a), Some(b)) => Some(combine(a, b))
      case (Some(a), None) => Some(a)
      case (None, Some(b)) => Some(b)
      case _ => None
    }
  }
  implicit val minMonoid: Monoid[Option[Int]] = new Monoid[Option[Int]] {
    def empty: Option[Int] = None
    def combine(x: Option[Int], y: Option[Int]): Option[Int] = combineOpt(x, y)(Math.min)
  }

  implicit val maxMonoid: Monoid[Option[Int]] = new Monoid[Option[Int]] {
    def empty: Option[Int] = None
    def combine(x: Option[Int], y: Option[Int]): Option[Int] = combineOpt(x, y)(Math.max)
  }

  implicit val avgMonoid: Monoid[Option[Double]] = new Monoid[Option[Double]] {
    def empty: Option[Double] = None
    def combine(x: Option[Double], y: Option[Double]): Option[Double] = combineOpt(x, y) {
      case (x, y) => (x + y) / 2
    }
  }

  implicit val statMonoid: Monoid[Stat] = new Monoid[Stat] {
    def empty: Stat = Stat(None, None, None)
    def combine(x: Stat, y: Stat): Stat = Stat(
      min = minMonoid.combine(x.min, y.min),
      max = maxMonoid.combine(x.min, y.max),
      avg = avgMonoid.combine(x.avg, y.avg)
    )
  }
}