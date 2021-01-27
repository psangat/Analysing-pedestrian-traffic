import scala.collection.mutable.Map
import scala.io.Source

object Utils {
  /**
   * From the book, Beginning Scala, by David Pollak.
   */
  def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try {
      f(param)
    } finally {
      param.close()
    }

  /** Reads the config file
   *
   *  @param filename path to the file
   *  @return mutable map of the configuration
   */
  def readConfigFile(filename: String): Map[String, String] = {
    val configuration: Map[String, String] = Map()
    using(Source.fromFile(filename)) { source =>
      for (line <- source.getLines) {
        val config = line.split(":")
        configuration += config(0) -> config(1)
      }
    }
    configuration
  }

}