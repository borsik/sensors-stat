import com.github.tototoshi.csv.CSVReader
import org.scalatest.funsuite.AnyFunSuite

import scala.io.Source

class SummaryTest extends AnyFunSuite {
  test("calculate summary") {
    val samples = LazyList(
      SensorData("s1", Some(10)),
      SensorData("s1", Some(5)),
      SensorData("s2", None),
    )
    assert(Summary.calc(samples) == Summary(3, 1, List(SensorStat("s1", Stat(Some(5), Some(7.5), Some(10))), SensorStat("s2", Stat.empty))))
  }

  test("read from file") {
    val samples = CSVReader.open(Source.fromResource("leader-1.csv")).toStream.drop(1).map { line =>
      SensorData(line(0), line(1).toIntOption)
    }
    assert(Summary.calc(LazyList(samples:_*)) == Summary(3, 1, List(
      SensorStat("s2", Stat(Some(88), Some(88), Some(88))),
      SensorStat("s1", Stat(Some(10), Some(10), Some(10))),
    )))
  }
}
