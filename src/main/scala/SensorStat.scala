import cats.Monoid

case class SensorStat(name: String, stat: Stat) {
  override def toString: String = s"$name,$stat"
}

object SensorStat {
  implicit val sensorStatMonoid: Monoid[Map[String, Stat]] = new Monoid[Map[String, Stat]] {
    def empty: Map[String, Stat] = Map.empty
    def combine(x: Map[String, Stat], y: Map[String, Stat]): Map[String, Stat] =
      y.foldLeft(x) {
        case (state, (name, summary)) => state.updatedWith(name) {
          case Some(nextSummary) => Some(Stat.statMonoid.combine(summary, nextSummary))
          case None => Some(summary)
        }
      }
  }
}
