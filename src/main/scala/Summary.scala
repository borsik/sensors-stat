import cats.Foldable

case class Summary(processed: Int, failed: Int, stats: List[SensorStat])

object Summary {
  def calc(sensorsData: LazyList[SensorData]): Summary = {
    val processed = sensorsData.size
    val failed = sensorsData.count(_.value.isEmpty)

    val statsMap = sensorsData.map { sensor =>
      Map(sensor.name -> Stat(sensor.value, sensor.value.map(_.toDouble), sensor.value))
    }
    val stats = Foldable[LazyList].fold(statsMap)(SensorStat.sensorStatMonoid).toList.sortBy {
      case (_, summary) => summary.avg
    }.map {
      case (str, stat) => SensorStat(str, stat)
    }.reverse
    Summary(processed, failed, stats)
  }
}
