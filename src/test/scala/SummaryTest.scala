import org.scalatest.funsuite.AnyFunSuite

class SummaryTest extends AnyFunSuite {
  test("summary") {
    val samples = LazyList(
      SensorData("s1", Some(10)),
      SensorData("s1", Some(5)),
      SensorData("s2", None),
    )
    assert(Summary.calc(samples) == Summary(3, 1, List(SensorStat("s1", Stat(Some(5), Some(7.5), Some(10))), SensorStat("s2", Stat.empty))))
  }
}
