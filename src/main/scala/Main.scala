import com.github.tototoshi.csv.CSVReader
import java.io.File

object Main extends App {
  args.headOption match {
    case Some(path) =>
      val files = new File(path).listFiles().filter(_.getName.endsWith(".csv"))
      val sensorsData = LazyList(files:_*).flatMap { file =>
        CSVReader.open(file).toStream.drop(1).map { line =>
          SensorData(line(0), line(1).toIntOption)
        }
      }

      val filesCount = files.length
      val summary = Summary.calc(sensorsData)

      println(s"Num of processed files: $filesCount")
      println(s"Num of processed measurements: ${summary.processed}")
      println(s"Num of failed measurements: ${summary.failed}")
      println()
      println("Sensors with highest avg humidity:")
      println()
      println("sensor-id,min,avg,max")
      summary.stats.foreach(println)

    case None => println("Provide path to dir with data")
  }
}